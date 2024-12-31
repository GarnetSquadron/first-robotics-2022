package org.firstinspires.ftc.teamcode.OpModes.autonomi;

import org.firstinspires.ftc.teamcode.Subsystems.ActionBot;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.CrankSlideSubSystem;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeClawSub;
import org.firstinspires.ftc.teamcode.Subsystems.outake.Outtake;
import org.firstinspires.ftc.teamcode.Subsystems.outake.ViperSlidesSubSystem;
import org.firstinspires.ftc.teamcode.Subsystems.outake.OuttakeClaw;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
@Autonomous(name = "AUTOTESTING", group = "test")
public class AutoTesing extends LinearOpMode {
    ActionBot bot;
    double pushY = -45;
    double pushX = 47;
    @Override
    public void runOpMode() throws InterruptedException {
        bot =  new ActionBot(hardwareMap,telemetry,this::getRuntime);
        Pose2d beginPose = new Pose2d(0,0,0);
        Pose2d depositSpot = new Pose2d(-55, -55, Math.toRadians(45));

        Action Deposit = bot.drive.actionBuilder(beginPose)
                .splineToLinearHeading(depositSpot, 10)
                .build();

        Action DepositTan = bot.drive.actionBuilder(depositSpot)
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


/*
          AVAIL ACTIONS

            *INTAKE*
crankSlideSubSystem.Crankin(),
crankSlideSubSystem.Crankout(),
intakeClawSub.InClawOpen(),
intakeClawSub.InClawClose(),

           *OUTTAKE*
viperSlidesSubSystem.Viperup(),
viperSlidesSubSystem.Viperdown(),
outtakeClaw.OutClawClose(),
outtakeClaw.OutClawOpen(),
outtake.OuttakeBucket(),

           *EXTRA*
outtake.ClawTransfer(),

*/




        Actions.runBlocking(

                new SequentialAction(

                        bot.outtake.claw.Close(),

                        bot.intake.claw.Open(),

                        new ParallelAction(

                                Deposit,
                                bot.outtake.BasketDrop()),

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
                                bot.outtake.BasketDrop()),

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
                                bot.outtake.BasketDrop()),

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
                                bot.outtake.BasketDrop()),

                        bot.outtake.claw.Open(),

                        new ParallelAction(
                                bot.outtake.vipers.Down(),
                                bot.outtake.TransferPos(),
                                Park)
                )
        );
    }
}
