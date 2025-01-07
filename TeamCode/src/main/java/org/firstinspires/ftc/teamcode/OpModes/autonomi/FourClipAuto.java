package org.firstinspires.ftc.teamcode.OpModes.autonomi;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Subsystems.Bot;

@Autonomous(name = "FourClip")
public class FourClipAuto extends LinearOpMode {
    double pushY = -45;
    double pushX = 47;
    Vector2d pushPos = new Vector2d(pushX,pushY);
    Vector2d grabPos = new Vector2d(56,-60);
    double placeY = -34;
    Bot bot;
    Pose2d beginPose = new Pose2d(26,-62, Math.toRadians(90));


    Action pushSamples = bot.drive.actionBuilder(beginPose)
            .splineToConstantHeading(new Vector2d(-0,-34),90)
            .waitSeconds(0.7)
            .setTangent(182)
            .splineToConstantHeading(new Vector2d(36,-30),45)
            .splineToConstantHeading(new Vector2d(34,-0),0)
            .splineToConstantHeading(new Vector2d(40,-0),5)
            .splineToConstantHeading(pushPos,2)
            .splineToConstantHeading(new Vector2d(43,-0),0)
            .splineToConstantHeading(pushPos,2)
            .splineToConstantHeading(new Vector2d(52,-0),0)
            .splineToConstantHeading(pushPos,2)
            .build();
    Action grabSpecimen1 = bot.drive.actionBuilder(beginPose)
            .setTangent(-90)
            .splineToConstantHeading(grabPos,6)
            .build();
    Action placeSpecimen1 = bot.drive.actionBuilder(beginPose)
            .splineToLinearHeading(new Pose2d(5, placeY, Math.toRadians(90)), Math.toRadians(0))
            .build();
    Action grabSpecimen2 = bot.drive.actionBuilder(beginPose)
            .setTangent(130)
            .splineToConstantHeading(grabPos,10)
            .build();
    Action getPlaceSpecimen2 = bot.drive.actionBuilder(beginPose)
            .splineToLinearHeading(new Pose2d(8,placeY, Math.toRadians(90)), Math.toRadians(0))
            .build();
    Action grabSpecimen3 = bot.drive.actionBuilder(beginPose)
            .splineToConstantHeading(grabPos,10)
            .build();


    @Override
    public void runOpMode(){
        waitForStart();
        Actions.runBlocking(
                new SequentialAction(
                        pushSamples,
                        grabSpecimen1,

                        placeSpecimen1
                )
        );
    }
}

