package org.firstinspires.ftc.teamcode.Subsystems.controllers;

import androidx.annotation.Nullable;

import org.firstinspires.ftc.teamcode.Subsystems.Encoder;

import java.util.function.DoubleSupplier;
public abstract class Controller {
    Encoder encoder;
    public void setEncoder(Encoder encoder){
        this.encoder = encoder;
    }
    public abstract double calculate();
}
