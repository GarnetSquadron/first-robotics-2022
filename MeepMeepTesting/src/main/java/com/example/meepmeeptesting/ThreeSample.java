package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class ThreeSample {
    RoadRunnerBotEntity myBot;
    Pose2d beginPose = new Pose2d(-26,-62,Math.toRadians(180));
    ThreeSample (RoadRunnerBotEntity myBot){
        this.myBot = myBot;
    }
    public void run(){
        myBot.runAction(myBot.getDrive().actionBuilder(beginPose)
                .splineToLinearHeading(new Pose2d(-55, -55,Math.toRadians(45)), 10)
                .waitSeconds(0.7)
                .splineToLinearHeading(new Pose2d(-34, -26,Math.toRadians(180)), 45)
                .waitSeconds(0.7)
                .splineToLinearHeading(new Pose2d(-55, -55,Math.toRadians(45)), 10)
                .waitSeconds(0.7)
                .splineToLinearHeading(new Pose2d(-44, -26,Math.toRadians(180)), 90)
                .waitSeconds(0.7)
                .splineToLinearHeading(new Pose2d(-55, -55,Math.toRadians(45)), 10)
                .waitSeconds(0.7)
                .splineToLinearHeading(new Pose2d(-54, -26,Math.toRadians(180)), 90)
                .waitSeconds(0.7)
                .setTangent(-90)
                .splineToLinearHeading(new Pose2d(-55, -55,Math.toRadians(45)), 10)
                .waitSeconds(0.7)
                .splineToLinearHeading(new Pose2d(-24, 10, Math.toRadians(180)), 0)
                .build());
}
}