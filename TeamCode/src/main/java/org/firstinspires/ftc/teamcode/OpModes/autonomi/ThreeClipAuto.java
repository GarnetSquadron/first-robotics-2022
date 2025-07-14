package org.firstinspires.ftc.teamcode.OpModes.autonomi;

import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MiscActions.CancelableAction;
import org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff.Bot;
import org.firstinspires.ftc.teamcode.Subsystems.StaticInfo;

@Autonomous(name = "#THREE CLIPS#", group = "test")
@Disabled
public class ThreeClipAuto extends LinearOpMode
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

        Vector2d WallPos = new Vector2d(34, -54.75);
        Vector2d prepWallPos = new Vector2d(34, -52);
        double SubPos = -29.5;
        double prepSubPos = -40;
        double SampleDistanceX = 4;
        double SampleDistanceY = 5;
        double dropAngle = -60;

        TrajectoryActionBuilder StartDeposit = bot.drive.actionBuilder(beginPose)
                .splineToLinearHeading(new Pose2d(-2.5, prepSubPos, Math.toRadians(90)), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-2.5, SubPos), 90);

        TrajectoryActionBuilder SampGrab1 = StartDeposit.endTrajectory().fresh()
                .setTangent(-Math.PI / 2)
                .splineToLinearHeading(new Pose2d(25 + SampleDistanceX, -41 + SampleDistanceY, Math.toRadians(30)), Math.toRadians(0))
                .afterDisp(20, new ParallelAction(
                        bot.intake.PoiseToGrab(1),
                        bot.intake.wrist.runToDegrees(120)
                ));
//auto route
        TrajectoryActionBuilder SampDrop1 = SampGrab1.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(35, -45, Math.toRadians(dropAngle)), Math.toRadians(3));

        TrajectoryActionBuilder SampGrab2 = SampDrop1.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(35 + SampleDistanceX, -41 + SampleDistanceY, Math.toRadians(30)), Math.toRadians(0));

        TrajectoryActionBuilder SampDrop2 = SampGrab2.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(42, -41, Math.toRadians(dropAngle)), Math.toRadians(3));

        TrajectoryActionBuilder SampGrab3 = SampDrop2.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(42 + SampleDistanceX, -41 + SampleDistanceY, Math.toRadians(30)), Math.toRadians(0));

        TrajectoryActionBuilder SampDrop3 = SampGrab3.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(41, -41, Math.toRadians(dropAngle)), Math.toRadians(3));

        TrajectoryActionBuilder WallGrab1 = SampDrop3.endTrajectory().fresh()
                .setTangent(-75)
                .splineToLinearHeading(new Pose2d(prepWallPos, Math.toRadians(90)), Math.toRadians(-90))
                .waitSeconds(0.5)
                .splineToLinearHeading(new Pose2d(WallPos, Math.toRadians(90)), Math.toRadians(-90), new TranslationalVelConstraint(10));

        TrajectoryActionBuilder Deposit1 = WallGrab1.endTrajectory().fresh()
                .setTangent(90)
                .splineToLinearHeading(new Pose2d(-0.5, SubPos, Math.toRadians(90)), Math.toRadians(90), new TranslationalVelConstraint(30));

        TrajectoryActionBuilder WallGrab2 = Deposit1.endTrajectory().fresh()
                .setTangent(-90)
                .splineToLinearHeading(new Pose2d(prepWallPos.minus(new Vector2d(0.5, 0)), Math.toRadians(90)), Math.toRadians(-90))
                .waitSeconds(0.5)
                .splineToLinearHeading(new Pose2d(WallPos.minus(new Vector2d(0.5, 0)), Math.toRadians(90)), Math.toRadians(-90), new TranslationalVelConstraint(10));

        TrajectoryActionBuilder Deposit2 = WallGrab2.endTrajectory().fresh()
                .setTangent(90)
                .splineToLinearHeading(new Pose2d(1.5, SubPos, Math.toRadians(90)), Math.toRadians(90), new TranslationalVelConstraint(30));

        TrajectoryActionBuilder WallGrab3 = Deposit2.endTrajectory().fresh()
                .setTangent(-90)
                .splineToLinearHeading(new Pose2d(prepWallPos, Math.toRadians(90)), Math.toRadians(-90))
                .waitSeconds(0.5)
                .splineToConstantHeading(WallPos, Math.toRadians(-90));

        TrajectoryActionBuilder Deposit3 = WallGrab3.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(-4, SubPos - 30, Math.toRadians(90)), Math.toRadians(90))
                .waitSeconds(0.1)
                .splineToLinearHeading(new Pose2d(-4, SubPos, Math.toRadians(90)), Math.toRadians(90));

        TrajectoryActionBuilder Park = Deposit3.endTrajectory().fresh()
                .setTangent(-90)
                .splineToConstantHeading(new Vector2d(36, -60), 6);
        CancelableAction auto = new CancelableAction(
                new SequentialAction(
                        new ParallelAction(
                                bot.outtake.claw.Close(),
                                bot.intake.claw.Open()
                        ),

                        bot.outtake.prepareToPlaceSpec(),

                        StartDeposit.build(),

                        bot.outtake.placeSpecV2(),

                        bot.outtake.claw.Open(),

                        new ParallelAction(
                                SampGrab1.build()

                        ),

                        new ParallelAction(
                        ),
                        new SleepAction(0.5),

                        bot.IntakeGrab(),

                        //new SleepAction(1),

                        SampDrop1.build(),

                        bot.IntakeDropSample(),

                        //                        SampGrab2.build(),
                        //
                        //                        bot.IntakeGrab(),
                        //
                        //                        SampDrop2.build(),
                        //
                        //                        bot.IntakeDropSample(),

                        new SequentialAction(
                                bot.SafeUndeployIntake(),
                                bot.outtake.grabSpecPos()
                        ),
                        new ParallelAction(
                                WallGrab1.build()
                        ),

                        bot.outtake.prepareToGrabSpecOffWall(),

                        bot.outtake.prepareToPlaceSpec(),

                        Deposit1.build(),

                        bot.outtake.placeSpecV2(),

                        bot.outtake.claw.Open(),

                        new ParallelAction(
                                WallGrab2.build(),
                                bot.outtake.grabSpecPos()
                        ),

                        bot.outtake.prepareToGrabSpecOffWall(),

                        bot.outtake.prepareToPlaceSpec(),

                        Deposit2.build(),

                        bot.outtake.placeSpecV2(),

                        bot.outtake.claw.Open()

                        //                        new ParallelAction(
                        //                                WallGrab3.build(),
                        //                                bot.outtake.grabSpecPos()
                        //                        ),
                        //
                        //                        bot.outtake.grabSpecOfWall(),
                        //
                        //                        bot.outtake.prepareToPlaceSpec(),
                        //
                        //                        Deposit3.build(),
                        //
                        //                        bot.outtake.placeSpecPosV2(),
                        //
                        //                        bot.outtake.claw.Open(),
                        //
                        //                        Park.build()
                ),
                bot.drive.Stop()

        );

        waitForStart();

        Actions.runBlocking(
                new ParallelAction(
                        bot.UpdateMotorPowers(),
                        auto,
                        new SequentialAction(
                                new SleepAction(28),
                                new InstantAction(auto::failover),
                                bot.outtake.vipers.Down()
                        )
                )
        );
        StaticInfo.LastOpModeWasAuto = true;
    }
}
