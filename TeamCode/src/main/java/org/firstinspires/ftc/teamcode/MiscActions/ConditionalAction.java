package org.firstinspires.ftc.teamcode.MiscActions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

import java.util.function.BooleanSupplier;

/**
 * not to be confused with WaitForConditionAction
 */
public class ConditionalAction implements Action
{
    Action action;
    BooleanSupplier condition;

    public ConditionalAction(Action action, BooleanSupplier condition)
    {
        this.action = action;
        this.condition = condition;
    }

    @Override
    public boolean run(@NonNull TelemetryPacket telemetryPacket)
    {
        if (condition.getAsBoolean()) {
            return action.run(telemetryPacket);
        }
        return true;
    }
}
