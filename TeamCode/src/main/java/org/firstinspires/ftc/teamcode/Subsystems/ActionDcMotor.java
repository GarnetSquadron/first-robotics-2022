package org.firstinspires.ftc.teamcode.Subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Actions;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.MiscActions.CancelableAction;
import org.firstinspires.ftc.teamcode.time.TTimer;
import org.firstinspires.ftc.teamcode.enums.AngleUnitV2;

import java.util.function.Function;

public class ActionDcMotor {
    private DcMotorSub motor;
    private String motorName;
    public ActionDcMotor(HardwareMap hardwareMap, String MotorName, int minPos, int maxPos, double posCoefficient,double tolerance){
        motor = new DcMotorSub(hardwareMap,MotorName,minPos, maxPos,posCoefficient,tolerance);
        motorName = MotorName;
    }
    public ActionDcMotor(HardwareMap hardwareMap, String MotorName, int minPos, int maxPos, double posCoefficient,double velCoefficient,double maxPower,double tolerance){
        motor = new DcMotorSub(hardwareMap,MotorName,minPos, maxPos,posCoefficient,velCoefficient,maxPower,tolerance);
        motorName = MotorName;
    }
    public void setNewCoefficient(double c){
        motor.setNewPosCoefficient(c);
    }
    public class SetTgtPosRatio implements Action{
        double pos,tolerance;
        boolean ChangingTolerance = true;
        public SetTgtPosRatio(double pos){
            this.pos = pos;
            ChangingTolerance = false;
        }
        public SetTgtPosRatio(double pos,double tolerance){
            this.pos = pos;
            this.tolerance = tolerance;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if(ChangingTolerance){
                motor.setTgPosRatio(pos,tolerance);
            }
            else
                motor.setTgPosRatio(pos);
            return false;
        }
    }
    public class SetTgtPosAngle implements Action{
        double pos,tolerance;
        boolean ChangingTolerance = true;
        AngleUnitV2 unit;
        public SetTgtPosAngle(double pos, AngleUnitV2 unit){
            this.pos = pos;
            this.unit = unit;
            ChangingTolerance = false;
        }
        public SetTgtPosAngle(double pos, AngleUnitV2 unit,double tolerance){
            this.pos = pos;
            this.unit = unit;
            this.tolerance = tolerance;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if(ChangingTolerance){
                motor.setTgPosAngle(pos, unit,tolerance);
            }
            else
                motor.setTgPosAngle(pos,unit);
            return false;
        }
    }
    public Action goToTgtPos = new Action(){
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                motor.runToTgPos();
                if (motor.TargetReached()){
                    motor.stop();
                    return false;
                }
                else {
                    return true;
                }
            }
        };

    /**
     * runs to a position and then proceeds to hold that position. This function will not work unless you are also updating the motor speed continuously
     */
    public class goToTgtPosAndHoldIt implements Action{
        double holdPower,tgtPos,runPower = 1;
        boolean firstIter = true;
        /**
         * runs at max vel because speeeeeeeeeeeeeeed
         * @param holdPower
         */
        goToTgtPosAndHoldIt(double holdPower){
            this.holdPower = holdPower;
        }
        /**
         * runs at whatever power you want because mechanical doesn't like fun things.
         * @param holdPower
         */
        goToTgtPosAndHoldIt(double holdPower,double runPower){
            this.holdPower = holdPower;
            this.runPower = runPower;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if(firstIter){
                tgtPos = motor.getTargetPos();
                firstIter = false;
            }
            motor.runToTgPosAndHoldIt(holdPower,runPower);
            if(motor.TargetReached()){//just added this part because it makes life simpler. I feel like I would have done this a while back, so maybe theres a reason it wasnt like this? Im just going to try it
                return false;
            }
            return tgtPos == motor.getTargetPos();//if the target position is switched, stop the action
        }
    };
    public class goToTgtPosAndHoldItAccountingForExtForces implements Action{
        double tgtPos;
        boolean firstIter = true;
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            telemetryPacket.put(motorName+" targetReached",motor.TargetReached());
            if(firstIter){
                tgtPos = motor.getTargetPos();
                firstIter = false;
            }
            motor.AccountForExtForces();
            if(motor.TargetReached()){//just added this part because it makes life simpler. I feel like I would have done this a while back, so maybe theres a reason it wasnt like this? Im just going to try it
                return false;
            }
            return tgtPos == motor.getTargetPos();//if the target position is switched, stop the action
        }
    };
    public class goToTgtPosButIfStoppedAssumeTgtPosHasBeenReached implements Action {
        boolean firstLoop=true;
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            telemetryPacket.put(motorName+" target reached",motor.TargetReached());
            motor.runToTgPos();
            if(getSpeed() == 0&&!firstLoop&&Math.abs(motor.getTargetPos()-motor.getPos())<motor.tolerance){
                    motor.stop();
                    motor.setPosition(motor.getTargetPos());
                    return false;
            }
            /* Andrew Suggestion LOOK DJ TODO Also could be bad for viper though so maybe not
            else if(getSpeed() == 0&&!firstLoop){
                    motor.stop();

                    return false;
            }
             */
//            if(motor.TargetReached()){
//                motor.stop();
//                return false;
//            }
            firstLoop = false;
            return true;
        }
    }
    public class goUntilStoppedAndAssumeTgtPosHasBeenReached implements Action {
        boolean firstLoop=true;
        double power;
        int ticks;
        double angle;
        AngleUnitV2 unit;
        boolean AsAnAngle;
        TTimer timer = new TTimer(Actions::now);
        public goUntilStoppedAndAssumeTgtPosHasBeenReached(double power, int ticks){
            this.power = power;
            this.ticks = ticks;
            AsAnAngle = false;
        }
        public goUntilStoppedAndAssumeTgtPosHasBeenReached(double power, double angle,AngleUnitV2 unit){
            this.power = power;
            this.angle = angle;
            this.unit = unit;
            AsAnAngle = true;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if(firstLoop){
                motor.JustKeepRunning(power);
                timer.StartTimer(0.1);
            }
            if(getSpeed() == 0&&timer.timeover()){
                motor.stop();
                if(AsAnAngle){
                    motor.setAngle(angle,unit);
                }
                else{
                    motor.setPosition(ticks);
                }
                return false;
            }
            firstLoop = false;
            return true;
        }
    }
    public class setPowerForDuration implements Action{
        boolean firstLoop=true;
        double power,duration;
        TTimer timer = new TTimer(Actions::now);
        public setPowerForDuration(double power, double duration){
            this.power = power;
            this.duration = duration;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if(firstLoop){
                motor.JustKeepRunning(power);
                timer.StartTimer(duration);
            }
            if(timer.timeover()){
                motor.stop();
                return false;
            }
            firstLoop = false;
            return true;
        }
    }
    public class GoUntilStopped implements Action {
        boolean firstLoop=true;
        double power;
        TTimer timer = new TTimer(Actions::now);
        public GoUntilStopped(double power){
            this.power = power;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if(firstLoop){
                motor.JustKeepRunning(power);
                timer.StartTimer(1000);
            }
            if(getSpeed() == 0&&timer.timeover()){
                motor.stop();
                return false;
            }
            firstLoop = false;
            return true;
        }
    }
    public class IncreaseForceUntilStoppedOnceMore implements Action {
        boolean firstLoop=true;
        boolean secondStage = false;
        double rampedForce = 0;
        double power;
        TTimer timer = new TTimer(Actions::now);
        public IncreaseForceUntilStoppedOnceMore(double power){
            this.power = power;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if(firstLoop){
                timer.StartTimer(5);
            }
            if(!secondStage){
                motor.JustKeepRunning(rampedForce);
                rampedForce+=power;
                if(motor.getSpeed()!=0){
                    secondStage = true;
                }
            }
            if(secondStage){
                if(motor.getSpeed()==0){
                    motor.setPower(0);
                    return false;
                }
            }
            if(timer.timeover()){
                return false;
            }
            firstLoop = false;
            return true;
        }
    }
    public boolean targetReached(){
        return motor.TargetReached();
    }

    public Action Stop = new InstantAction(()->motor.stop());
    private class UpdatePower implements Action{

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            motor.updatePower();
            return true;
        }
    }
    public Action updatePower(){
        return new UpdatePower();
    }
    //public Action updatePower = new InstantAction(()->motor.updatePower());
    public Action GoToPos(double pos){
        return new CancelableAction(new SequentialAction(new SetTgtPosRatio(pos),goToTgtPos),Stop);
    }

    public Action GoToPosAndHoldIt(double pos,double holdPower){
        return new CancelableAction(new SequentialAction(new SetTgtPosRatio(pos),new goToTgtPosAndHoldIt(holdPower)),Stop);
    }
    public Action GoToPosAndHoldIt(double pos,double holdPower,double runPower){
        return new CancelableAction(new SequentialAction(new SetTgtPosRatio(pos),new goToTgtPosAndHoldIt(holdPower,runPower)),Stop);
    }
    public Action GoToPosButIfStoppedAssumePosHasBeenReached(double pos){
        return new CancelableAction(new SequentialAction(new SetTgtPosRatio(pos),new goToTgtPosButIfStoppedAssumeTgtPosHasBeenReached()),Stop);
    }
    public Action GoToPosButIfStoppedAssumePosHasBeenReached(double pos,double tolerance){
        return new CancelableAction(new SequentialAction(new SetTgtPosRatio(pos,tolerance),new goToTgtPosButIfStoppedAssumeTgtPosHasBeenReached()),Stop);
    }
    public Action goUntilStoppedAndAssumeTgtPosHasBeenReached(int pos,double power){
        return new CancelableAction(new goUntilStoppedAndAssumeTgtPosHasBeenReached(power,pos),Stop);
    }
    public Action GoToAngle(double angle,AngleUnitV2 unit){
        return new CancelableAction(new SequentialAction(new SetTgtPosAngle(angle,unit),goToTgtPos),Stop);
    }
    public Action GoToAngleAndHoldIt(double angle,double tolerance,double holdPower,AngleUnitV2 unit){
        return new CancelableAction(new SequentialAction(new SetTgtPosAngle(angle,unit,tolerance),new goToTgtPosAndHoldIt(holdPower)),Stop);
    }
    public Action GoToAngleAndHoldIt(double angle,double tolerance,double holdPower,double runPower,AngleUnitV2 unit){
        return new CancelableAction(new SequentialAction(new SetTgtPosAngle(angle,unit,tolerance),new goToTgtPosAndHoldIt(holdPower,runPower)),Stop);
    }
    public Action GoToAngleAndHoldItAccountingForExternalForces(double angle,double tolerance,AngleUnitV2 unit){
        return new CancelableAction(new SequentialAction(new SetTgtPosAngle(angle,unit,tolerance),new goToTgtPosAndHoldItAccountingForExtForces()));
    }
    public Action GoToAngleButIfStoppedAssumePosHasBeenReached(double angle,double tolerance,AngleUnitV2 unit){
        return new CancelableAction(new SequentialAction(new SetTgtPosAngle(angle,unit,tolerance),new goToTgtPosButIfStoppedAssumeTgtPosHasBeenReached()),Stop);
    }
    public Action goUntilStoppedAndAssumeTgtAngleHasBeenReached(double angle,double power,AngleUnitV2 unit){
        return new CancelableAction(new goUntilStoppedAndAssumeTgtPosHasBeenReached(power,angle,unit),Stop);
    }
    public Action goUntilStoppedAndThenRampPowerUntilItsStoppedAgain(double power1,double power2){
        return new ParallelAction(new GoUntilStopped(power1),new IncreaseForceUntilStoppedOnceMore(power2));
    }


    public class runWithRawPower implements Action{

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            return false;
        }
    }
    public class HoldCurrentPos implements Action{
        public HoldCurrentPos(){

        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            motor.setTgPosTick(getPos());
            motor.runToTgPos();
            return false;
        }
    }
//    public Action HoldPos(){
//
//    }
    public double getDistanceToTarget(){
        return motor.getTargetPos()-motor.getPos();
    }
    public double getTargetPos(){
        return motor.getTargetPos();
    }
    public double getTargetAngle(AngleUnitV2 unit){
        return motor.getTargetAngle(unit);
    }
    public double getCurrent(){
        return motor.getCurrent();
    }
    public int getPos(){
        return motor.getPos();
    }
    public double getAngle(AngleUnitV2 unit){
        return motor.getPosInAngle(unit);
    }
    public double getPower(){
        return motor.getPower();
    }
    public double getSpeed(){
        return motor.getSpeed();
    }
    public void setEncoder(Motor encoder){
        motor.setEncoder(encoder);
    }
    public Motor getMotor(){
        return motor.getMotor();
    }
    public void reverseMotor(){
        motor.ReverseMotor();
    }
    public double getError(){
        return motor.getError();
    }
    public void setExtTorqueFunction(Function<Double,Double> function){
        motor.setExtTorqueFunction(function);
    }
    public boolean inExtForceMode(){
        return motor.inExtForceMode();
    }
}
