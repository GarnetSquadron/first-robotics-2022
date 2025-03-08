package org.firstinspires.ftc.teamcode.Subsystems.controllers;

import org.firstinspires.ftc.teamcode.Subsystems.Encoder;
import org.firstinspires.ftc.teamcode.TIME;

import java.util.function.DoubleSupplier;

import kotlin.jvm.functions.Function3;

public class InstantController extends Controller{
    Function3<Double,Double,Double,Double> function;
    public InstantController(Function3<Double,Double,Double,Double> function, Encoder encoder){
        super(encoder);
        this.function = function;
    }
    @Override
    public double calculate() {
        return function.invoke(position.getAsDouble()-targetPosition,velocity.getAsDouble(), TIME.getTime());
    }
}
