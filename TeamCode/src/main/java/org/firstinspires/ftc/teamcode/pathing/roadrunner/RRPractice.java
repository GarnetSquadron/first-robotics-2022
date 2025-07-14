package org.firstinspires.ftc.teamcode.pathing.roadrunner;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pathing.roadrunner.drives.MecanumDrive;

@Config
@TeleOp(name = "Practice")
@Disabled
public class RRPractice extends LinearOpMode
{


    public void runOpMode() throws InterruptedException
    {

        Pose2d beginpose = new Pose2d(0, 0, 0);
        MecanumDrive Drive = new MecanumDrive(hardwareMap, beginpose);
        Action path = Drive.actionBuilder(beginpose).splineToConstantHeading(new Vector2d(20, 20), 0).build();
        waitForStart();
        Actions.runBlocking(path);
    }

}

