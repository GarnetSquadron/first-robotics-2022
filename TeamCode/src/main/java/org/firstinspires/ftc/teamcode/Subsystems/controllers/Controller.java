package org.firstinspires.ftc.teamcode.Subsystems.controllers;

import org.firstinspires.ftc.teamcode.Subsystems.Encoder;

import java.util.function.DoubleSupplier;

public abstract class Controller {
    DoubleSupplier position,velocity;
    double targetPosition;
    Controller(Encoder encoder){
        this.position = encoder::getPos;
        this.velocity = encoder::getVelocity;
    }
    public void setTargetPosition(double targetPosition){
        this.targetPosition = targetPosition;
    }
    public double getTargetPosition(){
        return targetPosition;
    }

    public abstract double calculate();
}
