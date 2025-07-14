package org.firstinspires.ftc.teamcode.SeasonSpecific.OpModes.autonomi;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.pathing.roadrunner.drives.MecanumDrive;

@Autonomous(name = "ScrimRed", group = "test")
@Disabled
public class Autonoredteamscrim extends LinearOpMode
{

    public void runOpMode() throws InterruptedException
    {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(26, -62, 90));
        Pose2d beginPose = new Pose2d(26, -62, Math.toRadians(90));
        Pose2d tgtPose = new Pose2d(20, 20, Math.PI);

        Action path1 = drive.actionBuilder(beginPose)
                .splineToConstantHeading(new Vector2d(56, -40), 0)
                .build();
        Action path2 = drive.actionBuilder(new Pose2d(40, 20, 0))
                .splineToConstantHeading(new Vector2d(56, -62), 90)
                .build();

        Actions.runBlocking(
                new SequentialAction(path1, path2)
        );

    }
}

