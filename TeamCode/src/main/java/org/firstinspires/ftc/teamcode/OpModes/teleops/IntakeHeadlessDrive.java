package org.firstinspires.ftc.teamcode.OpModes.teleops;

import static com.arcrobotics.ftclib.gamepad.GamepadKeys.Trigger.LEFT_TRIGGER;
import static com.arcrobotics.ftclib.gamepad.GamepadKeys.Trigger.RIGHT_TRIGGER;

import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.commands.IntakeCenteredHeadlessDrive;

public class IntakeHeadlessDrive extends OpMode {
    IntakeCenteredHeadlessDrive driveCommand;
    MecanumDrive drive;
    Pose2d beginPose = new Pose2d(0,0,0);
    static GamepadEx Gpad1, Gpad2;
    public static double getLeftTrigger(){
        return Gpad1.getTrigger(LEFT_TRIGGER);
    }
    @Override
    public void init() {
        Gpad1 = new GamepadEx(gamepad1);
        Gpad2 = new GamepadEx(gamepad2);
        drive = new MecanumDrive(hardwareMap,beginPose);
        driveCommand = new IntakeCenteredHeadlessDrive(drive,Gpad1::getLeftX,Gpad1::getLeftY,Gpad1::getRightX,IntakeHeadlessDrive::getLeftTrigger);
    }

    @Override
    public void loop() {
        driveCommand.execute();
    }
}