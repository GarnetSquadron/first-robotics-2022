package org.firstinspires.ftc.teamcode.OpModes.autonomi;

import org.firstinspires.ftc.teamcode.MiscActions.CancelableAction;
import org.firstinspires.ftc.teamcode.MiscActions.LoopAction;
import org.firstinspires.ftc.teamcode.MiscActions.WaitForConditionAction;
import org.firstinspires.ftc.teamcode.Subsystems.Bot;
import org.firstinspires.ftc.teamcode.Subsystems.StaticInfo;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
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
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "#CLIP TESTING#", group = "test")
public class Autocliptesting extends LinearOpMode {
    Bot bot;
    @Override
    public void runOpMode() throws InterruptedException {
        //telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        //the position the auto starts at
        Pose2d beginPose = new Pose2d(26, -62, Math.toRadians(90));
        //the class that contains all the subsystems
        bot =  new Bot(hardwareMap,telemetry,this::getRuntime,beginPose);

        //the trajectories that it will drive along the course of the auto

        Vector2d WallPos =  new Vector2d(34,-54.75);
        Vector2d prepWallPos = new Vector2d(34,-52);
        double SubPos = -40;
        double SubDriveInPos = -32;
        //double prepSubPos = -40;
        double SampleDistanceX = 4;
        double SampleDistanceY = 5;
        double dropAngle = -60;

        TrajectoryActionBuilder StartDeposit = bot.drive.actionBuilder(beginPose)
                .setTangent(Math.toRadians(160))
                .splineToLinearHeading(new Pose2d(9.5, SubPos, Math.toRadians(270)), Math.toRadians(90));

        TrajectoryActionBuilder Depositdriveinstart = StartDeposit.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(9.5, SubDriveInPos, Math.toRadians(270)), Math.toRadians(90));

