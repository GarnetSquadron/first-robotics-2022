package org.firstinspires.ftc.teamcode.OpModes.autonomi;

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
@TeleOp(name = "ScrimmageAutoRouteRR")
@Disabled
public class ScrimmageAutoRouteRR extends LinearOpMode
{

    public void runOpMode() throws InterruptedException
    {

        Pose2d beginpose = new Pose2d(-26, -70, 90);
        MecanumDrive Drive = new MecanumDrive(hardwareMap, beginpose);
        Action path = Drive.actionBuilder(beginpose)
                .splineToConstantHeading(new Vector2d(-45, -34), 0)
                .splineToConstantHeading(new Vector2d(-60, -60), 0)
                .splineToConstantHeading(new Vector2d(-70, -34), 0)
                .splineToConstantHeading(new Vector2d(-60, -60), 0)
                .splineToConstantHeading(new Vector2d(-54, -34), 0)
                .splineToConstantHeading(new Vector2d(-60, -60), 0)
                .splineToConstantHeading(new Vector2d(56, -60), 0)
                .build();
        waitForStart();
        Actions.runBlocking(path);
    }

}
