package org.firstinspires.ftc.teamcode.SeasonSpecific.OpModes.autonomi;


import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pathing.roadrunner.drives.MecanumDrive;

@TeleOp(name = "example")
@Disabled
public class Example extends LinearOpMode
{

    @Override
    public void runOpMode() throws InterruptedException
    {
        Pose2d BeginPose = new Pose2d(0, 0, 0);
        MecanumDrive drive = new MecanumDrive(hardwareMap, BeginPose);
        waitForStart();
        Action path = drive.actionBuilder(BeginPose)
                .splineTo(new Vector2d(48, 0), 0)
                .turnTo(Math.toRadians(90))
                .build();
        Actions.runBlocking(path);
    }
}
