package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    IntakePivot pivot;
    public static IntakeClawSub claw;
    public Wrist wrist;
    public double littleClawArmThingyLength = 5;
    public double CrankWidth = 9.57;
    public double leftClawLength = CrankWidth/2.0;
    public double rightClawLength = CrankWidth/2.0;
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
        wrist.runToRatio(0);
        undeploy();
    }
    public boolean targetReached(){
        return crankSlide.targetReached()&&pivot.pivot.targetReached()&&claw.SERVO.targetReached()&&wrist.wrist.targetReached();
    }



}
