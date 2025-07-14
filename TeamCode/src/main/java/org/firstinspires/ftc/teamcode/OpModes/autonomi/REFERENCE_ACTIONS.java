package org.firstinspires.ftc.teamcode.OpModes.autonomi;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;

import org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff.Bot;

public class REFERENCE_ACTIONS
{
    Bot bot;

    public Action DoEverything()
    {
        return new SequentialAction(
                bot.SafeDeployIntake(1),//deploy the intake. 1 is maximum distance, 0 is minimum distance
                bot.SafeUndeployIntake(),//undeploy the intake
                bot.AutoBasketDrop(),//move the outtake in a position to drop a sample in the basket
                bot.outtake.SafeVipersDown(),//bring the vipers down, and this makes sure that the outtake is flipped first so that it doesn't attempt level 4 hang
                bot.Transfer(),//transfer
                bot.intake.PoiseToGrab(1), //get ready to grab a sample by rotating the intake to above a sample
                bot.intake.wrist.runToDegrees(90),//run the wrist to a given number of degrees
                bot.IntakeGrab(),//extend slides if not already, pivot intake down and then close claw
                bot.IntakeDropSample(),//pivot the intake up a tad and drop the sample
                bot.intake.claw.Open(),//just open intake claw and nothing else
                bot.intake.claw.Close(),//just close intake claw and nothing else
                bot.outtake.claw.Open(),//open outtake claw
                bot.outtake.claw.Close(),//close outtake claw
                bot.IntakeGrab(),//make the claw dip down to grab
                bot.IntakeDropSample(),//make the intake drop the sample and come up
                bot.outtake.grabSpecPos(),//position the outtake for grabbing specimens
                bot.outtake.prepareToGrabSpecOffWall(),//closes the claw(presumably around a specimen), and safely raises the vipers to get it off the wall, and then rotates the outtake pivot for placing specimens
                bot.outtake.placeSpecV2()//place specimens on the high chamber
        );
    }
}
