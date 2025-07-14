package org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff.outake;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardwareClasses.ActionServo;

import java.util.function.DoubleSupplier;


public class PrimaryOuttakePivot
{
    public ActionServo pivot;
    //public ActionServo pivot2;

    public Action goToPos(double ratio)
    {
        return new ParallelAction(pivot.runToRatio(ratio)
                //pivot2.runToRatio(ratio)
        );
    }

    public Action gotodegrees(double angle)
    {
        return new ParallelAction(pivot.runToDegrees(angle)
                // pivot2.runToDegrees(angle)
        );
    }

    public PrimaryOuttakePivot(HardwareMap hardwareMap, DoubleSupplier time)
    {
        pivot = new ActionServo(hardwareMap, "primary pivot", 0.9, 0, 2, time);
        //pivot2 = new ActionServo(hardwareMap,"primary pivot 2",0,0.9,2,time);
    }

    public Action BucketPos()
    {
        return gotodegrees(125);
    }

    public Action SpecimenOnWallPos()
    {
        return gotodegrees(225);
    }

    public Action SpecimenOnChamberPos()
    {
        return gotodegrees(0);
    }

    public Action SpecimenOnChamberPosV2()
    {
        return new SequentialAction(gotodegrees(20), gotodegrees(70));
    }

    public Action prepareForSpecimenOnChamberPosV2()
    {
        return gotodegrees(20);
    }

    public Action TransferPos()
    {
        return pivot.runToRatio(0.03);
    }

    public Action outOfTheWayOfIntakePos()
    {
        return pivot.runToRatio(0.2);
    }
}