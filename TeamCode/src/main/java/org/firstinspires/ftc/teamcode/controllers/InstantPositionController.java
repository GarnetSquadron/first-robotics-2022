package org.firstinspires.ftc.teamcode.controllers;

import org.firstinspires.ftc.teamcode.time.TIME;

import kotlin.jvm.functions.Function3;

public class InstantPositionController extends PositionController
{
    Function3<Double, Double, Double, Double> function;

    public InstantPositionController(Function3<Double, Double, Double, Double> function)
    {
        this.function = function;
    }

    @Override
    public double calculate()
    {
        return function.invoke(encoder.getPos() - targetPosition, encoder.getVelocity(), TIME.getTime());
    }
}
