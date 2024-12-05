package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class Wrist{
    static ServoSub wrist;
    static double rangeInDegrees = 270;
    public Wrist(HardwareMap hardwareMap){
        wrist = new ServoSub(hardwareMap,"wrist",0,1);
    }
    public static void runToDegrees(double angle){
        wrist.goToPos(angle/rangeInDegrees);
    }
    public static void runToRad(double angle){
        runToDegrees(Math.toDegrees(angle));
    }
    public static void runToRatio(double ratio){
        wrist.goToPos(ratio);
    }
}
