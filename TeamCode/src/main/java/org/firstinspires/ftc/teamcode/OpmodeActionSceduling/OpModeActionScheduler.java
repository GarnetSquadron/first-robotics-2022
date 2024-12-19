package org.firstinspires.ftc.teamcode.OpmodeActionSceduling;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Actions;
import com.acmerobotics.roadrunner.ParallelAction;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.util.ArrayList;

public class OpModeActionScheduler {
    ArrayList <Action> actions;
    OpModeActionScheduler(){
    }
    public void start(Action action){
        actions.add(action);
    }
    public Action update(){
        Action updatedAction = new ParallelAction(
                actions
        );
        actions.clear();
        return updatedAction;
    }

}
