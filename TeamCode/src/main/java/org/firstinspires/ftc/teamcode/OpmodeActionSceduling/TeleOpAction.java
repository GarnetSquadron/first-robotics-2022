package org.firstinspires.ftc.teamcode.OpmodeActionSceduling;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;

public class TeleOpAction{
    public Action action;
    public String ID;
    TeleOpAction(String ID, Action action){
        this.ID = ID;
        this.action = action;
    }

}
