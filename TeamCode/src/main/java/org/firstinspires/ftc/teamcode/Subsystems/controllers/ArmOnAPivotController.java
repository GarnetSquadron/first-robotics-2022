package org.firstinspires.ftc.teamcode.Subsystems.controllers;

import static java.lang.Math.decrementExact;
import static java.lang.Math.sin;

import org.firstinspires.ftc.teamcode.AngleEncoder;
import org.firstinspires.ftc.teamcode.Subsystems.Encoder;

public class ArmOnAPivotController extends Controller{
    double zeroAngle;
    double magnitude;
    public ArmOnAPivotController(double zeroAngle, double magnitude) {
        super();
        this.zeroAngle = zeroAngle;
        this.magnitude = magnitude;
    }
    @Override
    public double calculate() {
        return magnitude*sin(encoder.getPos()-zeroAngle);
    }
}
