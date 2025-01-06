package org.firstinspires.ftc.teamcode.OpModes.autonomi;

import org.firstinspires.ftc.teamcode.Subsystems.ActionBot;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "AUTOTESTINGSAMPLE", group = "test")
public class AutoTestingSAMPLE extends LinearOpMode {
    ActionBot bot;
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        Pose2d beginPose = new Pose2d(-26, -62, Math.toRadians(180));
        bot =  new ActionBot(hardwareMap,telemetry,this::getRuntime,beginPose);
        Pose2d depositSpot = new Pose2d(-55, -55, Math.toRadians(45));

        Action Deposit = bot.drive.actionBuilder(beginPose)
                .splineToLinearHeading(depositSpot, 10)
                .build();

        Action DepositTan = bot.drive.actionBuilder(beginPose)
                .setTangent(-90)
                .splineToLinearHeading(new Pose2d(-55, -55, Math.toRadians(45)), 10)
                .build();

        Action Sample1 = bot.drive.actionBuilder(beginPose)
                .splineToLinearHeading(new Pose2d(-48, -48, Math.toRadians(90)), 45)
                .build();

        Action Sample2 = bot.drive.actionBuilder(beginPose)
                .splineToLinearHeading(new Pose2d(-58, -48, Math.toRadians(90)), 90)
                .build();

        Action Sample3 = bot.drive.actionBuilder(beginPose)
                .splineToLinearHeading(new Pose2d(-50, -46, Math.toRadians(133)), 90)
                .build();

        Action Park = bot.drive.actionBuilder(beginPose)
                .splineToLinearHeading(new Pose2d(-24, -12, Math.toRadians(0)), 0)
                .build();


        Actions.runBlocking(
                new SequentialAction(

                        bot.outtake.claw.Close(),

                        bot.intake.claw.Open(),

                        bot.BasketDrop(),

                        new ParallelAction(
                                Deposit
                        ),

                        bot.outtake.claw.Open(),

                        bot.outtake.vipers.Down(),
                        bot.outtake.TransferPos(),

                        new ParallelAction(

                                Sample1,
                                bot.intake.deploy(1)
                        ),

                        bot.intake.claw.Close(),

                        bot.intake.undeploy(),

                        bot.outtake.claw.Close(),

                        bot.intake.claw.Open(),

                        bot.BasketDrop(),

                        new ParallelAction(

                                Deposit
                        ),

                        bot.outtake.claw.Open(),

                        bot.outtake.vipers.Down(),
                        bot.outtake.TransferPos(),

                        new ParallelAction(


                                Sample2,
                                bot.intake.deploy(1)),

                        bot.intake.claw.Close(),

                        bot.intake.undeploy(),

                        bot.outtake.claw.Close(),

                        bot.intake.claw.Open(),

                        bot.BasketDrop(),

                        new ParallelAction(

                                Deposit
                        ),

                        bot.outtake.claw.Open(),

                        bot.outtake.vipers.Down(),
                        bot.outtake.TransferPos(),

                        new ParallelAction(


                                Sample3,
                                bot.intake.deploy(1)),

                        bot.intake.claw.Close(),

                        bot.intake.undeploy(),

                        bot.outtake.claw.Close(),

                        bot.intake.claw.Open(),

                        bot.BasketDrop(),

                        new ParallelAction(

                                DepositTan
                        ),

                        bot.outtake.claw.Open(),

                        bot.outtake.vipers.Down(),
                        bot.outtake.TransferPos(),

                        new ParallelAction(
                                Park
                        )

                )
        );
    }
}
