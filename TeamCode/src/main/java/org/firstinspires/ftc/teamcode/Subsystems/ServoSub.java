package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ServoSub {
    public final Servo servo;
    private double Max;
    private double Min;
    public ServoSub(HardwareMap hardwareMap, String name, double min, double max) {
        servo = hardwareMap.get(Servo.class, name);
        Max = max;
        Min = min;
    }

    double getPos(double min, double max, double pos){
        return min+pos*(max-min);
    }
    public void goToPos(double pos){
        servo.setPosition(getPos(Min,Max,pos));
    }
    public void MoveToMax() {
        goToPos(0);
    }
    public void MoveToMin() {
        goToPos(1);
    }
}
