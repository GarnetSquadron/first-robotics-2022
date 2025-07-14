package org.firstinspires.ftc.teamcode.learningStuf;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ArmsStuff
{
    public RingArm ringArms;
    public WeddingVipers vipers;

    public ArmsStuff(HardwareMap hardwareMap)
    {
        ringArms = new RingArm(hardwareMap);
        vipers = new WeddingVipers(hardwareMap);
    }

    public Action funToUp()
    {
        return new Action()
        {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket)
            {
                ringArms.setPosition(100);
                vipers.setpower(1);
                return false;
            }
        };
    }
}
