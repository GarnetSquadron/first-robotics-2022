package org.firstinspires.ftc.teamcode.MiscActions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

public class NullAction implements Action {
    @Override
    public boolean run(@NonNull TelemetryPacket telemetryPacket) {
        return false;
    }
}
