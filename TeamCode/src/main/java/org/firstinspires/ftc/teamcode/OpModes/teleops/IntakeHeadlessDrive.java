package org.firstinspires.ftc.teamcode.OpModes.teleops;

import static com.arcrobotics.ftclib.gamepad.GamepadKeys.Trigger.LEFT_TRIGGER;

import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.CrankSlideSubSystem;
import org.firstinspires.ftc.teamcode.commands.IntakeCenteredHeadlessDrive;

public class IntakeHeadlessDrive extends OpMode {
    IntakeCenteredHeadlessDrive driveCommand;
    MecanumDrive drive;
    Pose2d beginPose = new Pose2d(0,0,0);
    static GamepadEx Gpad1, Gpad2;
    CrankSlideSubSystem crank;
    public static double getLeftTrigger(){
        return Gpad1.getTrigger(LEFT_TRIGGER);
    }
    @Override
    public void init() {
        Gpad1 = new GamepadEx(gamepad1);
        Gpad2 = new GamepadEx(gamepad2);
        crank = new CrankSlideSubSystem(hardwareMap,this::getRuntime);
        drive = new MecanumDrive(hardwareMap,beginPose);
        driveCommand = new IntakeCenteredHeadlessDrive(drive,Gpad1::getLeftX,Gpad1::getLeftY,Gpad1::getRightX,IntakeHeadlessDrive::getLeftTrigger);
    }

    @Override
    public void loop() {
        driveCommand.execute();
    }
}