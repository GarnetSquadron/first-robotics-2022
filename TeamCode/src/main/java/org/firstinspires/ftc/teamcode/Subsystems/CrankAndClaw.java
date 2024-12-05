package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class CrankAndClaw {
    IntakePivot pivot;
    public ClawIntake claw;
    public Wrist wrist;
    CrankSlideSubSystem crankSlide;
    public CrankAndClaw(HardwareMap hardwareMap){
        pivot = new IntakePivot(hardwareMap);
        crankSlide = new CrankSlideSubSystem(hardwareMap);
        claw = new ClawIntake(hardwareMap);
        wrist = new Wrist(hardwareMap);
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
