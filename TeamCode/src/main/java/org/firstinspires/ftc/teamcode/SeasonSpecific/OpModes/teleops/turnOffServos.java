package org.firstinspires.ftc.teamcode.SeasonSpecific.OpModes.teleops;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff.Bot;

@TeleOp(name = "ATurnOffTheServos")//the A is for the alphabetical order
@Disabled
public class turnOffServos extends LinearOpMode
{
    Bot bot;

    @Override
    public void runOpMode() throws InterruptedException
    {
        bot = new Bot(hardwareMap, telemetry, this::getRuntime);
        waitForStart();
        bot.turnOffTheServos();
        sleep(100000);
    }
}
