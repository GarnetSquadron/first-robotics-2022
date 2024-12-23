package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.function.DoubleSupplier;

public class Intake {
    IntakePivot pivot;
    public static IntakeClawSub claw;
    public Wrist wrist;
    public double littleClawArmThingyLength = 5;
    public double CrankWidth = 9.57;
    public double leftClawLength = CrankWidth/2.0;
    public double rightClawLength = CrankWidth/2.0;
    public CrankSlideSubSystem crankSlide;
    public Intake(HardwareMap hardwareMap, DoubleSupplier time){
        pivot = new IntakePivot(hardwareMap,time);
        crankSlide = new CrankSlideSubSystem(hardwareMap,time);
        claw = new IntakeClawSub(hardwareMap,time);
        wrist = new Wrist(hardwareMap,time);
    }
    public Action deploy(double distance){
        return new ParallelAction(
            crankSlide.goToPos(distance),
            pivot.deploy
        );
    }
    public Action undeploy(){
        return new ParallelAction(
                crankSlide.undeploy,
                pivot.undeploy
        );
    }
    public Action DefaultPos(){
        return new ParallelAction(

        );
    }



}
