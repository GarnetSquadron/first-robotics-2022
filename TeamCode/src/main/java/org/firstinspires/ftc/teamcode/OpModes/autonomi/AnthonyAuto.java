package org.firstinspires.ftc.teamcode.OpModes.autonomi;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Trajectory;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.Subsystems.ColorSensorSubSystem;
import org.firstinspires.ftc.teamcode.Subsystems.CrankSlideSubSystem;
import org.firstinspires.ftc.teamcode.Subsystems.IntakePivot;
import org.firstinspires.ftc.teamcode.Subsystems.OuttakePivotSub;
import org.firstinspires.ftc.teamcode.Subsystems.TriangleIntake;
import org.firstinspires.ftc.teamcode.commands.HeadlessDriveCommand;
import org.firstinspires.ftc.teamcode.commands.TriangleIntakeCommand;
import org.firstinspires.ftc.teamcode.Subsystems.ViperSlidesSubSystem;
import org.firstinspires.ftc.teamcode.Subsystems.TriangleIntake;
import org.firstinspires.ftc.teamcode.enums.Color;
import org.firstinspires.ftc.teamcode.oldStuff.VoidsAndThings;

public class AnthonyAuto extends LinearOpMode {
    ColorSensorSubSystem colorSensor;
    TriangleIntake triangleIntake;
    TriangleIntakeCommand triangleIntakeCommand;
    ViperSlidesSubSystem viperSlidesSubSystem;
    OuttakePivotSub outtakePivot;
    CrankSlideSubSystem crank;

    IntakePivot intakePivot;
    public void pickUp() {
        intakePivot.deploy();
        sleep(100);
        triangleIntakeCommand.execute();
        sleep(100);
        intakePivot.undeploy();
        sleep(100);
        triangleIntake.eject();
    }
    public void score() {
    outtakePivot.Up();
    ViperSlidesSubSystem.SetTgPosToExtend();
    outtakePivot.Down();
    ViperSlidesSubSystem.SetTgPosToRetract();
    }
    public void Auto() {
        viperSlidesSubSystem = new ViperSlidesSubSystem(hardwareMap);
        outtakePivot = new OuttakePivotSub(hardwareMap,"AlignServo1","AlignServo2");
        crank = new CrankSlideSubSystem(hardwareMap,"CrankLeft","CrankRight");
        triangleIntake = new TriangleIntake(hardwareMap,"Ti", "Fi", "Bi","pivot");
        colorSensor = new ColorSensorSubSystem(hardwareMap,"ColorSensor");
        triangleIntakeCommand = new TriangleIntakeCommand(triangleIntake,colorSensor, Color.BLUE,telemetry);
        intakePivot = new IntakePivot(hardwareMap);
        telemetry.addData("sensed color",triangleIntakeCommand.c);
        Pose2d StartPose = new Pose2d(-26,-64.5,Math.toRadians(90));
        MecanumDrive Drive = new MecanumDrive(hardwareMap,StartPose);

        Action path0 = Drive.actionBuilder(StartPose)
                .splineToSplineHeading(new Pose2d(-55, -55, Math.toRadians(45)), Math.toRadians(0))
                .build();
        Action path1 = Drive.actionBuilder(new Pose2d(-55, -55, Math.toRadians(45)))
                .waitSeconds(2)
                .splineToSplineHeading(new Pose2d(-48,-36.5, Math.toRadians(90)),0)
                .build();
        Action path2 = Drive.actionBuilder(new Pose2d(-48,-36.5, Math.toRadians(90)))
                .waitSeconds(1.25)
                .splineToSplineHeading(new Pose2d(-55, -55, Math.toRadians(45)), Math.toRadians(0))
                .build();
        Action path3 = Drive.actionBuilder(new Pose2d(-55, -55, Math.toRadians(45)))
                .waitSeconds(2)
                .splineToSplineHeading(new Pose2d(-58, -36.5, Math.toRadians(90)), Math.toRadians(270))
                .build();
        Action path4 = Drive.actionBuilder(new Pose2d(-58, -36.5, Math.toRadians(90)))
                .waitSeconds(1.25)
                .splineToSplineHeading(new Pose2d(-55, -55, Math.toRadians(45)), Math.toRadians(0))
                .build();
        Action path5 = Drive.actionBuilder (new Pose2d(-55, -55, Math.toRadians(45)))
                .waitSeconds(2)
                .splineToSplineHeading(new Pose2d(-55,-25, Math.toRadians(180)),Math.toRadians(270))
                .build();
        Action path6 = Drive.actionBuilder (new Pose2d(-55,-25, Math.toRadians(180)))
                .waitSeconds(1.25)
                .splineToSplineHeading(new Pose2d(-50, -55, Math.toRadians(45)), Math.toRadians(0))
                .build();
        Action path7 = Drive.actionBuilder (new Pose2d(-50, -55, Math.toRadians(45)))
                .waitSeconds(2)
                .splineToSplineHeading(new Pose2d(55,-55,Math.toRadians(90)),0)
                .build();


        Actions.runBlocking(path0);
        score();
        Actions.runBlocking(path1);
        pickUp();
        Actions.runBlocking(path2);
        score();
        Actions.runBlocking(path3);
        pickUp();
        Actions.runBlocking(path4);
        score();
        Actions.runBlocking(path5);
        pickUp();
        Actions.runBlocking(path6);
        score();
        Actions.runBlocking(path7);
        pickUp();
    }
    @Override
    public void runOpMode() throws InterruptedException {
        Auto();

    }
}


//package com.example.meepmeeptesting;
//
//import com.acmerobotics.roadrunner.Action;
//import com.acmerobotics.roadrunner.Pose2d;
//import com.acmerobotics.roadrunner.Vector2d;
//import com.noahbres.meepmeep.MeepMeep;
//import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
//import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
//
//public class MeepMeepTesting {
//    public static void main(String[] args) {
//        MeepMeep meepMeep = new MeepMeep(600);
//
//        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
//                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
//                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
//                .build();
//
//        Pose2d beginPose = new Pose2d(-26,-64.5,Math.toRadians(90));
//        myBot.runAction(myBot.getDrive().actionBuilder(beginPose)
//                .splineToSplineHeading(new Pose2d(-55, -55, Math.toRadians(45)), Math.toRadians(0))
//                .waitSeconds(2)
//                .splineToSplineHeading(new Pose2d(-48,-36.5, Math.toRadians(90)),0)
//                .waitSeconds(1.5)
//                .splineToSplineHeading(new Pose2d(-55, -55, Math.toRadians(45)), Math.toRadians(0))
//                .waitSeconds(2)
//                .splineToSplineHeading(new Pose2d(-58, -36.5, Math.toRadians(90)), Math.toRadians(270))
//                .waitSeconds(1.5)
//                .splineToSplineHeading(new Pose2d(-55, -55, Math.toRadians(45)), Math.toRadians(45))
//                .waitSeconds(2)
//                .splineToSplineHeading(new Pose2d(-55,-25, Math.toRadians(180)),Math.toRadians(270))
//                .waitSeconds(1.5)
//                .splineToSplineHeading(new Pose2d(-50, -55, Math.toRadians(45)), Math.toRadians(90))
//                .waitSeconds(2)
//                .splineToSplineHeading(new Pose2d(55,-55,Math.toRadians(90)),270)
//                .build());
//
//        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
//                .setDarkMode(true)
//                .setBackgroundAlpha(0.95f)
//                .addEntity(myBot)
//                .start();
//    }
//}
