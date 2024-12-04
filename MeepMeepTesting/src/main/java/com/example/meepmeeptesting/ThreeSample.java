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
                .splineToConstantHeading(new Vector2d(-34,-26),90)
                .waitSeconds(0.7)
                .splineToConstantHeading(new Vector2d(-55,-55),10)
                .turn(Math.toRadians(225))
                .waitSeconds(0.7)
                .splineToConstantHeading(new Vector2d(-44,-26),90)
                .turn(Math.toRadians(135))
                .waitSeconds(0.7)
                .splineToConstantHeading(new Vector2d(-55,-55),10)
                .turn(Math.toRadians(225))
                .waitSeconds(0.7)
                .splineToConstantHeading(new Vector2d(-54,-26),90)
                .turn(Math.toRadians(135))
                .waitSeconds(0.7)
                .splineToConstantHeading(new Vector2d(-55,-55),10)
                .turn(Math.toRadians(225))
               .waitSeconds(0.7)
                .splineToLinearHeading(new Pose2d(58, -62, Math.toRadians(90)), Math.toRadians(0))
                .build());
}
}