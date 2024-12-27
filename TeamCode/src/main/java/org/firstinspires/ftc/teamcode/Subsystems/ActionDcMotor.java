package org.firstinspires.ftc.teamcode.Subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.MiscActions.CancelableAction;

public class ActionDcMotor {
    private DcMotorSub motor;
    public ActionDcMotor(HardwareMap hardwareMap, String MotorName, int minPos, int maxPos, double posCoefficient){
        motor = new DcMotorSub(hardwareMap,MotorName,minPos, maxPos,posCoefficient);
    }
    boolean Stopping = false;
    public class SetTgPos implements Action{
        double pos;
        public SetTgPos(double pos){
            this.pos = pos;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            motor.setTgPos(pos);
            return false;
        }
    }
    public Action goToTgtPos = new CancelableAction(
        new Action(){
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                motor.runToTgPos();
                if (motor.TargetReached()&&!Stopping){
                    motor.stop();
                    return false;
                }
                else {
                    return true;
                }
            }
        }
    );
    public boolean targetReached(){
        return motor.TargetReached();
    }
    public Action GoToPos(double pos){
        return new SequentialAction(new SetTgPos(pos),goToTgtPos);
    }
}
