package org.firstinspires.ftc.teamcode.MiscActions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

import java.util.function.BooleanSupplier;

public class LoopAction implements Action
{
    BooleanSupplier condition;
    Action action;

    public LoopAction(Action action, BooleanSupplier condition)
    {
        this.condition = condition;
        this.action = action;
    }

    @Override
    public boolean run(@NonNull TelemetryPacket telemetryPacket)
    {
        action.run(telemetryPacket);
        return condition.getAsBoolean();
    }
}
