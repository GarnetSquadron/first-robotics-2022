package org.firstinspires.ftc.teamcode.OpModes.autonomi;


import com.acmerobotics.roadrunner.ftc.Actions;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.Subsystems.StaticInfo;

@Autonomous(name = "Direction Test", group = "tests")
public class DirectionTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(0,0,0);
        MecanumDrive drive = new MecanumDrive(hardwareMap);
        waitForStart();
        Actions.runBlocking(
                drive.actionBuilder(beginPose).turn(Math.PI/2).build()
        );
        StaticInfo.LastOpModeWasAuto = true;
    }
}
