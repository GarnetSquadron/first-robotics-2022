package org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff.Intake;

import com.acmerobotics.roadrunner.Action;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardwareClasses.ActionServo;

import java.util.function.DoubleSupplier;

public class IntakePivot extends SubsystemBase
{
    public ActionServo pivot;

    public IntakePivot(HardwareMap hardwaremap, DoubleSupplier time)
    {
        pivot = new ActionServo(hardwaremap, "pivot", 1, 0, 0.5, time);
        //hardwaremap.get(Servo.class, "pivot");

    }

    public Action deploy()
    {
        return pivot.runToRatio(1);
    }

    public Action undeploy()
    {
        return pivot.runToRatio(0);
    }

    public Action poiseForTheGrab()
    {
        return pivot.runToRatio(0.95);
    }

    public Action poiseForTheGrabAuto()
    {
        return pivot.runToRatio(0.85);
    }
}
