package org.firstinspires.ftc.teamcode.Subsystems.controllers;

import static java.lang.Math.decrementExact;
import static java.lang.Math.sin;

import org.firstinspires.ftc.teamcode.AngleEncoder;

public class ArmOnAPivotController extends Controller{
    double zeroAngle;
    double magnitude;
    ArmOnAPivotController(AngleEncoder encoder,double zeroAngle,double magnitude) {
        super(encoder);
        this.zeroAngle = zeroAngle;
        this.magnitude = magnitude;
    }

    @Override
    public double calculate() {
        return magnitude*sin(position.getAsDouble()-zeroAngle);
    }
}
