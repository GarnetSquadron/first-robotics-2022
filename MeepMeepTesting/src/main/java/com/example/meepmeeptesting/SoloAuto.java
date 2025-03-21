package com.example.meepmeeptesting;
//
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class SoloAuto {
    RoadRunnerBotEntity myBot;
    double pushY = -45;
    double pushX = 47;
    Pose2d beginPose = new Pose2d(-26,-62,Math.toRadians(90));
    SoloAuto (RoadRunnerBotEntity myBot){
        this.myBot = myBot;
    }
    public void run(){
        myBot.runAction(myBot.getDrive().actionBuilder(beginPose)
                .splineToConstantHeading(new Vector2d(-0,-34),90)
                .waitSeconds(0.7)
                .setTangent(182)
                .splineToConstantHeading(new Vector2d(36,-30),45)
                .splineToConstantHeading(new Vector2d(34,-0),0)
                .splineToConstantHeading(new Vector2d(40,-0),5)
                .splineToConstantHeading(new Vector2d(pushX, pushY),2)
                .splineToConstantHeading(new Vector2d(43,-0),0)
                .splineToConstantHeading(new Vector2d(pushX, pushY),2)
                .splineToConstantHeading(new Vector2d(52,-0),0)
                .splineToConstantHeading(new Vector2d(pushX, pushY),2)
                .waitSeconds(2)
                .splineToLinearHeading(new Pose2d(56, -60, Math.toRadians(270)), Math.toRadians(0))
                .waitSeconds(0.7)
                .setTangent(90)
                .splineToLinearHeading(new Pose2d(5, -34, Math.toRadians(90)), Math.toRadians(0))
                .waitSeconds(0.7)
                .splineToConstantHeading(new Vector2d(5,-50),2)
                .splineToLinearHeading(new Pose2d(56, -34, Math.toRadians(270)), Math.toRadians(10))
                .splineToConstantHeading(new Vector2d(56,-34),0)
                .waitSeconds(0.7)
                .splineToConstantHeading(new Vector2d(56,-60),6)
                .waitSeconds(0.7)
                .splineToConstantHeading(new Vector2d(56,-34),2)
                .splineToConstantHeading(new Vector2d(-0,-50),-5)
                .splineToLinearHeading(new Pose2d(-5, -34, Math.toRadians(90)), Math.toRadians(0))
                .waitSeconds(0.7) //break in routes
                .setTangent(80)
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
                .build());
    }
}
