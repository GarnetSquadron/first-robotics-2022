package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.TTimer;

@TeleOp(name = "timer test")
public class TimerTest extends OpMode {
    TTimer timer  = new TTimer(System::currentTimeMillis);

    @Override
    public void init() {
        timer.StartTimer(1000000);
    }

    @Override
    public void loop() {
        telemetry.addData("time", System.currentTimeMillis());
        telemetry.addData("timer up",timer.timeover());
    }
}
