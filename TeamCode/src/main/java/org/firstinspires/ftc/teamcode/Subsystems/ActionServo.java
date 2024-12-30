package org.firstinspires.ftc.teamcode.Subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.MiscActions.CancelableAction;

import java.util.function.DoubleSupplier;

public class ActionServo {
    public ServoSub servo;
    public double rangeInDegrees;

    /**
     * Servo that has a bunch of actions
     * @param hardwareMap
     * @param name name of the device
     * @param min minimum position (a number from 0 to 1)
     * @param max maximum position (a number from 0 to 1)
     * @param time the system time supplier
     * @param t
     * @param rangeInDegrees
     */
    public ActionServo(HardwareMap hardwareMap, String name, double min, double max, double t, DoubleSupplier time,double rangeInDegrees){
        servo = new ServoSub(hardwareMap,name,min,max,time,t);
        this.rangeInDegrees = rangeInDegrees;
    }
    public ActionServo(HardwareMap hardwareMap, String name,double min,double max, DoubleSupplier time){
        this(hardwareMap,name,min,max,0.5,time,270);
    }
    public ActionServo(HardwareMap hardwareMap, String name,double min,double max, double t,DoubleSupplier time){
        this(hardwareMap,name,min,max,t,time,270);
    }
    public ActionServo(HardwareMap hardwareMap, String name,double min,double max, DoubleSupplier time,double rangeInDegrees){
        this(hardwareMap,name,min,max,0.5,time,rangeInDegrees);
    }
    public Action runToDegrees(double angle){
        return runToRatio(angle/rangeInDegrees);
    }
    public Action runToRad(double angle){
        return runToDegrees(Math.toDegrees(angle));
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
    public Action changePosBy(double delta){
        return runToRatio(getPos()-delta);
    }

}
