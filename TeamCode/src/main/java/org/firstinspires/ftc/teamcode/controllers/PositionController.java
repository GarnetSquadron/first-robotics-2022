package org.firstinspires.ftc.teamcode.controllers;

import org.firstinspires.ftc.teamcode.ExtraMath;

public abstract class PositionController extends Controller
{
    double targetPosition, tolerance = 0;

    public void setTargetPosition(double targetPosition)
    {
        this.targetPosition = targetPosition;
    }

    public void setTolerance(double tolerance)
    {
        this.tolerance = tolerance;
    }

    public double getTolerance()
    {
        return tolerance;
    }

    public double getTargetPosition()
    {
        return targetPosition;
    }

    public double getDistanceToTarget()
    {
        return targetPosition - encoder.getPos();
    }

    public boolean targetReached()
    {
        return ExtraMath.ApproximatelyEqualTo(encoder.getPos(), targetPosition, tolerance);
    }

    public double calculateIfOutsideTolerance()
    {
        if (targetReached()) {
            return 0;
        } else {
            return calculate();
        }
    }
}
