package org.firstinspires.ftc.teamcode.OpModes.autonomi;

import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MiscActions.CancelableAction;
import org.firstinspires.ftc.teamcode.MiscActions.LoopAction;
import org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff.Bot;

@Autonomous(name = "#PARK AUTO#")
@Disabled
public class ParkAuto extends LinearOpMode
{
    Bot bot;

    @Override
    public void runOpMode() throws InterruptedException
    {
        //telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        //the position the auto starts at
        Pose2d beginPose = new Pose2d(26, -62, Math.toRadians(90));
        //the class that contains all the subsystems
        bot = new Bot(hardwareMap, telemetry, this::getRuntime, beginPose);

        //the trajectories that it will drive along the course of the auto
        TrajectoryActionBuilder Park = bot.drive.actionBuilder(beginPose)
                .setTangent(-90)
                .splineToConstantHeading(new Vector2d(36, -60), 6);

        CancelableAction auto = new CancelableAction(
                new SequentialAction(
                        bot.outtake.pivot1.zeroMotor(),
                        Park.build()
                ),
                bot.drive.Stop()

        );
        waitForStart();

        telemetry.addData("outtake pivot target reached", bot.outtake.pivot1.pivot::targetReached);
        telemetry.addData("outtake pivot power", bot.outtake.pivot1.pivot::getPower);
        telemetry.addData("outtake pivot degrees", () -> Math.toDegrees(bot.outtake.pivot1.pivot.getEncoder().getPos()));
        telemetry.addData("outtake pivot tgt degrees", () -> Math.toDegrees(bot.outtake.pivot1.pivot.getTargetPosition()));
        telemetry.addData("viper target reached", bot.outtake.vipers.l::targetReached);
        telemetry.addData("viper power", bot.outtake.vipers.l::getPower);
        telemetry.addData("viper degrees", () -> Math.toDegrees(bot.outtake.vipers.l.getEncoder().getPos()));
        telemetry.addData("viper tgt degrees", () -> Math.toDegrees(bot.outtake.vipers.l.getTargetPosition()));
        Actions.runBlocking(
                new ParallelAction(
                        bot.UpdateMotorPowers(),
                        auto,
                        new LoopAction(new InstantAction(telemetry::update), this::opModeIsActive),
                        new SequentialAction(
                                new SleepAction(28),
                                new InstantAction(auto::failover),
                                bot.outtake.vipers.Down(),
                                bot.outtake.pivot1.pivot.runWithPowerUntilStopped(0.5, 0.01)
                        )
                )
        );
    }
}
