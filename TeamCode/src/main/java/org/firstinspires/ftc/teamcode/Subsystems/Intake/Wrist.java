package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.ServoSub;

public class Wrist{
    public static ServoSub wrist;
    static double rangeInDegrees = 270;
    public Wrist(HardwareMap hardwareMap){
        wrist = new ServoSub(hardwareMap,"wrist",0,1);
    }
    public void runToDegrees(double angle){
        wrist.goToRatio(angle/rangeInDegrees);
    }
    public void runToRad(double angle){
        runToDegrees(Math.toDegrees(angle));
    }
    public void runToRatio(double ratio){
        wrist.goToRatio(ratio);
    }
}
