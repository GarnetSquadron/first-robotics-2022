package org.firstinspires.ftc.teamcode.Subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.MiscActions.CancelableAction;
import org.firstinspires.ftc.teamcode.enums.AngleUnitV2;

public class ActionDcMotor {
    private DcMotorSub motor;
    public ActionDcMotor(HardwareMap hardwareMap, String MotorName, int minPos, int maxPos, double posCoefficient,double tolerance){
        motor = new DcMotorSub(hardwareMap,MotorName,minPos, maxPos,posCoefficient,tolerance);
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
    public class goToTgtPosAndHoldIt implements Action{
        double holdPower,tgtPos;
        boolean firstIter = true;
        goToTgtPosAndHoldIt(double holdPower){
            this.holdPower = holdPower;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if(firstIter){
                tgtPos = motor.getTargetPos();
                firstIter = false;
            }
            motor.runToTgPosAndHoldIt(holdPower);
            return tgtPos == motor.getTargetPos();//if the target position is switched, stop the action
        }
    };
    public class goToTgtPosButIfStoppedAssumeTgtPosHasBeenReached implements Action {
        boolean firstLoop=true;
        public goToTgtPosButIfStoppedAssumeTgtPosHasBeenReached(){
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            motor.runToTgPos();
            if(getSpeed() == 0&&!firstLoop){
                    motor.stop();
                    motor.setPosition(motor.getTargetPos());
                    return false;
            }
            if(motor.TargetReached()){
                motor.stop();
                return false;
            }
            firstLoop = false;
            return true;
        }
    }
    public class goUntilStoppedAndAssumeTgtPosHasBeenReached implements Action {
        boolean firstLoop=true;
        double power;
        int pos;
        public goUntilStoppedAndAssumeTgtPosHasBeenReached(double power, int pos){
            this.power = power;
            this.pos = pos;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            motor.JustKeepRunning(power);
            if(getSpeed() == 0&&!firstLoop){
                motor.stop();
                motor.setPosition(pos);
                return false;
            }
            firstLoop = false;
            return true;
        }
    }
    public class goUntilStoppedAndAssumeTgtAngleHasBeenReached implements Action {
        boolean firstLoop=true;
        double power;
        double angle;
        AngleUnitV2 unit;
        public goUntilStoppedAndAssumeTgtAngleHasBeenReached(double power, double angle,AngleUnitV2 unit){
            this.power = power;
            this.angle = angle;
            this.unit = unit;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            motor.JustKeepRunning(power);
            if(getSpeed() == 0&&!firstLoop){
                motor.stop();
                motor.setAngle(angle,unit);
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

    public Action GoToPos(double pos){
        return new CancelableAction(new SequentialAction(new SetTgtPosRatio(pos),goToTgtPos),Stop);
    }
    public Action GoToPosAndHoldIt(double pos,double holdPower){
        return new CancelableAction(new SequentialAction(new SetTgtPosRatio(pos),new goToTgtPosAndHoldIt(holdPower)),Stop);
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
    public Action GoToAngleButIfStoppedAssumePosHasBeenReached(double angle,double tolerance,AngleUnitV2 unit){
        return new CancelableAction(new SequentialAction(new SetTgtPosAngle(angle,unit,tolerance),new goToTgtPosButIfStoppedAssumeTgtPosHasBeenReached()),Stop);
    }
    public Action goUntilStoppedAndAssumeTgtAngleHasBeenReached(double angle,double power,AngleUnitV2 unit){
        return new CancelableAction(new goUntilStoppedAndAssumeTgtAngleHasBeenReached(power,angle,unit),Stop);
    }
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
    public double getPos(){
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
}
