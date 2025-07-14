package org.firstinspires.ftc.teamcode.controllers;

import static java.lang.Math.sin;

public class ArmOnAPivotController extends Controller
{
    double zeroAngle;
    double magnitude;

    public ArmOnAPivotController(double zeroAngle, double magnitude)
    {
        super();
        this.zeroAngle = zeroAngle;
        this.magnitude = magnitude;
    }

    @Override
    public double calculate()
    {
        return magnitude * sin(encoder.getPos() - zeroAngle);
    }
}
