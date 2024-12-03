package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Rotation2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
import com.sun.tools.javac.util.ArrayUtils;

import java.util.Arrays;

import sun.security.util.ArrayUtil;

public class MeepMeepTesting {
    static Pose2d[] excludeElement0(Pose2d[] arr){
        Pose2d[] arr2 = new Pose2d[arr.length-1];
        for (int i=0; i<arr.length-1;i++){
            arr2[i]=arr[i+1];
        }
        return arr2;
    }
    static double getTangentAngle(Pose2d tgtPose, Pose2d beginPose){
        return Math.PI+Math.atan((tgtPose.position.y-beginPose.position.y)/(tgtPose.position.x-beginPose.position.x));
    }
    static TrajectoryActionBuilder EasyLine(TrajectoryActionBuilder trajectoryActionBuilder,Pose2d beginPose, Pose2d tgtPose){
        return trajectoryActionBuilder
                .setTangent(getTangentAngle(beginPose,tgtPose))
                .splineToSplineHeading(tgtPose, getTangentAngle(tgtPose,beginPose));
    }
    static TrajectoryActionBuilder EasyLines(TrajectoryActionBuilder trajectoryActionBuilder,Pose2d beginPose,Pose2d... positions){
        if (positions.length == 0) {
            return trajectoryActionBuilder;
        }
        else{
            Pose2d[] newPoses =excludeElement0(positions);
            return EasyLines(EasyLine(trajectoryActionBuilder,beginPose,positions[0]),positions[0],newPoses);
        }
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

            Pose2d beginPose = new Pose2d(26,-62,Math.toRadians(90));
            Pose2d tgtPose = new Pose2d(20,20,Math.PI);
            TrajectoryActionBuilder actionB = myBot.getDrive().actionBuilder(beginPose);
            myBot.runAction(
                    EasyLines(actionB,beginPose,new Pose2d(0,0,0),new Pose2d(0,20,Math.PI/4),new Pose2d(20,70,0),new Pose2d(0,20,Math.PI/4)).build()
            );

            meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                    .setDarkMode(true)
                    .setBackgroundAlpha(0.95f)
                    .addEntity(myBot)
                    .start();
    }
}
