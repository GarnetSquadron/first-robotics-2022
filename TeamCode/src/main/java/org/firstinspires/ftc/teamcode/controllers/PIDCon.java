package org.firstinspires.ftc.teamcode.controllers;

import org.firstinspires.ftc.teamcode.ExtraMath;
import org.firstinspires.ftc.teamcode.ValueAtTimeStamp;
import org.firstinspires.ftc.teamcode.time.TIME;

public class PIDCon extends PositionController
{
    double kp, ki, kd;
    ValueAtTimeStamp prevPos;
    double integral;

    public PIDCon(double kp, double ki, double kd)
    {
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
        reset();
    }

    public void reset()
    {
        integral = 0;
        prevPos = new ValueAtTimeStamp(0, TIME.getTime());
    }

    @Override
    public void setTargetPosition(double targetPosition)
    {
        this.targetPosition = targetPosition;
        reset();
    }

    @Override
    public double calculate()
    {
        double error = getDistanceToTarget();
        if (ki != 0) {
            double currentTime = TIME.getTime();
            integral += ExtraMath.integration.trapazoid(prevPos, new ValueAtTimeStamp(error, currentTime));
            prevPos = new ValueAtTimeStamp(error, currentTime);
        }
        return kp * error + ki * integral + kd * encoder.getVelocity();
    }
}
