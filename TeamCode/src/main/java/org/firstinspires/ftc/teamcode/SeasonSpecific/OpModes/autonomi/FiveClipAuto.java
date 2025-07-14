package org.firstinspires.ftc.teamcode.SeasonSpecific.OpModes.autonomi;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.StaticInfo;
import org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff.Bot;

@Autonomous(name = "#FIVE CLIPS#", group = "test")
@Disabled
public class FiveClipAuto extends LinearOpMode
{
    Bot bot;

    @Override
    public void runOpMode() throws InterruptedException
    {
        //the position the auto starts at
        Pose2d beginPose = new Pose2d(26, -62, Math.toRadians(90));
        //the class that contains all the subsystems
        bot = new Bot(hardwareMap, telemetry, this::getRuntime, beginPose);

        //the trajectories that it will drive along the course of the auto

        TrajectoryActionBuilder StartDeposit = bot.drive.actionBuilder(beginPose)
                .splineToConstantHeading(new Vector2d(-5, -34), 90);

        TrajectoryActionBuilder SampGrab1 = StartDeposit.endTrajectory().fresh()
                .setTangent(182)
                .splineToLinearHeading(new Pose2d(30, -41, Math.toRadians(30)), Math.toRadians(0));

        TrajectoryActionBuilder SampDrop1 = SampGrab1.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(35, -41, Math.toRadians(-40)), Math.toRadians(3));

        TrajectoryActionBuilder SampGrab2 = SampDrop1.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(35, -41, Math.toRadians(30)), Math.toRadians(0));

        TrajectoryActionBuilder SampDrop2 = SampGrab2.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(42, -41, Math.toRadians(-40)), Math.toRadians(3));

        TrajectoryActionBuilder SampGrab3 = SampDrop2.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(42, -41, Math.toRadians(30)), Math.toRadians(0));

        TrajectoryActionBuilder SampDrop3 = SampGrab3.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(41, -41, Math.toRadians(-40)), Math.toRadians(3));

        TrajectoryActionBuilder WallGrab1 = SampDrop3.endTrajectory().fresh()
                .setTangent(-75)
                .splineToLinearHeading(new Pose2d(36, -60, Math.toRadians(90)), Math.toRadians(3));

        TrajectoryActionBuilder Deposit1 = WallGrab1.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(0, -34, Math.toRadians(90)), Math.toRadians(0));

        TrajectoryActionBuilder WallGrab2 = Deposit1.endTrajectory().fresh()
                .setTangent(-90)
                .splineToConstantHeading(new Vector2d(36, -60), 6);

        TrajectoryActionBuilder Deposit2 = WallGrab2.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(5, -34, Math.toRadians(90)), Math.toRadians(0));

        TrajectoryActionBuilder WallGrab3 = Deposit2.endTrajectory().fresh()
                .setTangent(-90)
                .splineToConstantHeading(new Vector2d(36, -60), 6);

        TrajectoryActionBuilder Deposit3 = WallGrab3.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(8, -34, Math.toRadians(90)), Math.toRadians(0));

        TrajectoryActionBuilder WallGrab4 = Deposit3.endTrajectory().fresh()
                .setTangent(-90)
                .splineToConstantHeading(new Vector2d(36, -60), 6);

        TrajectoryActionBuilder Deposit4 = WallGrab4.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(12, -34, Math.toRadians(90)), Math.toRadians(0));

        TrajectoryActionBuilder Park = Deposit4.endTrajectory().fresh()
                .setTangent(-90)
                .splineToConstantHeading(new Vector2d(36, -60), 6);

        waitForStart();

        Actions.runBlocking(
                new ParallelAction(
                        bot.UpdateMotorPowers(),
                        new SequentialAction(

                                new ParallelAction(
                                        bot.outtake.claw.Close(),
                                        bot.intake.claw.Open()
                                ),

                                new ParallelAction(
                                        StartDeposit.build(),
                                        bot.outtake.placeSpec()
                                ),

                                bot.outtake.claw.Open(),

                                new ParallelAction(
                                        bot.outtake.SafeVipersDown(),
                                        SampGrab1.build(),
                                        bot.intake.PoiseToGrab(1),
                                        bot.intake.wrist.runToDegrees(-70)
                                ),

                                bot.IntakeGrab(),

                                SampDrop1.build(),

                                bot.IntakeDropSample(),

                                SampGrab2.build(),

                                bot.IntakeGrab(),

                                SampDrop2.build(),

                                bot.IntakeDropSample(),

                                SampGrab3.build(),

                                bot.IntakeGrab(),

                                SampDrop3.build(),

                                bot.IntakeDropSample(),

                                new ParallelAction(
                                        WallGrab1.build(),
                                        bot.outtake.grabSpecPos()
                                ),

                                bot.outtake.claw.Close(),

                                new ParallelAction(
                                        Deposit1.build(),
                                        bot.outtake.placeSpec()
                                ),

                                bot.outtake.claw.Open(),

                                new ParallelAction(
                                        WallGrab2.build(),
                                        bot.outtake.grabSpecPos()
                                ),

                                bot.outtake.claw.Close(),

                                new ParallelAction(
                                        Deposit2.build(),
                                        bot.outtake.placeSpec()
                                ),

                                bot.outtake.claw.Open(),

                                new ParallelAction(
                                        WallGrab3.build(),
                                        bot.outtake.grabSpecPos()
                                ),

                                bot.outtake.claw.Close(),

                                new ParallelAction(
                                        Deposit3.build(),
                                        bot.outtake.placeSpec()
                                ),

                                bot.outtake.claw.Open(),


                                new ParallelAction(
                                        WallGrab4.build(),
                                        bot.outtake.grabSpecPos()
                                ),

                                bot.outtake.claw.Close(),

                                new ParallelAction(
                                        Deposit4.build(),
                                        bot.outtake.placeSpec()
                                ),

                                bot.outtake.claw.Open(),


                                Park.build()


                        )
                )
        );
        StaticInfo.LastOpModeWasAuto = true;
    }
}
