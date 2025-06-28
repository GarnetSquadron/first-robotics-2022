package org.firstinspires.ftc.teamcode.OpModes.autonomi;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.Subsystems.StaticInfo;

@Autonomous(name = "Direction Test", group = "tests")
public class DirectionTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        Pose2d beginPose = new Pose2d(0,0,0);
        MecanumDrive drive = new MecanumDrive(hardwareMap,beginPose);
        waitForStart();
        while (opModeIsActive()){
            Actions.runBlocking(
                    drive.actionBuilder(beginPose).turn(Math.PI).build()
            );
            telemetry.addData("x", drive.pose.position.x);
            telemetry.addData("y", drive.pose.position.y);
            telemetry.addData("heading (deg)", Math.toDegrees(drive.pose.heading.toDouble()));
            telemetry.update();
        }
        StaticInfo.LastOpModeWasAuto = true;
    }
}
