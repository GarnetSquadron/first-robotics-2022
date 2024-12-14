package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class NewGAutoMM {
    RoadRunnerBotEntity myBot;
    double pushY = -45;
    double pushX = 47;
    Pose2d beginPose = new Pose2d(-26,-62,Math.toRadians(90));
    NewGAutoMM (RoadRunnerBotEntity myBot){
        this.myBot = myBot;
    }
    public void run(){ {

        myBot.runAction(myBot.getDrive().actionBuilder(beginPose)
            .splineToConstantHeading(new Vector2d(2,-25),0)
            .splineToConstantHeading(new Vector2d(-62,-34),0)
            .splineToConstantHeading(new Vector2d(-62,-62),0)
            .splineToConstantHeading(new Vector2d(-62,-34),0)
            .splineToConstantHeading(new Vector2d(-62,-62),0)
            .splineToConstantHeading(new Vector2d(56,-32),0)
            .splineToConstantHeading(new Vector2d(56,-68),0)

            .build());

}
    }
}