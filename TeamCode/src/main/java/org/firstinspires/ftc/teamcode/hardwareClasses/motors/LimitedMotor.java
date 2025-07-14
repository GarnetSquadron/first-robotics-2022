package org.firstinspires.ftc.teamcode.hardwareClasses.motors;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.ExtraMath;

public class LimitedMotor extends ACTIONMOTOR
{
    double minPos, maxPos;

    public LimitedMotor(HardwareMap hardwareMap, String name, double minPos, double maxPos)
    {
        super(hardwareMap, name);
        this.minPos = minPos;
        this.maxPos = maxPos;
    }

    @Override
    public void setTargetPosition(double targetPosition)
    {
        super.setTargetPosition(ExtraMath.Clamp(targetPosition, maxPos, minPos));
    }
}
