package org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff.Intake;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.function.DoubleSupplier;

public class Intake
{
    public IntakePivot pivot;
    public IntakeClawSub claw;
    public Wrist wrist;
    public double littleClawArmThingyLength = 5;
    public double CrankWidth = 9.57;
    public double leftClawLength = CrankWidth / 2.0;
    public double rightClawLength = CrankWidth / 2.0;
    public CrankSlideSubSystem crankSlide;

    public Intake(HardwareMap hardwareMap, DoubleSupplier time)
    {
        pivot = new IntakePivot(hardwareMap, time);
        crankSlide = new CrankSlideSubSystem(hardwareMap, time);
        claw = new IntakeClawSub(hardwareMap, time);
        wrist = new Wrist(hardwareMap, time);
    }

    public Action deploy(double distance)
    {
        return new ParallelAction(
                crankSlide.goToPos(distance),
                pivot.deploy()
        );
    }

    public Action undeploy()
    {
        return new ParallelAction(
                crankSlide.undeploy(),
                pivot.undeploy()
        );
    }

    public Action DefaultPos()
    {
        return new ParallelAction(
                undeploy(),
                wrist.runToRad(0)
                //claw.Open()
        );
    }

    public Action PoiseToGrab(double distance)
    {
        return new ParallelAction(
                crankSlide.goToPos(distance),
                pivot.poiseForTheGrab()
        );
    }

    public Action PoiseToGrabAuto(double distance)
    {
        return new ParallelAction(
                crankSlide.goToPos(distance),
                pivot.poiseForTheGrabAuto()
        );
    }


}
