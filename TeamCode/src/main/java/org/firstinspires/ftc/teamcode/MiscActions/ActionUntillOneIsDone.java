package org.firstinspires.ftc.teamcode.MiscActions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

public class ActionUntillOneIsDone implements Action
{
    Action[] actions;
    boolean actionRunning = true;

    public ActionUntillOneIsDone(Action... actions)
    {
        this.actions = actions;
    }

    @Override
    public boolean run(@NonNull TelemetryPacket telemetryPacket)
    {
        for (Action action : actions) {
            actionRunning = actionRunning && action.run(telemetryPacket);//if action.run returns false, then end the whole thing and return false
        }
        return actionRunning;
    }
}
