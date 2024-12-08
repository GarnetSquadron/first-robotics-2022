package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class CrankAndClaw {
    IntakePivot pivot;
    public static ClawIntake claw;
    public Wrist wrist;
    public CrankSlideSubSystem crankSlide;
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
    public void goToDefaultPos(){
        undeploy();
        wrist.runToRatio(0);
    }



}
