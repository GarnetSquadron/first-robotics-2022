package org.firstinspires.ftc.teamcode.controllers;

import org.firstinspires.ftc.teamcode.ExtraMath;

/**
 * does not work well in most cases
 */
public class MaxSpeedController extends PositionController
{
    double accelerationConstant, maxAcceleration, velocityTolerance;

    public MaxSpeedController(double accelerationConstant, double velocityTolerance)
    {
        this.accelerationConstant = accelerationConstant;
        this.velocityTolerance = velocityTolerance;
    }

    public void setMaxAcceleration(double maxAcceleration)
    {
        this.maxAcceleration = maxAcceleration;
    }

    @Override
    public double calculate()
    {
        if (targetReached() && ExtraMath.ApproximatelyEqualTo(encoder.getVelocity(), 0, velocityTolerance)) {
            return 0;
        }
        if (Math.pow(encoder.getVelocity(), 2) >= 2 * maxAcceleration * accelerationConstant * getDistanceToTarget())
            return -Math.signum(encoder.getVelocity());
        else
            return Math.signum(encoder.getVelocity());
    }
}
