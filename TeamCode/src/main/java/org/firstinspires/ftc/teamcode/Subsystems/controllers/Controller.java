package org.firstinspires.ftc.teamcode.Subsystems.controllers;

import org.firstinspires.ftc.teamcode.Subsystems.Encoder;

import java.util.function.DoubleSupplier;

public abstract class Controller {
    DoubleSupplier position,velocity;
    Controller(Encoder encoder){
        this.position = encoder::getPos;
        this.velocity = encoder::getVelocity;
    }
    public abstract double calculate();
}
