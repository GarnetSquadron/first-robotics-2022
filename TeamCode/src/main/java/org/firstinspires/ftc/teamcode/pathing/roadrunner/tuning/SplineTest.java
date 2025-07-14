package org.firstinspires.ftc.teamcode.pathing.roadrunner.tuning;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.pathing.roadrunner.drives.MecanumDrive;
import org.firstinspires.ftc.teamcode.pathing.roadrunner.drives.TankDrive;

public final class SplineTest extends LinearOpMode
{
    @Override
    public void runOpMode() throws InterruptedException
    {
        Pose2d beginPose = new Pose2d(0, 0, 0);
        if (TuningOpModes.DRIVE_CLASS.equals(MecanumDrive.class)) {

            MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
            waitForStart();
            while (opModeIsActive()) {
                Actions.runBlocking(
                        drive.actionBuilder(beginPose)
                                .setTangent(0)
                                .splineToSplineHeading(new Pose2d(20, 20, -Math.PI / 2), Math.PI / 2)
                                .setTangent(0)
                                .splineToSplineHeading(new Pose2d(40, 0, 0), -Math.PI / 2)
                                .build());
            }
        } else if (TuningOpModes.DRIVE_CLASS.equals(TankDrive.class)) {
            TankDrive drive = new TankDrive(hardwareMap, beginPose);

            waitForStart();

            Actions.runBlocking(
                    drive.actionBuilder(beginPose)
                            .splineTo(new Vector2d(30, 30), Math.PI / 2)
                            .splineTo(new Vector2d(0, 60), Math.PI)
                            .build());
        } else {
            throw new RuntimeException();
        }
    }
}
