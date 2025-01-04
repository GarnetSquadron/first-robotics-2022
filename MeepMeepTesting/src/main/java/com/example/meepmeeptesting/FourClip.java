package com.example.meepmeeptesting;
//SLIGHTLY BROKEN!!!!//
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class FourClip {
    double pushY = -45;
    double pushX = 47;
    RoadRunnerBotEntity myBot;
    Pose2d beginPose = new Pose2d(26,-62, Math.toRadians(90));
    FourClip(RoadRunnerBotEntity myBot){
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
                .setTangent(-90)
                .splineToConstantHeading(new Vector2d(56,-60),6)
                .waitSeconds(0.7)
                .splineToLinearHeading(new Pose2d(5, -34, Math.toRadians(90)), Math.toRadians(0))
                .waitSeconds(0.7)
                .setTangent(130)
                .splineToConstantHeading(new Vector2d(56,-60),10)
                .waitSeconds(0.7)
                .splineToLinearHeading(new Pose2d(8,-34, Math.toRadians(90)), Math.toRadians(0))
                .waitSeconds(0.7)
                .setTangent(130)
                .splineToConstantHeading(new Vector2d(56,-60),10)
                .waitSeconds(0.7)
                .splineToLinearHeading(new Pose2d(12,-34, Math.toRadians(90)), Math.toRadians(0))
                .waitSeconds(0.7)
                .setTangent(130)
                .splineToConstantHeading(new Vector2d(56,-60),10)
                .build());
    }
}
