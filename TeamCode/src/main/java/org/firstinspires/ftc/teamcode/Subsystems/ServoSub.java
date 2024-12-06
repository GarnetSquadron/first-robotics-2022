package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

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

    double getPos(double min, double max, double pos){
        return min+pos*(max-min);
    }
    public void goToPos(double pos){
        if(servo.getPosition()!=getPos(Min, Max, pos)){
            servo.setPosition(getPos(Min, Max, pos));
            timer.StartTimer(runtime);//when the timer goes off, the servo should be at the correct position. this needs to be tuned
        }
    }
    public void MoveToMax() {
        goToPos(0);
    }
    public void MoveToMin() {
        goToPos(1);
    }
//    private double getToothSize(int teeth){
//        return 4.0/(teeth*3.0);
//    }
    public double getPos(){
        return servo.getPosition();
    }
    public boolean targetReached(){
        return timer.timeover()|| !timer.timestarted();
    }
}
