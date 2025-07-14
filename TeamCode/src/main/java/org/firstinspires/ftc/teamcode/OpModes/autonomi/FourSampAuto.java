package org.firstinspires.ftc.teamcode.OpModes.autonomi;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff.Bot;
import org.firstinspires.ftc.teamcode.Subsystems.StaticInfo;

@Autonomous(name = "#FOUR SAMPLE#", group = "test")
@Disabled
public class FourSampAuto extends LinearOpMode
{
    Bot bot;

    @Override
    public void runOpMode() throws InterruptedException
    {
        //the position the auto starts at
        Pose2d beginPose = new Pose2d(-23, -62, Math.toRadians(90));
        //the class that contains all the subsystems
        bot = new Bot(hardwareMap, telemetry, this::getRuntime, beginPose);
        Pose2d depositSpot = new Pose2d(-55.5, -55.5, Math.toRadians(45));

        //the trajectories that it will drive along the course of the auto

        TrajectoryActionBuilder Deposit1 = bot.drive.actionBuilder(beginPose)
                .splineToLinearHeading(depositSpot, 10);

        TrajectoryActionBuilder Sample1 = Deposit1.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(-48.5, -45, Math.toRadians(90)), 45);

        TrajectoryActionBuilder Deposit2 = Sample1.endTrajectory().fresh()
                .splineToLinearHeading(depositSpot, 10);

        TrajectoryActionBuilder Sample2 = Deposit2.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(-59, -45, Math.toRadians(90)), 90);

        TrajectoryActionBuilder Deposit3 = Sample2.endTrajectory().fresh()
                .splineToLinearHeading(depositSpot, 10);

        TrajectoryActionBuilder Sample3 = Deposit3.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(-54.5, -40.5, Math.toRadians(133)), 90);

        TrajectoryActionBuilder Deposit4Tan = Deposit3.fresh()
                .setTangent(-90)
                .splineToLinearHeading(depositSpot, 10);

        TrajectoryActionBuilder Park = Deposit4Tan.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(-24, -12, Math.toRadians(0)), 0);

        waitForStart();
        StaticInfo.LastOpModeWasAuto = true;

        Actions.runBlocking(

                new ParallelAction(
                        bot.UpdateMotorPowers(),
                        new SequentialAction(
                                //make sure that the outtake claw is closed around the sample, and that the intake claw is opened
                                new ParallelAction(
                                        bot.outtake.claw.Close(),

                                        bot.intake.claw.Open(),
                                        bot.outtake.pivot1.zeroMotor()
                                ),

                                //drive to the basket and move the outtake to a position where it can drop the sample in a basket by extending the vipers
                                new ParallelAction(
                                        bot.AutoBasketDrop(),
                                        Deposit1.build()
                                ),
                                //wait for an instant and then drop the sample
                                new SleepAction(0.3),
                                bot.outtake.claw.Open(),
                                // go grab the sample as the vipers come down and as the intake extends
                                new ParallelAction(

                                        Sample1.build(),
                                        bot.outtake.SafeVipersDown(),
                                        new SequentialAction(
                                                bot.intake.PoiseToGrabAuto(1)
                                        )
                                ),
                                //rotate the intake pivot down more to grab the sample
                                bot.intake.deploy(1),
                                //grab it
                                bot.intake.claw.Close(),
                                //transfer the sample from the intake to the outtake
                                bot.Transfer(),
                                //move to the basket again as we extend the vipers
                                new ParallelAction(
                                        new SequentialAction(
                                                bot.AutoBasketDrop()
                                        ),
                                        Deposit2.build()
                                ),
                                //open the claw
                                new SleepAction(0.25),
                                bot.outtake.claw.Open(),
                                //again, go grab the next sample as the vipers come down and as the intake extends
                                new ParallelAction(

                                        Sample2.build(),
                                        bot.outtake.SafeVipersDown(),
                                        new SequentialAction(
                                                bot.intake.PoiseToGrabAuto(1)
                                        )
                                ),
                                //you can probably see where this is going. position to grab
                                bot.intake.deploy(1),

                                //bot.transfer grabs the sample before transferring that sample from the intake to the outtake
                                bot.Transfer(),
                                //again, go back to the basket and get ready to deposit
                                new ParallelAction(
                                        new SequentialAction(
                                                bot.AutoBasketDrop()
                                        ),
                                        Deposit3.build()
                                ),
                                //drop sample
                                new SleepAction(0.25),
                                bot.outtake.claw.Open(),
                                //etc
                                new ParallelAction(

                                        bot.outtake.SafeVipersDown(),
                                        Sample3.build()

                                ),
                                bot.intake.wrist.runToDegrees(45),
                                bot.intake.PoiseToGrabAuto(1),


                                bot.intake.deploy(1),


                                bot.intake.claw.Close(),
                                bot.Transfer(),

                                new ParallelAction(
                                        new SequentialAction(
                                                bot.AutoBasketDrop()
                                        ),
                                        Deposit4Tan.build()
                                ),
                                new SleepAction(0.25),
                                bot.outtake.claw.Open(),
                                bot.outtake.SafeVipersDown()


                        )
                )
        );
    }
}
