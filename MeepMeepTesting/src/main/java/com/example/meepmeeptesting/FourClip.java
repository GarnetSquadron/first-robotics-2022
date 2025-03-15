package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class FourClip {

    RoadRunnerBotEntity myBot;
    Pose2d beginPose = new Pose2d(26,-62, Math.toRadians(90));

    FourClip(RoadRunnerBotEntity myBot){
        this.myBot = myBot;
    }
    public void run(){
       myBot.runAction(myBot.getDrive().actionBuilder(beginPose)
                .splineToConstantHeading(new Vector2d(-2,-34),90)
                .waitSeconds(0.7)
                .setTangent(-Math.PI/2)
                .splineToLinearHeading(new Pose2d(30, -41, Math.toRadians(30)), Math.toRadians(0))
                .waitSeconds(0.7)
                .splineToLinearHeading(new Pose2d(35, -41, Math.toRadians(-40)), Math.toRadians(3))
                .splineToLinearHeading(new Pose2d(35, -41, Math.toRadians(30)), Math.toRadians(0))
                .waitSeconds(0.7)
                .splineToLinearHeading(new Pose2d(42, -41, Math.toRadians(-40)), Math.toRadians(3))
                .splineToLinearHeading(new Pose2d(42, -41, Math.toRadians(30)), Math.toRadians(0))
                .waitSeconds(0.7)
                .splineToLinearHeading(new Pose2d(41, -41, Math.toRadians(-40)), Math.toRadians(3))
                .setTangent(-75)
                .splineToSplineHeading(new Pose2d(36, -50, Math.toRadians(90)), Math.toRadians(-90))
                .splineToLinearHeading(new Pose2d(36, -60, Math.toRadians(90)), Math.toRadians(-90))
                .waitSeconds(0.7)
                .setTangent(Math.toRadians(160))
                       .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(14, -40, Math.toRadians(270)), Math.toRadians(90))
                .waitSeconds(0.7)
                .setTangent(-90)
                .splineToSplineHeading(new Pose2d(36, -50, Math.toRadians(90)), Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(36,-60),Math.toRadians(-90))
                .waitSeconds(0.7)
                .setTangent(Math.toRadians(160))
                .splineToLinearHeading(new Pose2d(12, -40, Math.toRadians(270)), Math.toRadians(90))
                .waitSeconds(0.7)
                .setTangent(-90)
                .splineToSplineHeading(new Pose2d(36, -50, Math.toRadians(90)), Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(36,-60),Math.toRadians(-90))
                .waitSeconds(0.7)
                .setTangent(Math.toRadians(160))
                .splineToLinearHeading(new Pose2d(10, -40, Math.toRadians(270)), Math.toRadians(90))
                .waitSeconds(0.7)
                .setTangent(-90)
                .splineToConstantHeading(new Vector2d(36,-60),6)
                .build());
    }
}
