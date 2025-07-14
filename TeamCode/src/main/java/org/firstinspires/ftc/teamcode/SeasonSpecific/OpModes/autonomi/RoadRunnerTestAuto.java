package org.firstinspires.ftc.teamcode.SeasonSpecific.OpModes.autonomi;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pathing.roadrunner.drives.MecanumDrive;

@TeleOp(name = "RRTestAuto", group = "RoadRunnerStuff")
@Disabled
public class RoadRunnerTestAuto extends LinearOpMode
{
    @Override
    public void runOpMode() throws InterruptedException
    {
        Pose2d beginPose = new Pose2d(0, 0, 0);
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        waitForStart();
        while (opModeIsActive()) {
            Actions.runBlocking(
                    drive.actionBuilder(beginPose)
                            .setTangent(0)
                            .splineToConstantHeading(new Vector2d(20, 0), 0)
                            .waitSeconds(3)
                            .setTangent(Math.PI / 2)
                            .splineToConstantHeading(new Vector2d(20, 20), Math.PI / 2)
                            .waitSeconds(3)
                            .setTangent(Math.PI)
                            .splineToConstantHeading(new Vector2d(0, 20), Math.PI)
                            .waitSeconds(3)
                            .setTangent(-Math.PI / 2)
                            .splineToConstantHeading(new Vector2d(0, 0), Math.PI * 3 / 2)
                            .waitSeconds(3)
                            .build()
            );
//            //sleep(20000);
        }
    }

}
