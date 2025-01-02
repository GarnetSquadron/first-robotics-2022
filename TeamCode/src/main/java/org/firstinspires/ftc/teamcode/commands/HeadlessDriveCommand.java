package org.firstinspires.ftc.teamcode.commands;

import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.MecanumDrive;

import java.util.DoubleSummaryStatistics;
import java.util.function.DoubleSupplier;

public class HeadlessDriveCommand{
    MecanumDrive drive;
    public HeadlessDriveCommand(MecanumDrive m){
        drive = m;
    }
    public void execute(DoubleSupplier xvel, DoubleSupplier yvel, DoubleSupplier AngularVel){
        drive.updatePoseEstimate();
        double direction = drive.pose.heading.toDouble();
        drive.setDrivePowers(new PoseVelocity2d(
                new Vector2d(
                        -Math.sin(drive.pose.heading.toDouble())*xvel.getAsDouble()+Math.cos(drive.pose.heading.toDouble())*yvel.getAsDouble(),
                        -Math.cos(drive.pose.heading.toDouble())*xvel.getAsDouble()-Math.sin(drive.pose.heading.toDouble())*yvel.getAsDouble()
                ),
                AngularVel.getAsDouble()
        ));
    }
}
