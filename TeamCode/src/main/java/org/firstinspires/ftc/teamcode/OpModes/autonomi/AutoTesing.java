package org.firstinspires.ftc.teamcode.OpModes.autonomi;

import org.firstinspires.ftc.teamcode.Subsystems.outake.ViperSlidesSubSystem;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
@Autonomous(name = "AUTOTESTING", group = "test")
public class AutoTesing extends LinearOpMode {
    MecanumDrive drive;
    ViperSlidesSubSystem viperSlidesSubSystem;
    double pushY = -45;
    double pushX = 47;
    @Override
    public void runOpMode() throws InterruptedException {
        viperSlidesSubSystem =  new ViperSlidesSubSystem(hardwareMap);
        Pose2d beginPose = new Pose2d(0,0,0);
        drive = new MecanumDrive(hardwareMap,beginPose);


        Action path1 = drive.actionBuilder(beginPose)
                .splineToLinearHeading(new Pose2d(-55, -55, Math.toRadians(45)), 10)
                .waitSeconds(0.7)
                .build();

        Action path2 = drive.actionBuilder(beginPose)
                .splineToLinearHeading(new Pose2d(-48, -48, Math.toRadians(90)), 45)
                .waitSeconds(0.7)
                .build();

        Action path3 = drive.actionBuilder(beginPose)
                .splineToLinearHeading(new Pose2d(-55, -55, Math.toRadians(45)), 10)
                .waitSeconds(0.7)
                .build();

        Action path4 = drive.actionBuilder(beginPose)
                .splineToLinearHeading(new Pose2d(-58, -48, Math.toRadians(90)), 90)
                .waitSeconds(0.7)
                .build();

        Action path5 = drive.actionBuilder(beginPose)
                .splineToLinearHeading(new Pose2d(-55, -55, Math.toRadians(45)), 10)
                .waitSeconds(0.7)
                .build();

        Action path6 = drive.actionBuilder(beginPose)
                .splineToLinearHeading(new Pose2d(-50, -46, Math.toRadians(133)), 90)
                .waitSeconds(0.7)
                .build();

        Action path7 = drive.actionBuilder(beginPose)
                .setTangent(-90)
                .splineToLinearHeading(new Pose2d(-55, -55, Math.toRadians(45)), 10)
                .waitSeconds(0.7)
                .build();

        Action path8 = drive.actionBuilder(beginPose)
                .splineToLinearHeading(new Pose2d(-24, -12, Math.toRadians(0)), 0)
                .build();







        Actions.runBlocking(
                new SequentialAction(
                        path1,
                        viperSlidesSubSystem.Viperdown(),
                        viperSlidesSubSystem.Viperup())
//                        path2,
//
//                        path3,
//
//                        path4,
//
//                        path5,
//
//                        path6,
//
//                        path7,
//
//                        path8)

        );
    }
}
