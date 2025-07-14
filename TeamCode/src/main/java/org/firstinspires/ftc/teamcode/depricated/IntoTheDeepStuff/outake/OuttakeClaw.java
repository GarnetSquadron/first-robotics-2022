package org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff.outake;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardwareClasses.ActionServo;

import java.util.function.DoubleSupplier;

public class OuttakeClaw
{
    public ActionServo claw;

    public OuttakeClaw(HardwareMap hardwareMap, DoubleSupplier time)
    {
        claw = new ActionServo(hardwareMap, "outtake claw", 0.4, 0, 1.5, time);//TODO: tune this
    }

    public Action Close()
    {
        return claw.runToRatio(0);
    }

    public Action Open()
    {
        return claw.runToRatio(1);
    }

    public boolean isOpen()
    {
        return claw.AtMax();
    }
}
