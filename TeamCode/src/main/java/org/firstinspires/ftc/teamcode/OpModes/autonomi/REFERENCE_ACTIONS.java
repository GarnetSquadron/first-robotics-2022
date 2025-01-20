package org.firstinspires.ftc.teamcode.OpModes.autonomi;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;

import org.firstinspires.ftc.teamcode.Subsystems.Bot;

public class REFERENCE_ACTIONS {
    Bot bot;
    public Action DoEverything(){
        return new SequentialAction(
                bot.SafeDeployIntake(1),//deploy the intake. 1 is maximum distance, 0 is minimum distance
                bot.SafeUndeployIntake(),//undeploy the intake
                bot.BasketDrop(),//move the outtake in a position to drop a sample in the basket
                bot.outtake.SafeVipersDown(),//bring the vipers down, and this makes sure that the outtake is flipped first so that it doesn't attempt level 4 hang
                bot.Transfer(),//transfer
                bot.intake.claw.Open(),//open intake claw
                bot.intake.claw.Close(),//close intake claw
                bot.outtake.claw.Open(),//open outtake claw
                bot.outtake.claw.Close(),//close outtake claw
                bot.IntakeGrab(),//make the claw dip down to grab
                bot.IntakeDropSample(),//make the intake drop the sample and come up
                bot.outtake.grabSpecPos(),//position the outtake for grabbing specimens
                bot.outtake.placeSpecPos()//position the outtake for placing specimens on the high chamber
        );
    }
}
