package org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff.Intake;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardwareClasses.ActionServo;

import java.util.function.DoubleSupplier;

public class Wrist
{
    public ActionServo wrist;

    public Wrist(HardwareMap hardwareMap, DoubleSupplier time)
    {
        wrist = new ActionServo(hardwareMap, "wrist", 0, 0.6666, time);
    }

    public Action runToDegrees(double angle)
    {
        return wrist.runToDegrees(angle);
    }

    public Action runToRad(double angle)
    {
        return wrist.runToRad(angle);
    }

    public Action runToRatio(double ratio)
    {
        return wrist.runToRatio(ratio);
    }
}
