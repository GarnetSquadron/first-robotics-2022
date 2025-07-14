package org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff.Intake;

import static java.lang.Math.PI;
import static java.lang.Math.pow;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.ExtraMath;
import org.firstinspires.ftc.teamcode.hardwareClasses.ActionServo;

import java.util.function.DoubleSupplier;

public class CrankSlideSubSystem
{
    public final ActionServo CrankL;
    public final ActionServo CrankR;
    private final int SplineTeeth = 25;

    private double getToothSize(int teeth)
    {
        return 4.0 / (teeth * 3.0);
    }

    private final double inchOff = 0.111;
    private final double LeftMin = 0.3333 - getToothSize(SplineTeeth) / 2.0;
    private final double RightMin = 1;
    private final double LeftMax = 1 - getToothSize(SplineTeeth) / 2.0 - inchOff;
    private final double RightMax = 0.3333 - inchOff;
    double maxExtensionInInches = 12;// needs to be updated
    double minExtensionInInches = 0;
    double drivingLinkageLength = 7, secondaryLinkageLength = 13;

    public CrankSlideSubSystem(HardwareMap hardwareMap, DoubleSupplier time)
    {
        CrankL = new ActionServo(hardwareMap, "CrankLeft", LeftMin, LeftMax, 1, time);
        CrankR = new ActionServo(hardwareMap, "CrankRight", RightMin, RightMax, 1, time);
    }

    public Action goToPos(double ratio)
    {
        return new ParallelAction
                (
                        CrankL.runToRatio(ratio),
                        CrankR.runToRatio(ratio)
                );
    }

    public Action goToRad(double angle)
    {
        return new ParallelAction
                (
                        CrankL.runToRad(angle),
                        CrankR.runToRad(angle)
                );
    }

    public double getAngleFromRatio(double ratio)
    {
        return ratio * PI;
    }

    public Action undeploy()
    {
        return goToRad(Math.toRadians(45));
    }

    public Action deploy()
    {
        return goToPos(1);
    }

    public double getExtensionInInches()
    {
        return Math.hypot(drivingLinkageLength * Math.sin(CrankL.getPosInRad()), secondaryLinkageLength) + drivingLinkageLength * Math.cos(CrankL.getPosInRad());
    }

    /**
     * in inches
     *
     * @return
     */
    public Action goToLengthInInches(double length)
    {
        if (length == 0) {
            return goToPos(0);
        }
        double cos = (pow(secondaryLinkageLength, 2) - pow(length, 2) - pow(drivingLinkageLength, 2)) / (2 * length * drivingLinkageLength);
        cos = ExtraMath.Clamp(cos, 1, -1);
        double angle = Math.acos(cos);
        return goToRad(angle);
    }

    public boolean IsExtended()
    {
        return CrankL.AtMax();
    }
}
