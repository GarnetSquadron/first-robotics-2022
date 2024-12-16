package org.firstinspires.ftc.teamcode.OpModes.autonomi;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
@Autonomous(name = "AUTOTESTING", group = "test")
public class AutoTesing extends LinearOpMode {
    MecanumDrive drive;
    double pushY = -45;
    double pushX = 47;
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(0,0,0);
        drive = new MecanumDrive(hardwareMap,beginPose);

        Action path1 = drive.actionBuilder(beginPose)
                .splineToConstantHeading(new Vector2d(-0,-34),90)
                .waitSeconds(0.7)
                .build();

        Action path2 = drive.actionBuilder(beginPose)
                .splineToConstantHeading(new Vector2d(-0,-50),5)
                .splineToConstantHeading(new Vector2d(36,-30),45)
                .splineToConstantHeading(new Vector2d(34,-0),0)
                .splineToLinearHeading(new Pose2d(40, 0,Math.toRadians(270)), Math.toRadians(0))
                .splineToConstantHeading(new Vector2d(pushX, pushY),-1)
                .splineToConstantHeading(new Vector2d(36,-30),45)
                .splineToConstantHeading(new Vector2d(34,-0),0)
                .splineToConstantHeading(new Vector2d(45,-0),0)
                .splineToConstantHeading(new Vector2d(pushX, pushY),2)
                .splineToConstantHeading(new Vector2d(36,-30),45)
                .splineToConstantHeading(new Vector2d(34,-0),0)
                .splineToConstantHeading(new Vector2d(54,-0),0)
                .splineToConstantHeading(new Vector2d(pushX, pushY),2)
                .splineToConstantHeading(new Vector2d(56,-34),2)
                .waitSeconds(2)
                .splineToConstantHeading(new Vector2d(56,-34),0)
                .waitSeconds(0.7)
                .splineToConstantHeading(new Vector2d(56,-60),6)
                .waitSeconds(0.7)
                .splineToConstantHeading(new Vector2d(56,-34),2)
                .splineToConstantHeading(new Vector2d(-0,-50),-5)
                .splineToLinearHeading(new Pose2d(5, -34, Math.toRadians(90)), Math.toRadians(0))
                .waitSeconds(0.7)
                .splineToConstantHeading(new Vector2d(5,-50),2)
                .splineToLinearHeading(new Pose2d(56, -34, Math.toRadians(270)), Math.toRadians(10))
                .splineToConstantHeading(new Vector2d(56,-34),0)
                .waitSeconds(0.7)
                .splineToConstantHeading(new Vector2d(56,-60),6)
                .waitSeconds(0.7)
                .splineToConstantHeading(new Vector2d(56,-34),2)
                .splineToConstantHeading(new Vector2d(-0,-50),-5)
                .splineToLinearHeading(new Pose2d(-5, -34, Math.toRadians(90)), Math.toRadians(0))
                .waitSeconds(0.7) //break in routes
                .splineToConstantHeading(new Vector2d(-5,-50),2)
                .waitSeconds(0.7)
                .splineToLinearHeading(new Pose2d(-34, -26,Math.toRadians(180)), 45)
                .waitSeconds(0.7)
                .splineToLinearHeading(new Pose2d(-55, -55,Math.toRadians(45)), 10)
                .waitSeconds(0.7)
                .splineToLinearHeading(new Pose2d(-44, -26,Math.toRadians(180)), 90)
                .waitSeconds(0.7)
                .splineToLinearHeading(new Pose2d(-55, -55,Math.toRadians(45)), 10)
                .waitSeconds(0.7)
                .splineToLinearHeading(new Pose2d(-54, -26,Math.toRadians(180)), 90)
                .waitSeconds(0.7)
                .setTangent(-90)
                .splineToLinearHeading(new Pose2d(-55, -55,Math.toRadians(45)), 10)
                .waitSeconds(0.7)
                .splineToLinearHeading(new Pose2d(-24, 10, Math.toRadians(180)), 0)
                .build();
        Action path3 = drive.actionBuilder(beginPose)
                .build();


        Actions.runBlocking(path3clea);
    }
}
