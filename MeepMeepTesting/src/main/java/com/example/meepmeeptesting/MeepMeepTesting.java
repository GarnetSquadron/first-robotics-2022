package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Rotation2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.util.Timer;

public class MeepMeepTesting {
        static Pose2d[] excludeElement0(Pose2d[] arr){
            Pose2d[] arr2 = new Pose2d[arr.length-1];
            System.arraycopy(arr, 1, arr2, 0, arr.length - 1);
            return arr2;
        }
        static double getTangentAngle(Pose2d tgtPose, Pose2d beginPose){
            return Math.PI+Math.atan((tgtPose.position.y-beginPose.position.y)/(tgtPose.position.x-beginPose.position.x));
        }
        static TrajectoryActionBuilder EasyLine(TrajectoryActionBuilder trajectoryActionBuilder, Pose2d beginPose, Pose2d tgtPose){
            return trajectoryActionBuilder
                    .setTangent(getTangentAngle(beginPose,tgtPose))
                    .splineToSplineHeading(tgtPose, getTangentAngle(tgtPose,beginPose));
        }

        /**
         * this basically adds a sequence of straight lines to a trajectory action builder.
         * NOTE: for some reason it sometimes seems to overshoot before moving backwards to compensate. it possibly has to do with the acceleration constraints, but it could also be that i somehow messed up the tangents to be reversed. idk i dont really feel like fixing it
         * @param trajectoryActionBuilder the original trajectory action builder
         * @param beginPose
         * @param positions
         * @return
         */
        static TrajectoryActionBuilder EasyLines(TrajectoryActionBuilder trajectoryActionBuilder,Pose2d beginPose,Pose2d... positions){
            if (positions.length == 0) {
                return trajectoryActionBuilder;
            }
            else{
                Pose2d[] newPoses = new Pose2d[positions.length-1];
                System.arraycopy(positions, 1, newPoses, 0, positions.length - 1);
                return EasyLines(EasyLine(trajectoryActionBuilder,beginPose,positions[0]),positions[0],newPoses);
            }
        }
        static TrajectoryActionBuilder EasyLines(RoadRunnerBotEntity myBot,Pose2d beginPose,Pose2d... positions){
            TrajectoryActionBuilder path= myBot.getDrive().actionBuilder(beginPose);
            return EasyLines(path,beginPose,positions);
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
            MeepMeep meepMeep = new MeepMeep(600);
            RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                    // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                    .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                    .build();

            //Auto routes
            ThreeClip c = new ThreeClip(myBot); //c.run();
            ThreeSample s = new ThreeSample(myBot); //s.run();
            SoloAuto SOLO = new SoloAuto(myBot); //SOLO.run();
            VisionAutoSixSamp vs = new VisionAutoSixSamp(myBot); //vs.run();
            VisionAutoClip vc = new VisionAutoClip(myBot); //vc.run();

            Pose2d beginPose = new Pose2d(26,-62,Math.toRadians(90));
            Pose2d tgtPose = new Pose2d(20,20,Math.PI);
            vs.run();

            meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                    .setDarkMode(true)
                    .setBackgroundAlpha(0.95f)
                    .addEntity(myBot)
                    .start();
        }
    }//