package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.outake.Outtake;
import org.firstinspires.ftc.teamcode.Subsystems.outake.PrimaryOuttakePivot;
import org.firstinspires.ftc.teamcode.commands.HeadlessDriveCommand;

public class Bot {
    public MecanumDrive drive;
    public Pose2d beginPose = new Pose2d(0,0,0);
    public HeadlessDriveCommand headlessDriveCommand;

    public Outtake outtake;

    public PrimaryOuttakePivot outtakePivot;

    public Intake intake;
    public boolean transfering = false;
    public Bot(HardwareMap hardwareMap, GamepadEx Gpad1){
        drive = new MecanumDrive(hardwareMap,beginPose);
        headlessDriveCommand = new HeadlessDriveCommand(drive,Gpad1::getLeftX,Gpad1::getLeftY,Gpad1::getRightX);
        outtake = new Outtake(hardwareMap);
        outtakePivot = new PrimaryOuttakePivot(hardwareMap);
        intake = new Intake(hardwareMap);
    }

    /**
     * meant to be looped, like a lot of this stuff
     */
    public void transfer(){
        transfering = true;
    }
    public void runToTargetPos() {
        outtake.runToTargetPos();
        headlessDriveCommand.execute();
        if (transfering){
            outtake.claw.open();
            intake.claw.close();
            intake.goToDefaultPos();
            outtake.goToDefaultPos();
            if(intake.targetReached()&&outtake.targetReached()){
                outtake.claw.close();
                intake.claw.open();
                transfering = false;
            }
        }
    }
}

//Default pos = set postition not starting pos
