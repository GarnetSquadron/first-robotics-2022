package org.firstinspires.ftc.teamcode.commands;

import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;

import org.firstinspires.ftc.teamcode.pathing.roadrunner.drives.MecanumDrive;

import java.util.function.DoubleSupplier;

public class RegularDrive
{
    MecanumDrive drive;

    public RegularDrive(MecanumDrive m)
    {
        drive = m;
    }

    public void execute(DoubleSupplier xvel, DoubleSupplier yvel, DoubleSupplier AngularVel, double sensitivity)
    {
        drive.updatePoseEstimate();
        double direction = MecanumDrive.pose.heading.toDouble();
        drive.setDrivePowers(new PoseVelocity2d(
                new Vector2d(
                        yvel.getAsDouble(),
                        xvel.getAsDouble()
                ).times(sensitivity),
                -AngularVel.getAsDouble() * sensitivity
        ));
    }
}
