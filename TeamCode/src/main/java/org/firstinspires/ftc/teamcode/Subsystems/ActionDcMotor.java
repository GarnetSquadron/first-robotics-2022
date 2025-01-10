package org.firstinspires.ftc.teamcode.Subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Actions;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.ExtraMath;
import org.firstinspires.ftc.teamcode.MiscActions.CancelableAction;
import org.firstinspires.ftc.teamcode.TTimer;

public class ActionDcMotor {
    private DcMotorSub motor;
    public ActionDcMotor(HardwareMap hardwareMap, String MotorName, int minPos, int maxPos, double posCoefficient){
        motor = new DcMotorSub(hardwareMap,MotorName,minPos, maxPos,posCoefficient);
    }
    public class SetTgPos implements Action{
        double pos;
        public SetTgPos(double pos){
            this.pos = pos;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            motor.setTgPosRatio(pos);
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
    public class goToTgtPosButIfStoppedAssumeTgtPosHasBeenReached implements Action {
        double tolerance, TimeOutTime;
        public goToTgtPosButIfStoppedAssumeTgtPosHasBeenReached(double tolerance,double TimeOutTime){
            this.tolerance = tolerance;
            this.TimeOutTime = TimeOutTime;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            motor.runToTgPos();
            if(getSpeed() == 0){
                    motor.stop();
                    motor.setPosition(motor.getTargetPos());
                    return false;
            }
            if(motor.TargetReached()){
                motor.stop();
                return false;
            }
            return true;
        }
    }
    public class holdPosition implements Action{
        public holdPosition(double power){

        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            return false;
        }
    }
    public boolean targetReached(){
        return motor.TargetReached();
    }

    public Action Stop = new InstantAction(()->motor.stop());

    public Action GoToPos(double pos){
        return new CancelableAction(new SequentialAction(new SetTgPos(pos),goToTgtPos),Stop);
    }
    public Action GoToPosButIfStoppedAssumePosHasBeenReached(double pos,double tolerance,double timeOutTime){
        return new CancelableAction(new SequentialAction(new SetTgPos(pos),new goToTgtPosButIfStoppedAssumeTgtPosHasBeenReached(tolerance,timeOutTime)),Stop);
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
