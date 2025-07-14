package org.firstinspires.ftc.teamcode.hardwareClasses.motors;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class UpdatableMOTOR extends MOTOR
{
    double targetPower;
    boolean positionControl;

    public UpdatableMOTOR(HardwareMap hardwareMap, String name)
    {
        super(hardwareMap, name);
    }

    public class UpdatePower implements Action
    {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket)
        {
            encoder.updateVelocity();
            if (positionControl) {
                runToTargetPosition();
            } else {
                runWithTargetPower();
            }
            return true;
        }
    }

    public boolean inPositionControl()
    {
        return positionControl;
    }

    @Override
    public void setTargetPosition(double targetPosition)
    {
        positionControl = true;
        super.setTargetPosition(targetPosition);
    }

    public void runWithTargetPower()
    {
        setPower(targetPower);
    }

    public void setTargetPower(double targetPower)
    {
        positionControl = false;
        this.targetPower = targetPower;
    }
}
