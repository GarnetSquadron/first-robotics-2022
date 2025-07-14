package org.firstinspires.ftc.teamcode.MiscActions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

import java.util.function.BooleanSupplier;

public class IfThenElseAction implements Action
{
    BooleanSupplier condition;
    Action action1, action2;
    boolean firstIter = true, conditionMet;

    public IfThenElseAction(BooleanSupplier condition, Action action1, Action action2)
    {
        this.condition = condition;
        this.action1 = action1;
        this.action2 = action2;
        //return new ParallelAction(new IfThenAction(condition, action1), new IfThenAction(condition,action2));
    }

    @Override
    public boolean run(@NonNull TelemetryPacket telemetryPacket)
    {
        if (firstIter) {
            firstIter = false;
            conditionMet = condition.getAsBoolean();
            return true;
        } else {
            if (conditionMet) {
                return action1.run(telemetryPacket);
            } else {
                return action2.run(telemetryPacket);
            }
        }
    }
}
