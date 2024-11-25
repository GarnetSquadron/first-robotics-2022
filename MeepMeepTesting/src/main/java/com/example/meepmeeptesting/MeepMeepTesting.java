package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Rotation2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    static double getTangentAngle(Pose2d tgtPose, Pose2d beginPose){
        return Math.PI+Math.atan((tgtPose.position.y-beginPose.position.y)/(tgtPose.position.x-beginPose.position.x));
    }
    static Action EasyLine(Pose2d tgtPose, Pose2d beginPose,RoadRunnerBotEntity myBot){
        return myBot.getDrive().actionBuilder(beginPose)
                .setTangent(getTangentAngle(beginPose,tgtPose))
                .splineToSplineHeading(tgtPose, getTangentAngle(tgtPose,beginPose))
                .build();
    }
    static Action RotateMove(Pose2d beginPose, Vector2d tgtp, double spinRate,RoadRunnerBotEntity myBot){
        Vector2d v=tgtp.div(2);
        return myBot.getDrive().actionBuilder(beginPose)
                .splineToSplineHeading(new Pose2d(beginPose.position.plus(tgtp.minus(beginPose.position).div(2)), Math.PI/2), Math.PI)
                .splineToSplineHeading(new Pose2d(tgtp, 0), Math.PI)
//                .strafeToSplineHeading(tgtp.times(1/4),2)
//
//                .strafeToSplineHeading(tgtp.times(2/4),4)
//                .strafeToSplineHeading(tgtp.times(3/4),6)
//                .strafeToSplineHeading(tgtp,8)
                .build();
    }
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        Pose2d beginPose = new Pose2d(60,0,Math.PI);
        Pose2d tgtPose = new Pose2d(-60,56,Math.PI/3);

        myBot.runAction(
                EasyLine(tgtPose,beginPose,myBot)
                    );

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}