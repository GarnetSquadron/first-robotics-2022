package org.firstinspires.ftc.teamcode.OpmodeActionSceduling;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

public class ActionThingy {
    Action action;
    boolean running = false;
    TelemetryPacket telemetryPacket;
    ActionThingy(Action action,TelemetryPacket t){
        this.action = action;
        telemetryPacket = t;
    }
    public void start(){
        running = true;
    }
    public void update(){
        if(running){
            running = action.run(telemetryPacket);
        }
    }
}
