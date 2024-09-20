package org.firstinspires.ftc.teamcode.OpModes.autonomi;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.NonDriveHardware;

@Config
@TeleOp(name="CrazySpline", group = "RoadRunnerStuff")
public class Example2 extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginpos = new Pose2d(0,0,0);
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginpos);
        NonDriveHardware.Arm arm = new NonDriveHardware.Arm(hardwareMap, 20);
        Action path = drive.actionBuilder(beginpos)
                .splineToSplineHeading(new Pose2d(20,20,Math.toRadians(90)), 0)
                .build();
        Action path2 = drive.actionBuilder(beginpos).build();
        Action path3 = new SequentialAction(path,path2);
        Action ArmMovement = arm.runTo(630);
        Action Movement = new ParallelAction(path,arm.runTo(630));
        waitForStart();
        Actions.runBlocking();
    }
}