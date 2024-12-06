package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.CrankAndClaw;
import org.firstinspires.ftc.teamcode.Subsystems.outake.Outtake;
import org.firstinspires.ftc.teamcode.Subsystems.outake.OuttakeClaw;
import org.firstinspires.ftc.teamcode.Subsystems.outake.OuttakePivotSub;
import org.firstinspires.ftc.teamcode.Subsystems.outake.ViperSlidesSubSystem;
import org.firstinspires.ftc.teamcode.commands.HeadlessDriveCommand;

public class Bot {
    public MecanumDrive drive;
    public Pose2d beginPose = new Pose2d(0,0,0);
    public HeadlessDriveCommand headlessDriveCommand;

    public Outtake outtake;

    public OuttakePivotSub outtakePivot;

    public CrankAndClaw intake;
    public Bot(HardwareMap hardwareMap, GamepadEx Gpad1){
        drive = new MecanumDrive(hardwareMap,beginPose);
        headlessDriveCommand = new HeadlessDriveCommand(drive,Gpad1::getLeftX,Gpad1::getLeftY,Gpad1::getRightX);
        outtake = new Outtake(hardwareMap);
        outtakePivot = new OuttakePivotSub(hardwareMap);
    }

}