        TrajectoryActionBuilder SampGrab1 = Depositdriveinstart.endTrajectory().fresh()
                .setTangent(-Math.PI/2)
                .splineToLinearHeading(new Pose2d(25+SampleDistanceX, -41+SampleDistanceY, Math.toRadians(30)), Math.toRadians(0))
                .afterDisp(20,new ParallelAction(
                        bot.intake.PoiseToGrab(1),
                        bot.intake.wrist.runToDegrees(120)
                ));
//auto route
        TrajectoryActionBuilder SampDrop1 = SampGrab1.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(35, -45, Math.toRadians(dropAngle)), Math.toRadians(3));

        TrajectoryActionBuilder SampGrab2 = SampDrop1.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(35+SampleDistanceX, -41+SampleDistanceY, Math.toRadians(30)), Math.toRadians(0));

        TrajectoryActionBuilder SampDrop2 = SampGrab2.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(42, -41, Math.toRadians(dropAngle)), Math.toRadians(3));

        TrajectoryActionBuilder SampGrab3 = SampDrop2.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(42+SampleDistanceX, -41+SampleDistanceY, Math.toRadians(30)), Math.toRadians(0));

        TrajectoryActionBuilder SampDrop3 = SampGrab3.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(41, -41, Math.toRadians(dropAngle)), Math.toRadians(3));

        TrajectoryActionBuilder WallGrab1 = SampDrop3.endTrajectory().fresh()
                .setTangent(Math.toRadians(-100))
                .splineToLinearHeading(new Pose2d(prepWallPos, Math.toRadians(90)), Math.toRadians(-90))
                .waitSeconds(0.1)
                .splineToLinearHeading(new Pose2d(WallPos, Math.toRadians(90)), Math.toRadians(-90),new TranslationalVelConstraint(10));

        TrajectoryActionBuilder Deposit1 = WallGrab1.endTrajectory().fresh()
                .setTangent(Math.toRadians(160))
                .splineToLinearHeading(new Pose2d(6, SubPos, Math.toRadians(270)), Math.toRadians(90));

        TrajectoryActionBuilder Depositdrivein1 = Deposit1.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(6, SubDriveInPos, Math.toRadians(270)), Math.toRadians(90));

        TrajectoryActionBuilder WallGrab2 = Depositdrivein1.endTrajectory().fresh()
                .setTangent(Math.toRadians(-100))
                .splineToLinearHeading(new Pose2d(8, -45, Math.toRadians(270)), Math.toRadians(180))
                .splineToLinearHeading(new Pose2d(prepWallPos.minus(new Vector2d(0.5,0)), Math.toRadians(90)), Math.toRadians(-90))
                .waitSeconds(0.1)
                .splineToLinearHeading(new Pose2d(WallPos.minus(new Vector2d(0.5,0)), Math.toRadians(90)), Math.toRadians(-90),new TranslationalVelConstraint(10));

        TrajectoryActionBuilder Deposit2 = WallGrab2.endTrajectory().fresh()
                .setTangent(Math.toRadians(160))
                .splineToLinearHeading(new Pose2d(4, SubPos, Math.toRadians(270)), Math.toRadians(90));

        TrajectoryActionBuilder Depositdrivein2 = Deposit2.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(4, SubDriveInPos, Math.toRadians(270)), Math.toRadians(90));

        TrajectoryActionBuilder WallGrab3 = Depositdrivein2.endTrajectory().fresh()
                .setTangent(Math.toRadians(-100))
                .splineToLinearHeading(new Pose2d(6.5, -45, Math.toRadians(270)), Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(prepWallPos, Math.toRadians(90)), Math.toRadians(-90))
                .waitSeconds(0.1)
                .splineToConstantHeading(WallPos,Math.toRadians(-90));

        TrajectoryActionBuilder Deposit3 = WallGrab3.endTrajectory().fresh()
                .setTangent(Math.toRadians(160))
                .splineToLinearHeading(new Pose2d(2, SubPos, Math.toRadians(270)), Math.toRadians(90));

        TrajectoryActionBuilder Depositdrivein3 = Deposit3.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(2, SubDriveInPos, Math.toRadians(270)), Math.toRadians(90));

        TrajectoryActionBuilder Park = Depositdrivein3.endTrajectory().fresh()
                .setTangent(-90)
                .splineToConstantHeading(new Vector2d(36,-60),6);

        CancelableAction auto = new CancelableAction(
                new SequentialAction(
                        bot.outtake.pivot1.zeroMotor(),
                        new ParallelAction(
                                bot.outtake.claw.Close(),
                                bot.intake.claw.Open()
                        ),

                        new ParallelAction(
                        StartDeposit.build(),
                        bot.outtake.placeSpec()
                        ),

                        Depositdriveinstart.build(),

                        bot.outtake.claw.Open(),

                        new ParallelAction(
                                SampGrab1.build(),
                                bot.intake.deploy(1)

                        ),

                        bot.IntakeGrab(),

                        SampDrop1.build(),

                        bot.IntakeDropSample(),

                        //                        SampGrab2.build(),
                        //
                        //                        bot.IntakeGrab(),
                        //
                        //                        SampDrop2.build(),
                        //
                        //                        bot.IntakeDropSample(),

                        new ParallelAction(
                                bot.intake.undeploy(),
                                bot.outtake.grabSpecPos()
                        ),
                                WallGrab1.build(),

                        bot.outtake.prepareToGrabSpecOffWall(),

                        new ParallelAction(
                        Deposit1.build(),
                        bot.outtake.placeSpec()
                        ),

                        Depositdrivein1.build(),

                        bot.outtake.claw.Open(),

                        new ParallelAction(

                                WallGrab2.build(),
                                new SequentialAction(
                                new SleepAction(2),
                                bot.outtake.grabSpecPos()
                                )
                        ),

                        bot.outtake.prepareToGrabSpecOffWall(),

                        new ParallelAction(
                        Deposit2.build(),
                        bot.outtake.placeSpec()
                        ),

                        Depositdrivein2.build(),

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

        telemetry.addData("outtake target reached",bot.outtake.pivot1.pivot::targetReached);
        telemetry.addData("outtake power",bot.outtake.pivot1.pivot::getPower);
        telemetry.addData("outtake pivot degrees", ()->Math.toDegrees(bot.outtake.pivot1.pivot.getEncoder().getPos()));
        telemetry.addData("outtake pivot tgt degrees", ()->Math.toDegrees(bot.outtake.pivot1.pivot.getTargetPosition()));
        Actions.runBlocking(
                new ParallelAction(
                        bot.UpdateMotorPowers(),
                        auto,
                        new LoopAction(new InstantAction(telemetry::update),()->!opModeIsActive()),
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
