package org.firstinspires.ftc.teamcode.OpModes.autonomi;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Trajectory;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
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
import org.firstinspires.ftc.teamcode.Subsystems.TriangleIntake;
import org.firstinspires.ftc.teamcode.commands.TriangleIntakeCommand;
import org.firstinspires.ftc.teamcode.Subsystems.ViperSlidesSubSystem;
import org.firstinspires.ftc.teamcode.Subsystems.TriangleIntake;
import org.firstinspires.ftc.teamcode.enums.Color;
import org.firstinspires.ftc.teamcode.oldStuff.VoidsAndThings;

public class AnthonyAuto extends LinearOpMode {
    ColorSensorSubSystem colorSensor;
    TriangleIntake triangleIntake;
    TriangleIntakeCommand triangleIntakeCommand;
    ViperSlidesSubSystem viperSlidesSubsystem;

    DcMotor lf;
    DcMotor rf;
    DcMotor lb;
    DcMotor rb;
    public void Auto() {
        colorSensor = new ColorSensorSubSystem(hardwareMap,"ColorSensor");
        triangleIntake = new TriangleIntake(hardwareMap,"IntakeServo1", "IntakeServo2", "IntakeServo3","pivot");
        Pose2d StartPose = new Pose2d(-26,-64.5,Math.toRadians(90));
        triangleIntakeCommand = new TriangleIntakeCommand(triangleIntake,colorSensor, Color.RED,telemetry);
        //Raise Viperslides and score sample in highest bucket
        //Lower Viperslides
        //spline to sample
        MecanumDrive Drive = new MecanumDrive(hardwareMap,StartPose);
        Action BlueAutoBasket = Drive.actionBuilder(StartPose)
                .splineToConstantHeading(new Vector2d(-48,-36.5),0)
                .waitSeconds(1)
                //goes to first sample
                //insert code to pick up first sample
                .splineToSplineHeading(new Pose2d(-55, -55, Math.toRadians(225)), Math.toRadians(0))
                .waitSeconds(1)
                //goes to basket
                //put sample in basket
                //.splineTo(new Vector2d(-55,-40), 0)
                .splineToSplineHeading(new Pose2d(-58, -36.5, Math.toRadians(90)), Math.toRadians(270))
                .waitSeconds(2)
                .splineToSplineHeading(new Pose2d(-55, -55, Math.toRadians(225)), Math.toRadians(0))
                .waitSeconds(3)
                //put sample in basket
                //.splineTo(new Vector2d(-51, -51), 0)
                .splineToSplineHeading(new Pose2d(-55,-25, Math.toRadians(180)),Math.toRadians(270))
                .splineToSplineHeading(new Pose2d(-55, -55, Math.toRadians(225)), Math.toRadians(0))
                .waitSeconds(2)
                .turn(Math.toRadians(-45))
                //.splineToSplineHeading(new Pose2d(55,-55, Math.toRadians(90)),0)
//                .splineToConstantHeading(new Vector2d(56,-60),0)
                .build();
        Actions.runBlocking(BlueAutoBasket
        );
        //pick up sample
        //transfer sample to bucket
        //spline back to bucket
        //repeat
    }
    @Override
    public void runOpMode() throws InterruptedException {
        Auto();

    }
}
//
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
//                .splineToConstantHeading(new Vector2d(-48,-36.5),0)
//                .waitSeconds(1)
//                //goes to first sample
//                //insert code to pick up first sample
//                .splineToSplineHeading(new Pose2d(-55, -55, Math.toRadians(225)), Math.toRadians(0))
//                .waitSeconds(1)
//                //goes to basket
//                //put sample in basket
//                //.splineTo(new Vector2d(-55,-40), 0)
//                .splineToSplineHeading(new Pose2d(-58, -36.5, Math.toRadians(90)), Math.toRadians(270))
//                .waitSeconds(2)
//                .splineToSplineHeading(new Pose2d(-55, -55, Math.toRadians(225)), Math.toRadians(0))
//                .waitSeconds(3)
//                //put sample in basket
//                //.splineTo(new Vector2d(-51, -51), 0)
//                .splineToSplineHeading(new Pose2d(-55,-25, Math.toRadians(180)),Math.toRadians(270))
//                .splineToSplineHeading(new Pose2d(-50, -55, Math.toRadians(225)), Math.toRadians(0))
//                .waitSeconds(2)
//                .turn(Math.toRadians(-45))
//                .waitSeconds(1)
//                .splineToSplineHeading(new Pose2d(55,-55,Math.toRadians(90)),0)
//                //.forward(15)
//                //.splineToSplineHeading(new Pose2d(55,-55, Math.toRadians(90)),0)
////                .splineToConstantHeading(new Vector2d(56,-60),0)
//                .build());
//
//        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
//                .setDarkMode(true)
//                .setBackgroundAlpha(0.95f)
//                .addEntity(myBot)
//                .start();
//    }
//}