package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class VisionAuto {
    RoadRunnerBotEntity myBot;
    Pose2d beginPose = new Pose2d(-26,-62,Math.toRadians(180));
    VisionAuto (RoadRunnerBotEntity myBot){
        this.myBot = myBot;
    }
    public void run(){
        myBot.runAction(myBot.getDrive().actionBuilder(beginPose)
                .splineToLinearHeading(new Pose2d(-55, -55,Math.toRadians(45)), 10)
                .waitSeconds(0.7)
                .splineToLinearHeading(new Pose2d(-48, -48,Math.toRadians(90)), 45)
                .waitSeconds(0.7)
                .splineToLinearHeading(new Pose2d(-55, -55,Math.toRadians(45)), 10)
                .waitSeconds(0.7)
                .splineToLinearHeading(new Pose2d(-58, -48,Math.toRadians(90)), 90)
                .waitSeconds(0.7)
                .splineToLinearHeading(new Pose2d(-55, -55,Math.toRadians(45)), 10)
                .waitSeconds(0.7)
                .splineToLinearHeading(new Pose2d(-50, -46,Math.toRadians(133)), 90)
                .waitSeconds(0.7)
                .setTangent(-90)
                .splineToLinearHeading(new Pose2d(-55, -55,Math.toRadians(45)), 10)
                .waitSeconds(0.7)
                .splineToLinearHeading(new Pose2d(-24, -12, Math.toRadians(0)), 0)
                .waitSeconds(1.4)
                .setTangent(-180)
                .splineToLinearHeading(new Pose2d(-55, -55,Math.toRadians(45)), 10)
                .waitSeconds(0.7)
                .splineToLinearHeading(new Pose2d(-24, -8, Math.toRadians(0)), 0)
                .waitSeconds(1.4)
                .setTangent(-180)
                .splineToLinearHeading(new Pose2d(-55, -55,Math.toRadians(45)), 10)
                .waitSeconds(0.7)
                .splineToLinearHeading(new Pose2d(-24, -10, Math.toRadians(0)), 0)
                .build());
    }
}