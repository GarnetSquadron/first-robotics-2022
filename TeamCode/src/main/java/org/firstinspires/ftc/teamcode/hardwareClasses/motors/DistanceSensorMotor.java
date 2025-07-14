package org.firstinspires.ftc.teamcode.hardwareClasses.motors;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.encoders.DistanceSensorEncoderCombo;

public class DistanceSensorMotor extends LimitedMotor
{
    public DistanceSensorMotor(HardwareMap hardwareMap, String name, double minPos, double maxPos)
    {
        super(hardwareMap, name, minPos, maxPos);
    }

    public void setDistanceSensor(DistanceSensor distanceSensor)
    {
        setEncoder(new DistanceSensorEncoderCombo(this.encoder, distanceSensor));
    }

    public void updateDistance()
    {
        ((DistanceSensorEncoderCombo) encoder).updateDistance();
    }
}
