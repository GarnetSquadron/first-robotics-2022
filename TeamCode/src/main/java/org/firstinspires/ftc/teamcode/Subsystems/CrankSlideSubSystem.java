package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class CrankSlideSubSystem extends SubsystemBase {
    private final Servo CrankL;
    private final Servo CrankR;
    private int SplineTeeth = 25;
    private double getToothSize(int teeth){
        return 4.0/(teeth*3.0);
    }
    private double LeftMax = 0.3333;
    private double RightMax = 1-getToothSize(SplineTeeth);
    private double LeftMin = 1;
    private double RightMin = 0.3333-getToothSize(SplineTeeth);

    public CrankSlideSubSystem(HardwareMap hardwareMap, String name1, String name2) {
        CrankL = hardwareMap.get(Servo.class, name1);
        CrankR = hardwareMap.get(Servo.class, name2);

    }
    double getPos(double min, double max, double pos){
        return min+pos*(max-min);
    }
    public void goToPos(double pos){
        CrankL.setPosition(getPos(LeftMin,LeftMax,pos));
        CrankR.setPosition(getPos(RightMin,RightMax,pos));
    }
    public void Extend() {
        goToPos(0);
    }
    public void Return() {
        goToPos(1);
    }
}
