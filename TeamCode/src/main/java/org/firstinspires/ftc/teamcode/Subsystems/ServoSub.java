package org.firstinspires.ftc.teamcode.Subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Actions;
import com.acmerobotics.roadrunner.InstantAction;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.ExtraMath;
import org.firstinspires.ftc.teamcode.TTimer;

import java.util.function.DoubleSupplier;

public class ServoSub {
    private final Servo servo;
    private double Max;
    private double Min;
    public TTimer timer;
    double runtime;
    private boolean powered = false;
    public ServoSub(HardwareMap hardwareMap, String name, double min, double max, DoubleSupplier time, double runtime) {
        servo = hardwareMap.get(Servo.class, name);
        Max = max;
        Min = min;
        timer = new TTimer(time);
        this.runtime = runtime;
    }
    public ServoSub(HardwareMap hardwareMap, String name, double min, double max, DoubleSupplier time){
        this(hardwareMap, name, min, max,time,1);

    }

    double getPosFromRatio(double ratio){
        return Min+ratio*(Max-Min);
    }
    double getRatioFromPos(double pos){
        return (pos-Min)/(Max-Min);
    }
    public void goToRatio(double ratioPos){
        ratioPos = ExtraMath.Clamp(ratioPos,1,0);
        if(!ExtraMath.ApproximatelyEqualTo(servo.getPosition(), getPosFromRatio(ratioPos),0.1)){
            timer.StartTimer(runtime);//when the timer goes off, the servo should be at the correct position. this needs to be tuned
        }
        if(!powered)
            servo.setPosition(1);//on init, the servo position is set to 0, even though it isnt powered and probably isnt at 0. if you then run servo.setposition(0), it will not move because it already this
        servo.setPosition(getPosFromRatio(ratioPos));
        powered = true;
    }

    public void MoveToMax() {
        goToRatio(1);
    }
    public void MoveToMin() {
        goToRatio(0);
    }
//    private double getToothSize(int teeth){
//        return 4.0/(teeth*3.0);
//    }
    public double getPos(){
        return servo.getPosition();
    }
    public double getRatioPos(){
        return getRatioFromPos(getPos());
    }
    public boolean targetReached(){
        return timer.timeover()|| !timer.timestarted();
    }
    public void changePosBy(double delta){
        goToRatio(getRatioPos()+delta);
    }
    public boolean isPowered(){
        return powered;
    };
    public boolean atMax(){
        return ExtraMath.ApproximatelyEqualTo(servo.getPosition(),Max,0.01);
    }

}
