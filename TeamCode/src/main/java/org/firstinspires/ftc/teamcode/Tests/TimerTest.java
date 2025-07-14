package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.time.TTimer;

@TeleOp(name = "timer test")
@Disabled
public class TimerTest extends OpMode
{
    TTimer timer = new TTimer(this::getRuntime);

    @Override
    public void init()
    {
        timer.StartTimer(100);
    }

    @Override
    public void loop()
    {
        telemetry.addData("time", this.getRuntime());
        telemetry.addData("timer up", timer.timeover());
    }
}
