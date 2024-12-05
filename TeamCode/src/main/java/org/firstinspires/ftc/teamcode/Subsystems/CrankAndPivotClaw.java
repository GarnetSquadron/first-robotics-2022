package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.CrankSlideSubSystem;
import org.firstinspires.ftc.teamcode.Subsystems.IntakePivot;

public class CrankAndPivotClaw {
    IntakePivot pivot;
    CrankSlideSubSystem crankSlide;
    public CrankAndPivotClaw(HardwareMap hardwareMap){
        pivot = new IntakePivot(hardwareMap);
        crankSlide = new CrankSlideSubSystem(hardwareMap);
    }
    public void deploy(double distance){
        crankSlide.goToPos(distance);
        pivot.deploy();
    }
    public void undeploy(){
        crankSlide.undeploy();
        pivot.undeploy();
    }
}
