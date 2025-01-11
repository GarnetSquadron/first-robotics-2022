package com.example.meepmeeptesting;
//
import com.acmerobotics.roadrunner.Pose2d;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class FourSample {
    RoadRunnerBotEntity myBot;
    Pose2d beginPose = new Pose2d(-23, -62, Math.toRadians(90));

    FourSample(RoadRunnerBotEntity myBot) {
        this.myBot = myBot;
    }

    public void run() {
        myBot.runAction(myBot.getDrive().actionBuilder(beginPose)
                .splineToLinearHeading(new Pose2d(-55, -55, Math.toRadians(45)), 10)
                .waitSeconds(0.7)
                .splineToLinearHeading(new Pose2d(-48, -53, Math.toRadians(90)), 45)
                .waitSeconds(0.7)
                .splineToLinearHeading(new Pose2d(-55, -55, Math.toRadians(45)), 10)
                .waitSeconds(0.7)
                .splineToLinearHeading(new Pose2d(-58, -53, Math.toRadians(90)), 90)
                .waitSeconds(0.7)
                .splineToLinearHeading(new Pose2d(-55, -55, Math.toRadians(45)), 10)
                .waitSeconds(0.7)
                .splineToLinearHeading(new Pose2d(-53, -43, Math.toRadians(133)), 90)
                .waitSeconds(0.7)
                .setTangent(-90)
                .splineToLinearHeading(new Pose2d(-55, -55, Math.toRadians(45)), 10)
                .waitSeconds(0.7)
                .splineToLinearHeading(new Pose2d(-24, -12, Math.toRadians(0)), 0)
                .build());
    }
}