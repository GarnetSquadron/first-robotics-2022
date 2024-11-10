package org.firstinspires.ftc.teamcode.Tests;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.Pipelines.SamplePipeline;
import org.firstinspires.ftc.teamcode.Vision;
import org.opencv.core.Point;

@TeleOp(name = "AutoAlign", group = "tests")
public class AutoAlign extends OpMode {
    Servo wrist;
    Vision vision;
    Pose2d beginPose = new Pose2d(0,0,0);
    MecanumDrive drive;
    @Override
    public void init() {
        drive = new MecanumDrive(hardwareMap,beginPose);
        vision = new Vision(hardwareMap,telemetry);
        wrist = hardwareMap.get(Servo.class, "wrist");
        vision.InitPipeline(hardwareMap);
    }

    @Override
    public void loop() {
        SamplePipeline.AnalyzedStone Sample = vision.getNearestSample();
        Action path = drive.actionBuilder(beginPose).splineToSplineHeading(Sample.getPose2d(),Sample.getAngleRad()).build();
        Actions.runBlocking(path);
    }
}
