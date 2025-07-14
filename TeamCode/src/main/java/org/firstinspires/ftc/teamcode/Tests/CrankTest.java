package org.firstinspires.ftc.teamcode.Tests;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff.Intake.CrankSlideSubSystem;


@TeleOp(name = "crankTest", group = "tests")
@Disabled
public class CrankTest extends OpMode
{
    CrankSlideSubSystem crank;
    TelemetryPacket packet = new TelemetryPacket();

    @Override
    public void init()
    {
        crank = new CrankSlideSubSystem(hardwareMap, this::getRuntime);
    }

    @Override
    public void loop()
    {
        if (gamepad1.x) {
            crank.undeploy().run(packet);
        }
        if (gamepad1.y) {
            crank.deploy().run(packet);
        }
    }
}
