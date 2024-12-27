package org.firstinspires.ftc.teamcode.Subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.function.DoubleSupplier;

public class ActionServo {
    public ServoSub servo;
    public ActionServo(HardwareMap hardwareMap, String name, double min, double max, DoubleSupplier time, double t){
        servo = new ServoSub(hardwareMap,name,min,max,time,t);
    }
    public ActionServo(HardwareMap hardwareMap, String name,double min,double max, DoubleSupplier time){
        this(hardwareMap,name,min,max,time,1);
    }

    public double getPos() {
        return servo.getPos();
    }

    public class MoveServoToRatio implements Action{
        double ratio;
        MoveServoToRatio(double ratio){
            this.ratio = ratio;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            servo.goToRatio(ratio);
            return false;
        }
    }
    public class WaitForTargetReached implements Action{
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            return !servo.targetReached();
        }
    }
    public Action runToRatio(double ratio){
        return new SequentialAction(new MoveServoToRatio(ratio),new WaitForTargetReached());
    }

}
