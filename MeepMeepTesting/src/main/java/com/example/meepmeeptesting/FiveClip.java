package com.example.meepmeeptesting;
//SLIGHTLY BROKEN!!!!//
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class FiveClip {

    RoadRunnerBotEntity myBot;
    Pose2d beginPose = new Pose2d(26,-62, Math.toRadians(90));
    FiveClip(RoadRunnerBotEntity myBot){
        this.myBot = myBot;
    }
    public void run(){
        myBot.runAction(myBot.getDrive().actionBuilder(beginPose)
                .splineToConstantHeading(new Vector2d(-5,-34),90)
                .waitSeconds(0.7)
                .setTangent(182)
                .splineToLinearHeading(new Pose2d(33, -41, Math.toRadians(30)), Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(35, -41, Math.toRadians(-40)), Math.toRadians(3))
                .splineToLinearHeading(new Pose2d(35, -41, Math.toRadians(30)), Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(38, -41, Math.toRadians(-40)), Math.toRadians(3))
                .splineToLinearHeading(new Pose2d(38, -41, Math.toRadians(30)), Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(41, -41, Math.toRadians(-40)), Math.toRadians(3))
                .waitSeconds(2)
                .setTangent(-75)
                .splineToLinearHeading(new Pose2d(50, -60, Math.toRadians(90)), Math.toRadians(3))
                .waitSeconds(0.7)
                .splineToLinearHeading(new Pose2d(0, -34, Math.toRadians(90)), Math.toRadians(0))
                .waitSeconds(0.7)
                .setTangent(-90)
                .splineToConstantHeading(new Vector2d(50,-60),6)
                .waitSeconds(0.7)
                .splineToLinearHeading(new Pose2d(5,-34, Math.toRadians(90)), Math.toRadians(0))
                .waitSeconds(0.7)
                .setTangent(-90)
                .splineToConstantHeading(new Vector2d(50,-60),6)
                .waitSeconds(0.7)
                .splineToLinearHeading(new Pose2d(8,-34, Math.toRadians(90)), Math.toRadians(0))
                .waitSeconds(0.7)
                .setTangent(-90)
                .splineToConstantHeading(new Vector2d(50,-60),6)
                .waitSeconds(0.7)
                .splineToLinearHeading(new Pose2d(11,-34, Math.toRadians(90)), Math.toRadians(0))
                .waitSeconds(0.7)
                .setTangent(-90)
                .splineToConstantHeading(new Vector2d(56,-60),6)
                .build());
    }
}
