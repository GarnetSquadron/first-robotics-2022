package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        Pose2d beginPose = new Pose2d(-26,-70,Math.toRadians(90));
        Pose2d tgtPose = new Pose2d(20,20,Math.PI);
        myBot.runAction(myBot.getDrive().actionBuilder(beginPose)
                .splineToConstantHeading(new Vector2d(-45,-34),0)
                .splineToConstantHeading(new Vector2d(-60,-60),0)
                .splineToConstantHeading(new Vector2d(-70,-34),0)
                .splineToConstantHeading(new Vector2d(-60,-60),0)
                .splineToConstantHeading(new Vector2d(-54,-34),0)
                .splineToConstantHeading(new Vector2d(-60,-60),0)
                .splineToConstantHeading(new Vector2d(56,-60),0)
                        .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}