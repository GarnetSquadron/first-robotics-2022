package org.firstinspires.ftc.teamcode.Subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.MiscActions.CancelableAction;

public class ActionDcMotor {
    private DcMotorSub motor;
    public ActionDcMotor(HardwareMap hardwareMap, String MotorName, int minPos, int maxPos, double posCoefficient){
        motor = new DcMotorSub(hardwareMap,MotorName,minPos, maxPos,posCoefficient);
    }
    public class SetTgtPos implements Action{
        double pos,tolerance;
        public SetTgtPos(double pos, double tolerance){
            this.pos = pos;
            this.tolerance = tolerance;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            motor.setTgPosRatio(pos,tolerance);
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
        double holdPower;
        goToTgtPosAndHoldIt(double holdPower){
            this.holdPower = holdPower;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            motor.runToTgPosAndHoldIt(holdPower);
            return true;
        }
    };
    public class goToTgtPosButIfStoppedAssumeTgtPosHasBeenReached implements Action {
        double tolerance;
        boolean firstLoop=true;
        public goToTgtPosButIfStoppedAssumeTgtPosHasBeenReached(double tolerance){
            this.tolerance = tolerance;
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
    public boolean targetReached(){
        return motor.TargetReached();
    }

    public Action Stop = new InstantAction(()->motor.stop());

    public Action GoToPos(double pos,double tolerance){
        return new CancelableAction(new SequentialAction(new SetTgtPos(pos,tolerance),goToTgtPos),Stop);
    }
    public Action GoToPosAndHoldIt(double pos,double tolerance,double holdPower){
        return new CancelableAction(new SequentialAction(new SetTgtPos(pos,tolerance),new goToTgtPosAndHoldIt(holdPower)),Stop);
    }
    public Action GoToPosButIfStoppedAssumePosHasBeenReached(double pos,double tolerance){
        return new CancelableAction(new SequentialAction(new SetTgtPos(pos,tolerance),new goToTgtPosButIfStoppedAssumeTgtPosHasBeenReached(tolerance)),Stop);
    }
    public double getDistanceToTarget(){
        return motor.getTargetPos()-motor.getPos();
    }
    public double getTargetPos(){
        return motor.getTargetPos();
    }
    public double getCurrent(){
        return motor.getCurrent();
    }
    public double getPos(){
        return motor.getPos();
    }
    public double getPower(){
        return motor.getPower();
    }
    public double getSpeed(){
        return motor.getSpeed();
    }
}
