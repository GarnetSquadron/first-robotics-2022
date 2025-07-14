package org.firstinspires.ftc.teamcode.OpModes.autonomi;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Subsystems.StaticInfo;
import org.firstinspires.ftc.teamcode.pathing.roadrunner.drives.MecanumDrive;

@Autonomous(name = "auto that does nothing for testing purposes")
@Disabled
public class nullAuto extends LinearOpMode
{
    double lastHeading = 0;

    @Override
    public void runOpMode() throws InterruptedException
    {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
        while (opModeInInit()) {
            drive.updatePoseEstimate();
            telemetry.addData("lastDirection", lastHeading);
            telemetry.addData("direction", MecanumDrive.pose.heading.toDouble());
            lastHeading = MecanumDrive.pose.heading.toDouble();
            telemetry.update();
        }
        waitForStart();
        while (opModeIsActive()) {
            drive.updatePoseEstimate();
            telemetry.addData("lastDirection", lastHeading);
            telemetry.addData("direction", MecanumDrive.pose.heading.toDouble());
            lastHeading = MecanumDrive.pose.heading.toDouble();
            telemetry.update();
        }
        StaticInfo.LastOpModeWasAuto = true;
    }
}
