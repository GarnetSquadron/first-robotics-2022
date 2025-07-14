package org.firstinspires.ftc.teamcode.MiscActions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;

import java.util.function.BooleanSupplier;

public class IfThenAction implements Action
{
    BooleanSupplier condition;
    boolean firstIter = true;
    Action action;

    public IfThenAction(BooleanSupplier condition, Action action)
    {
        this.condition = condition;
        this.action = action;
    }

    @Override
    public boolean run(@NonNull TelemetryPacket telemetryPacket)
    {
        if (firstIter) {
            firstIter = false;
            return condition.getAsBoolean();
        } else {
            return action.run(telemetryPacket);
        }
    }

    public Action Else(Action action)
    {
        return new ParallelAction(this, new IfThenAction(() -> !condition.getAsBoolean(), action));
    }
}
