package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.MecanumDrive;

public class MecanumDriveSubSystem extends SubsystemBase {
    public MecanumDrive drive;
    public MecanumDriveSubSystem(MecanumDrive drive){
        this.drive = drive;
    }
}
