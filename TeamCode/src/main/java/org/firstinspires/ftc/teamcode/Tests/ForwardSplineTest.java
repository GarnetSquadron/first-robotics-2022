package org.firstinspires.ftc.teamcode.Tests;


import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pathing.roadrunner.drives.MecanumDrive;

@TeleOp(name = "ForwardSplineTest")
@Disabled
public class ForwardSplineTest extends LinearOpMode
{

    public void runOpMode()
    {
        Pose2d beginPose = new Pose2d(0, 0, 0);
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        Action path = drive.actionBuilder(beginPose).splineToConstantHeading(new Vector2d(40, 0), 0).build();
        waitForStart();
        Actions.runBlocking(path);
    }
}
