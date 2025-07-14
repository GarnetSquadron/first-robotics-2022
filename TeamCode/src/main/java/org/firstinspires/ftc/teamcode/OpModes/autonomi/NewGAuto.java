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
@TeleOp(name = "NewGAuto")
@Disabled
public class NewGAuto extends LinearOpMode
{

    public void runOpMode() throws InterruptedException
    {

        Pose2d beginpose = new Pose2d(2, -70, 0);
        MecanumDrive Drive = new MecanumDrive(hardwareMap, beginpose);
        Action path = Drive.actionBuilder(beginpose)
                .splineToConstantHeading(new Vector2d(2, -40), 0)
                .splineToConstantHeading(new Vector2d(-62, -34), 0)
                .splineToConstantHeading(new Vector2d(-62, -62), 0)
                .splineToConstantHeading(new Vector2d(-62, -34), 0)
                .splineToConstantHeading(new Vector2d(-62, -62), 0)
                .splineToConstantHeading(new Vector2d(56, -32), 0)
                .splineToConstantHeading(new Vector2d(56, -68), 0)

                .build();
        waitForStart();
        Actions.runBlocking(path);
    }

}
