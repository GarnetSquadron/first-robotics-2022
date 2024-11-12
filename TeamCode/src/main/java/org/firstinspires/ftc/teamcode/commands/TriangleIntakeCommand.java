package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.robocol.Command;

import org.firstinspires.ftc.teamcode.NonDriveHardware;
import org.firstinspires.ftc.teamcode.Subsystems.TriangleIntake;

public class TriangleIntakeCommand extends CommandBase {
    TriangleIntake triangleIntake;
    TriangleIntakeCommand(TriangleIntake t){
        triangleIntake = t;
    }
    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
