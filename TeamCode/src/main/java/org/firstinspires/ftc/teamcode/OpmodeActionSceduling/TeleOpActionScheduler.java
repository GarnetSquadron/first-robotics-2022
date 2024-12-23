package org.firstinspires.ftc.teamcode.OpmodeActionSceduling;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

import java.util.ArrayList;

public class TeleOpActionScheduler {
    ArrayList <Action> actions = new ArrayList<>();
    TelemetryPacket packet = new TelemetryPacket();

    public TeleOpActionScheduler(){
    }
    public void start(Action action){
        actions.add(action);
    }
    public void update(){
        ArrayList <Action> actionsKept = new ArrayList<>();//temporary list for keeping track of the actions we want
        for(Action action:actions){
            if(action.run(packet)){
                actionsKept.add(action);
            }
        }
        actions = actionsKept;
    }

}
