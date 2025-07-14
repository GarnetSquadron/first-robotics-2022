package org.firstinspires.ftc.teamcode.OpModes.autonomi;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.depricated.NonDriveHardware;
import org.firstinspires.ftc.teamcode.pathing.roadrunner.drives.MecanumDrive;

@Config
@TeleOp(name = "Example2", group = "RoadRunnerStuff")
@Disabled
public class Example2 extends LinearOpMode
{
    @Override
    public void runOpMode() throws InterruptedException
    {
        Pose2d beginpos = new Pose2d(0, 0, 0);
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginpos);
        NonDriveHardware.Arm arm = new NonDriveHardware.Arm(hardwareMap, 20);
        Action path = drive.actionBuilder(beginpos)
                .splineToSplineHeading(new Pose2d(20, 20, Math.toRadians(90)), 0)
                .build();
        waitForStart();
        Actions.runBlocking(
                path
        );
    }
}