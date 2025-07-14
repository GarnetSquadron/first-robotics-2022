package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff.Intake.Wrist;

public class wristTest extends OpMode
{

    Wrist s;

    @Override
    public void init()
    {
        s = new Wrist(hardwareMap, this::getRuntime);
    }

    @Override
    public void loop()
    {
        s.runToRatio(gamepad1.left_stick_x);
    }
}
