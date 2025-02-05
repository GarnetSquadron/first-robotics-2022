package org.firstinspires.ftc.teamcode.MiscActions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

import java.util.function.BooleanSupplier;

/**
 * not to be confused with ConditionalAction
 */
public class WaitForConditionAction implements Action {
    Action action;
    BooleanSupplier condition;
    boolean conditionMet = false;
    public WaitForConditionAction(Action action, BooleanSupplier condition){
        this.action = action;
        this.condition = condition;
    }
    @Override
    public boolean run(@NonNull TelemetryPacket telemetryPacket) {
        if(conditionMet){
            return action.run(telemetryPacket);
        }
        else {
            conditionMet = condition.getAsBoolean();
        }
        return false;
    }
}
