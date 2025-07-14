package org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff.outake;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardwareClasses.ActionServo;

import java.util.function.DoubleSupplier;

public class SecondaryOuttakePivot
{
    public ActionServo pivot;

    public SecondaryOuttakePivot(HardwareMap hardwareMap, DoubleSupplier time)
    {
        pivot = new ActionServo(hardwareMap, "secondary pivot", 0.1, 1, 0.5, time);
    }

    public Action AutoBucketPos()
    {
        return pivot.runToRatio(1);
    }

    public Action TeleBucketPos()
    {
        return pivot.runToRatio(0.80);
    }

    public Action SpecimenOnWallPos()
    {
        return pivot.runToDegrees(50);
    }

    public Action SpecimenOnChamberPos()
    {
        return pivot.runToRatio(0.5);
    }

    public Action prepareForSpecimenOnChamberPos()
    {
        return pivot.runToDegrees(90);
    }

    public Action outOfTheWayOfIntakePos()
    {
        return pivot.runToRatio(0.5);
    }

    public Action TransferPos()
    {
        return pivot.runToDegrees(15);
    }

    //    public boolean grabbingOffWall(){
//        return pivot.get
//    }
    public Action goToDegrees(double angle)
    {
        return pivot.runToDegrees(103 - angle);
    }
}
