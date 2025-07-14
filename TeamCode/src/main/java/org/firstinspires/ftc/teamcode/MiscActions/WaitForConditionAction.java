package org.firstinspires.ftc.teamcode.MiscActions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

import java.util.function.BooleanSupplier;

/**
 * not to be confused with ConditionalAction
 */
public class WaitForConditionAction implements Action
{
    BooleanSupplier condition;

    public WaitForConditionAction(BooleanSupplier condition)
    {
        this.condition = condition;
    }

    @Override
    public boolean run(@NonNull TelemetryPacket telemetryPacket)
    {
        return !condition.getAsBoolean();
    }
}
