package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff.outake.OuttakeClaw;

@TeleOp(name = "OuttakeClawTest", group = "test")
@Disabled
public class OuttakeClawTest extends OpMode
{
    OuttakeClaw claw;

    @Override
    public void loop()
    {
//        if (gamepad1.a) {
//            claw.open();
//        }
//        if (gamepad1.b) {
//            claw.close();
//        }
    }

    public void init()
    {
        //claw = new OuttakeClaw(hardwareMap);
    }
}