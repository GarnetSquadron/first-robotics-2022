package org.firstinspires.ftc.teamcode.OpModes.autonomi;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ftc.Actions;// <--NOTE: FOR SOME REASON ACTIONS WONT IMPORT IF YOU DONT PUT .ftc BEFORE .Actions IDK WHY

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.depricated.NonDriveHardware;

@Config
@TeleOp(name="CrazySpline", group = "RoadRunnerStuff")
@Disabled
public class CrazySpline extends LinearOpMode {
    public static int pos = 300;

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(0, 0, 0);
        NonDriveHardware.Arm arm = new NonDriveHardware.Arm(hardwareMap, 630);
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        Action path1 = drive.actionBuilder(beginPose)
                .setTangent(0)
                .splineToConstantHeading(new Vector2d(40,20),0)
                .build();
        Action path2 = drive.actionBuilder(new Pose2d(40,20,0))
                .setTangent(0)
                .splineToSplineHeading(new Pose2d(30,20, Math.toRadians(180)), 0)
                .splineToLinearHeading(new Pose2d(20,20, 2*Math.PI), 0)
                .splineToLinearHeading(new Pose2d(10,20, 3*Math.PI), 0)
                .splineToLinearHeading(new Pose2d(0,20, 4*Math.PI), 0)
                .build();


        Action path3 = new SequentialAction();
        Action PATH =new ParallelAction(
                arm.handleMovement(),
                new SequentialAction(
                    new ParallelAction(
                                arm.runTo(0),
                                path1
                        ),
                        new ParallelAction(
                                arm.runTo(300),
                                path2
                        ),
                        arm.Disable()
                )
        );
        waitForStart();
        Actions.runBlocking(
            PATH
        );
    }
}