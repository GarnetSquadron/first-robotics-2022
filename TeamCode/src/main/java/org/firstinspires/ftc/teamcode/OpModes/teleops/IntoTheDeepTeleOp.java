package org.firstinspires.ftc.teamcode.OpModes.teleops;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.Subsystems.ColorSensorSubSystem;
import org.firstinspires.ftc.teamcode.Subsystems.CrankSlideSubSystem;
import org.firstinspires.ftc.teamcode.Subsystems.IntakePivot;
import org.firstinspires.ftc.teamcode.Subsystems.OuttakePivotSub;
import org.firstinspires.ftc.teamcode.Subsystems.TriangleIntake;
import org.firstinspires.ftc.teamcode.Subsystems.ViperSlidesSubSystem;
import org.firstinspires.ftc.teamcode.commands.HeadlessDriveCommand;
import org.firstinspires.ftc.teamcode.commands.TriangleIntakeCommand;
import org.firstinspires.ftc.teamcode.enums.Color;

@TeleOp(name = "INTOTHEDEEP TELEOP", group = "AAA TELEOPS")
public class IntoTheDeepTeleOp extends OpMode {
    MecanumDrive drive;
    Pose2d beginPose = new Pose2d(0,0,0);
    HeadlessDriveCommand headlessDriveCommand;
    ViperSlidesSubSystem viperSlidesSubSystem;
    OuttakePivotSub outtakePivot;
    CrankSlideSubSystem crank;
    TriangleIntake triangleIntake;
    ColorSensorSubSystem cSensor;
    TriangleIntakeCommand triangleIntakeCommand;

    IntakePivot intakePivot;
    Servo lid;
    GamepadEx Gpad1;
    GamepadEx Gpad2;
    Color AlianceColor = Color.RED;
    public static void RunHeadlessDrive(MecanumDrive drive, Gamepad gamepad){
        drive.updatePoseEstimate();
        double direction = drive.pose.heading.toDouble();
        drive.setDrivePowers(new PoseVelocity2d(
                new Vector2d(
                        -Math.sin(drive.pose.heading.toDouble())*gamepad.left_stick_x-Math.cos(drive.pose.heading.toDouble())*gamepad.left_stick_y,
                        -Math.cos(drive.pose.heading.toDouble())*gamepad.left_stick_x+Math.sin(drive.pose.heading.toDouble())*gamepad.left_stick_y
                ),
                -gamepad.right_stick_x
        ));
    }
    @Override
    public void init() {
        drive = new MecanumDrive(hardwareMap,beginPose);
        Gpad1 = new GamepadEx(gamepad1);
        Gpad2 = new GamepadEx(gamepad2);
        lid = hardwareMap.get(Servo.class, "lid");
        headlessDriveCommand = new HeadlessDriveCommand(drive,Gpad1::getLeftX,Gpad1::getLeftY,Gpad1::getRightX);
        viperSlidesSubSystem = new ViperSlidesSubSystem(hardwareMap);
        outtakePivot = new OuttakePivotSub(hardwareMap,"AlignServo1","AlignServo2");
        crank = new CrankSlideSubSystem(hardwareMap,"CrankLeft","CrankRight");
        triangleIntake = new TriangleIntake(hardwareMap,"Ti", "Fi", "Bi","pivot");
        cSensor = new ColorSensorSubSystem(hardwareMap,"ColorSensor");
        triangleIntakeCommand = new TriangleIntakeCommand(triangleIntake,cSensor, AlianceColor,telemetry);
        intakePivot = new IntakePivot(hardwareMap);
        telemetry.addData("sensed color",triangleIntakeCommand.c);
    }
    boolean firstiter = true;
    @Override
    public void loop() {
        if(firstiter){
            intakePivot.deploy();
            outtakePivot.Down();
            firstiter = false;
        }

        RunHeadlessDrive(drive, gamepad1);
        if(gamepad2.x) {
            viperSlidesSubSystem.SetTgPosToExtend();
        }
        if(gamepad2.y){
            viperSlidesSubSystem.SetTgPosToRetract();
        }
        viperSlidesSubSystem.runToTgPos();
        if(gamepad2.a){
            crank.Extend();
        }
        if(gamepad2.b){
            crank.Return();
        }
        if(gamepad2.dpad_up){
            outtakePivot.Up();
        }
        if(gamepad2.dpad_down) {
            outtakePivot.Down();
        }
        if(gamepad2.left_bumper){
            lid.setPosition(0);
        }
        if(gamepad2.right_bumper){
            lid.setPosition(1);
        }
        if(gamepad2.right_trigger>0.1/*&&!triangleIntakeCommand.isFinished()*/){
            triangleIntakeCommand.execute();
        }
        else if(gamepad2.left_trigger>0.1){
            triangleIntake.eject();
        }
        else {
            triangleIntake.hold();
        }

        if(gamepad2.dpad_right){
            intakePivot.deploy();
        }
        if(gamepad2.dpad_left){
            intakePivot.undeploy();
        }
        telemetry.addData("firstiter", firstiter);
        telemetry.update();

    }
}
