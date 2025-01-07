package org.firstinspires.ftc.teamcode.OpModes.autonomi;

import org.firstinspires.ftc.teamcode.Subsystems.Bot;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "AUTOTESTINGCLIP", group = "test")
public class AutoTestingCLIP extends LinearOpMode {
    Bot bot;
    double pushY = -45;
    double pushX = 47;
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        Pose2d beginPose = new Pose2d(26,-62, Math.toRadians(90));
        bot =  new Bot(hardwareMap,telemetry,this::getRuntime,beginPose);
        Pose2d clipgrab = new Pose2d (56,-60, 90);

        Action Homegrab = bot.drive.actionBuilder(beginPose)
                .setTangent(-90)
                .splineToLinearHeading(clipgrab, 6)
                .build();

        Action Push = bot.drive.actionBuilder(beginPose)
                .splineToConstantHeading(new Vector2d(36,-30),45)
                .splineToConstantHeading(new Vector2d(34,-0),0)
                .splineToConstantHeading(new Vector2d(40,-0),5)
                .splineToConstantHeading(new Vector2d(pushX, pushY),2)
                .splineToConstantHeading(new Vector2d(43,-0),0)
                .splineToConstantHeading(new Vector2d(pushX, pushY),2)
                .splineToConstantHeading(new Vector2d(52,-0),0)
                .splineToConstantHeading(new Vector2d(pushX, pushY),2)
                .build();

        Action Deposit1 = bot.drive.actionBuilder(beginPose)
                .splineToConstantHeading(new Vector2d(-0,-34),90)
                .build();

        Action Deposit2 = bot.drive.actionBuilder(beginPose)
                .splineToConstantHeading(new Vector2d(5,-34),0)
                .build();

        Action Deposit3 = bot.drive.actionBuilder(beginPose)
                .splineToConstantHeading(new Vector2d(7,-34),0)
                .build();

        Action Deposit4 = bot.drive.actionBuilder(beginPose)
                .splineToConstantHeading(new Vector2d(9,-34),0)
                .build();


        Action Park = bot.drive.actionBuilder(beginPose)
                .splineToLinearHeading(clipgrab, 6)
                .build();


        Actions.runBlocking(

                new SequentialAction(

                )
        );
    }
}
