package org.firstinspires.ftc.teamcode.MiscActions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

import java.util.function.BooleanSupplier;

public class ConditionLoopAction implements Action
{
    Action action;
    BooleanSupplier condition;

    public ConditionLoopAction(Action action, BooleanSupplier condition)
    {
        this.action = action;
        this.condition = condition;
    }

    @Override
    public boolean run(@NonNull TelemetryPacket telemetryPacket)
    {
        action.run(telemetryPacket);
        return condition.getAsBoolean();
    }
}
