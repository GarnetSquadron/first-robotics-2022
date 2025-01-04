package org.firstinspires.ftc.teamcode.OpModes.autonomi;

import org.firstinspires.ftc.teamcode.Subsystems.ActionBot;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "AUTOTESTINGSAMPLE", group = "test")
public class AutoTestingCLIP extends LinearOpMode {
    ActionBot bot;
    double pushY = -45;
    double pushX = 47;
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        Pose2d beginPose = new Pose2d(-26, -62, Math.toRadians(180));
        bot =  new ActionBot(hardwareMap,telemetry,this::getRuntime,beginPose);
        Pose2d clipgrab = new Pose2d
                .setTangent(-90)
                .splineToConstantHeading(new Vector2d(56,-60),6);

        Action Home = bot.drive.actionBuilder(beginPose)
                .setTangent(-90)
                .splineToLinearHeading(clipgrab, 6)
                .build();

        Action Push = bot.drive.actionBuilder(beginPose)

                .build();

        Action Deposit1 = bot.drive.actionBuilder(beginPose)

                .build();

        Action Deposit2 = bot.drive.actionBuilder(beginPose)

                .build();

        Action Deposit3 = bot.drive.actionBuilder(beginPose)

                .build();

        Action Deposit4 = bot.drive.actionBuilder(beginPose)

                .build();

        Action Sample1 = bot.drive.actionBuilder(beginPose)

                .build();

        Action Sample2 = bot.drive.actionBuilder(beginPose)

                .build();

        Action Sample3 = bot.drive.actionBuilder(beginPose)

                .build();

        Action Park = bot.drive.actionBuilder(beginPose)

                .build();


        Actions.runBlocking(

                new SequentialAction(

                        bot.outtake.claw.Close(),

                        bot.intake.claw.Open(),

                        new ParallelAction(

                                Deposit,
                                bot.BasketDrop()),

                        bot.outtake.claw.Open(),

                        new ParallelAction(

                                bot.outtake.vipers.Down(),
                                bot.outtake.TransferPos(),
                                Sample1,
                                bot.intake.deploy(1)),

                        bot.intake.claw.Close(),

                        bot.intake.undeploy(),

                        bot.outtake.claw.Close(),

                        bot.intake.claw.Open(),

                        new ParallelAction(

                                Deposit,
                                bot.BasketDrop()),

                        bot.outtake.claw.Open(),

                        new ParallelAction(

                                bot.outtake.vipers.Down(),
                                bot.outtake.TransferPos(),
                                Sample2,
                                bot.intake.deploy(1)),

                        bot.intake.claw.Close(),

                        bot.intake.undeploy(),

                        bot.outtake.claw.Close(),

                        bot.intake.claw.Open(),

                        new ParallelAction(

                                Deposit,
                                bot.BasketDrop()),

                        bot.outtake.claw.Open(),

                        new ParallelAction(

                                bot.outtake.vipers.Down(),
                                bot.outtake.TransferPos(),
                                Sample3,
                                bot.intake.deploy(1)),

                        bot.intake.claw.Close(),

                        bot.intake.undeploy(),

                        bot.outtake.claw.Close(),

                        bot.intake.claw.Open(),

                        new ParallelAction(

                                DepositTan,
                                bot.BasketDrop()),

                        bot.outtake.claw.Open(),

                        new ParallelAction(
                                bot.outtake.vipers.Down(),
                                bot.outtake.TransferPos(),
                                Park)
                )
        );
    }
}
