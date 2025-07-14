package org.firstinspires.ftc.teamcode.MiscActions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.NullAction;

public class CancelableAction implements Action
{
    private final Action mainAction;
    private final Action failoverAction;
    private boolean failedOver = false;

    public CancelableAction(Action mainAction, Action failoverAction)
    {
        this.mainAction = mainAction;
        this.failoverAction = failoverAction;
    }

    public CancelableAction(Action mainAction)
    {
        this(mainAction, new NullAction());
    }

    @Override
    public boolean run(@NonNull TelemetryPacket telemetryPacket)
    {
        if (failedOver) {
            return failoverAction.run(telemetryPacket);
        }

        return mainAction.run(telemetryPacket);
    }

    public void failover()
    {
        failedOver = true;
    }

    public Action getFailoverAction()
    {
        return failoverAction;
    }
}
