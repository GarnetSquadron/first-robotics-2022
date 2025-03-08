package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.Bot;

@Autonomous(name = "Win Worlds")
public class Worlds extends LinearOpMode {
    Bot bot;
    public void win(){
        while (true){
            bot.drive.setDrivePowers(new PoseVelocity2d(new Vector2d(0,0),100));
        }
    }

    @Override
    public void runOpMode() throws InterruptedException {
        bot = new Bot(hardwareMap,telemetry,this::getRuntime);
        win();
    }
}
