package org.firstinspires.ftc.teamcode.Subsystems.controllers;

import org.firstinspires.ftc.teamcode.Subsystems.Encoder;
import org.firstinspires.ftc.teamcode.TIME;

import kotlin.jvm.functions.Function3;

public class InstantPositionController extends PositionController{
    Function3<Double,Double,Double,Double> function;
    public InstantPositionController(Function3<Double,Double,Double,Double> function, Encoder encoder){
        super(encoder);
        this.function = function;
    }
    @Override
    public double calculate() {
        return function.invoke(position.getAsDouble()-targetPosition,velocity.getAsDouble(), TIME.getTime());
    }
}
