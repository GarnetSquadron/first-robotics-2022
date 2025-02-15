package org.firstinspires.ftc.teamcode.OpModes.autonomi;

import org.firstinspires.ftc.teamcode.Subsystems.Bot;
import org.firstinspires.ftc.teamcode.Subsystems.StaticInfo;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "#FOUR CLIPS#", group = "test")
public class FourClipAuto extends LinearOpMode {
    Bot bot;
    @Override
    public void runOpMode() throws InterruptedException {
        //the position the auto starts at
        Pose2d beginPose = new Pose2d(26, -62, Math.toRadians(90));
        //the class that contains all the subsystems
        bot =  new Bot(hardwareMap,telemetry,this::getRuntime,beginPose);

        //the trajectories that it will drive along the course of the auto

        Vector2d WallPos =  new Vector2d(36,-56);
        Vector2d prepWallPos = new Vector2d(36,-50);
        double SubPos = -30;
        double SampleDistanceX = 4;
        double SampleDistanceY = 4;
        double dropAngle = -50;

        TrajectoryActionBuilder StartDeposit = bot.drive.actionBuilder(beginPose)
                .splineToConstantHeading(new Vector2d(-0,SubPos),90);

        TrajectoryActionBuilder SampGrab1 = StartDeposit.endTrajectory().fresh()
                .setTangent(-Math.PI/2)
                .splineToLinearHeading(new Pose2d(25+SampleDistanceX, -41+SampleDistanceY, Math.toRadians(30)), Math.toRadians(0));

        TrajectoryActionBuilder SampDrop1 = SampGrab1.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(35, -41, Math.toRadians(dropAngle)), Math.toRadians(3));

        TrajectoryActionBuilder SampGrab2 = SampDrop1.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(35+SampleDistanceX, -41+SampleDistanceY, Math.toRadians(30)), Math.toRadians(0));

        TrajectoryActionBuilder SampDrop2 = SampGrab2.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(42, -41, Math.toRadians(dropAngle)), Math.toRadians(3));

        TrajectoryActionBuilder SampGrab3 = SampDrop2.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(42+SampleDistanceX, -41+SampleDistanceY, Math.toRadians(30)), Math.toRadians(0));

        TrajectoryActionBuilder SampDrop3 = SampGrab3.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(41, -41, Math.toRadians(dropAngle)), Math.toRadians(3));

        TrajectoryActionBuilder WallGrab1 = SampDrop3.endTrajectory().fresh()
                .setTangent(-75)
                .splineToLinearHeading(new Pose2d(prepWallPos, Math.toRadians(90)), Math.toRadians(-90))
                .splineToLinearHeading(new Pose2d(WallPos, Math.toRadians(90)), Math.toRadians(-90));

        TrajectoryActionBuilder Deposit1 = WallGrab1.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(5, SubPos, Math.toRadians(90)), Math.toRadians(0));

        TrajectoryActionBuilder WallGrab2 = Deposit1.endTrajectory().fresh()
                .setTangent(-90)
                .splineToLinearHeading(new Pose2d(prepWallPos, Math.toRadians(90)), Math.toRadians(-90))
                .splineToConstantHeading(WallPos,-90);

        TrajectoryActionBuilder Deposit2 = WallGrab2.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(8,SubPos, Math.toRadians(90)), Math.toRadians(0));

        TrajectoryActionBuilder WallGrab3 = Deposit2.endTrajectory().fresh()
                .setTangent(-90)
                .splineToLinearHeading(new Pose2d(prepWallPos, Math.toRadians(90)), Math.toRadians(-90))
                .splineToConstantHeading(WallPos,-90);

        TrajectoryActionBuilder Deposit3 = WallGrab3.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(12,SubPos, Math.toRadians(90)), Math.toRadians(0));

        TrajectoryActionBuilder Park = Deposit3.endTrajectory().fresh()
                .setTangent(-90)
                .splineToConstantHeading(new Vector2d(36,-60),6);

        waitForStart();

        Actions.runBlocking(
                new ParallelAction(
                        bot.UpdateMotorPowers(),
                new SequentialAction(

                        new ParallelAction(
                                bot.outtake.claw.Close(),
                                bot.intake.claw.Open()
                        ),

                                bot.outtake.prepareToPlaceSpec(),

                                StartDeposit.build(),

                                bot.outtake.placeSpecPosV2(),

                                bot.outtake.claw.Open(),

                        new ParallelAction(
                                bot.outtake.SafeVipersDown(),
                                SampGrab1.build()
                        ),

                        new ParallelAction(
                                bot.intake.PoiseToGrab(1),
                                bot.intake.wrist.runToDegrees(120)
                        ),

                        bot.IntakeGrab(),

                        new SleepAction(0.3),

                        SampDrop1.build(),

                        bot.IntakeDropSample(),

                        SampGrab2.build(),

                        bot.IntakeGrab(),

                        SampDrop2.build(),

                        bot.IntakeDropSample(),

                        bot.SafeUndeployIntake(),

                        new ParallelAction(
                                WallGrab1.build(),
                                bot.outtake.grabSpecPos()
                        ),

                        bot.outtake.GrabSpecOfWall(),

                        bot.outtake.prepareToPlaceSpec(),

                        Deposit1.build(),

                        bot.outtake.placeSpecPosV2(),

                        bot.outtake.claw.Open(),

                        new ParallelAction(
                                WallGrab2.build(),
                                bot.outtake.grabSpecPos()
                        ),

                        bot.outtake.GrabSpecOfWall(),

                        bot.outtake.prepareToPlaceSpec(),

                        Deposit2.build(),

                        bot.outtake.placeSpecPosV2(),

                        bot.outtake.claw.Open(),

                        new ParallelAction(
                                WallGrab3.build(),
                                bot.outtake.grabSpecPos()
                        ),

                        bot.outtake.GrabSpecOfWall(),

                        bot.outtake.prepareToPlaceSpec(),

                        Deposit3.build(),

                        bot.outtake.placeSpecPosV2(),

                        bot.outtake.claw.Open(),

                        Park.build()


                )
        )
        );
        StaticInfo.LastOpModeWasAuto = true;
    }
}
