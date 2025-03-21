package org.firstinspires.ftc.teamcode.MiscActions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

import java.util.function.BooleanSupplier;

public class LoopAction implements Action {
    public LoopAction(Action action, BooleanSupplier condition){

    }
    @Override
    public boolean run(@NonNull TelemetryPacket telemetryPacket) {

        return false;
    }
}
