package org.firstinspires.ftc.teamcode.Tests;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff.cv.Vision;
import org.firstinspires.ftc.teamcode.pathing.roadrunner.drives.MecanumDrive;

@TeleOp(name = "AutoAlign", group = "tests")
@Disabled
public class AutoAlign extends OpMode
{
    //Servo wrist;
    Vision vision;
    Pose2d beginPose = new Pose2d(0, 0, 0);
    MecanumDrive drive;

    @Override
    public void init()
    {
        telemetry.addLine("1");
        drive = new MecanumDrive(hardwareMap, beginPose);
        telemetry.addLine("2");
        //vision = new Vision(hardwareMap,telemetry);
        telemetry.addLine("3");
        //wrist = hardwareMap.get(Servo.class, "wrist");
        telemetry.addLine("4");
        //vision.InitPipeline(hardwareMap);
        telemetry.addLine("5");
    }

    @Override
    public void loop()
    {
        //SamplePipeline.AnalyzedStone Sample = vision.getNearestSample();
        //Action path = drive.actionBuilder(beginPose).splineToSplineHeading(Sample.getPose2d(),Sample.getAngleRad()).build();
        //Actions.runBlocking(path);
    }
}
