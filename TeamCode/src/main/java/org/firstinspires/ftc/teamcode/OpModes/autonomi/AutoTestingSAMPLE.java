package org.firstinspires.ftc.teamcode.OpModes.autonomi;

import org.firstinspires.ftc.teamcode.Subsystems.Bot;
import org.firstinspires.ftc.teamcode.Subsystems.StaticInfo;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "AUTOTESTINGSAMPLE", group = "test")
public class AutoTestingSAMPLE extends LinearOpMode {
    Bot bot;
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        Pose2d beginPose = new Pose2d(-23, -62, Math.toRadians(90));
        bot =  new Bot(hardwareMap,telemetry,this::getRuntime,beginPose);
        Pose2d depositSpot = new Pose2d(-55, -55, Math.toRadians(45));

        TrajectoryActionBuilder Deposit1 = bot.drive.actionBuilder(beginPose)
                .splineToLinearHeading(depositSpot, 10);

        TrajectoryActionBuilder Sample1 = Deposit1.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(-48, -49, Math.toRadians(90)), 45);

        TrajectoryActionBuilder Deposit2 = Sample1.endTrajectory().fresh()
                .splineToLinearHeading(depositSpot, 10);

        TrajectoryActionBuilder Sample2 = Deposit2.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(-57, -49, Math.toRadians(90)), 90);

        TrajectoryActionBuilder Deposit3 = Sample2.endTrajectory().fresh()
                .splineToLinearHeading(depositSpot, 10);

        TrajectoryActionBuilder Sample3 = Deposit3.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(-53, -43, Math.toRadians(133)), 90);

        TrajectoryActionBuilder DepositTan = Deposit3.fresh()
                .setTangent(-90)
                .splineToLinearHeading(depositSpot, 10);

        TrajectoryActionBuilder Park = DepositTan.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(-24, -12, Math.toRadians(0)), 0);


        Actions.runBlocking(
                new SequentialAction(

                        bot.outtake.claw.Close(),

                        bot.intake.claw.Open(),


                        new ParallelAction(
                                bot.BasketDrop(),
                                Deposit1.build()
                        ),

                        new ParallelAction(

                                Sample1.build(),
                                bot.outtake.SafeVipersDown(),
                                new SequentialAction(
                                        bot.intake.PoiseToGrab(1)
                                )
                        ),
                        bot.intake.deploy(1),

                        bot.intake.claw.Close(),

                        new ParallelAction(
                                new SequentialAction(
                                        bot.Transfer(),
                                        bot.BasketDrop()
                                ),
                                Deposit2.build()
                        ),

                        bot.outtake.claw.Open(),

                        bot.Transfer(),

                        new ParallelAction(

                                Sample2.build(),
                                new SequentialAction(
                                        bot.intake.PoiseToGrab(1)
                                )
                        ),
                        bot.intake.deploy(1),

                        bot.Transfer(),

                        bot.BasketDrop(),

                        new ParallelAction(

                                Deposit3.build()
                        ),

                        bot.outtake.claw.Open(),

                        bot.outtake.SafeVipersDown(),

                        new ParallelAction(

                                Sample3.build(),
                                bot.intake.PoiseToGrab(1)
                        ),
                        bot.intake.deploy(1),

                        bot.intake.wrist.runToDegrees(45),

                        bot.Transfer(),

                        bot.BasketDrop(),

                        new ParallelAction(

                                DepositTan.build()
                        ),

                        bot.outtake.claw.Open(),


                                Park

                                .build()

                )
        );
        StaticInfo.LastOpModeWasAuto = true;
    }
}
