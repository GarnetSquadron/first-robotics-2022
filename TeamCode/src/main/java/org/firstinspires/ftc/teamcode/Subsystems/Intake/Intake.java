package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    IntakePivot pivot;
    public static IntakeClawSub claw;
    public Wrist wrist;
    public CrankSlideSubSystem crankSlide;
    public Intake(HardwareMap hardwareMap){
        pivot = new IntakePivot(hardwareMap);
        crankSlide = new CrankSlideSubSystem(hardwareMap);
        claw = new IntakeClawSub(hardwareMap);
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
    public boolean targetReached(){
        return crankSlide.targetReached()&&pivot.pivot.targetReached()&&claw.SERVO.targetReached()&&wrist.wrist.targetReached();
    }



}
