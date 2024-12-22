package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.ExtraMath;
import org.firstinspires.ftc.teamcode.TTimer;


public class ServoSub {
    private final Servo servo;
    private double Max;
    private double Min;
    TTimer timer;
    double runtime;

    public ServoSub(HardwareMap hardwareMap, String name, double min, double max,double runtime) {
        servo = hardwareMap.get(Servo.class, name);
        Max = max;
        Min = min;
        timer = new TTimer(System::currentTimeMillis);
        this.runtime = runtime;
    }
    public ServoSub(HardwareMap hardwareMap, String name, double min, double max){
        this(hardwareMap, name, min, max,1000);

    }

    double getPosFromRatio(double ratio){
        return Min+ratio*(Max-Min);
    }
    double getRatioFromPos(double pos){
        return (pos-Min)/(Max-Min);
    }
    public void goToRatio(double ratioPos){
        ratioPos = ExtraMath.Clamp(ratioPos,1,0);
        if(servo.getPosition()!= getPosFromRatio(ratioPos)){
            servo.setPosition(getPosFromRatio(ratioPos));
            timer.StartTimer(runtime);//when the timer goes off, the servo should be at the correct position. this needs to be tuned
        }
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
}
