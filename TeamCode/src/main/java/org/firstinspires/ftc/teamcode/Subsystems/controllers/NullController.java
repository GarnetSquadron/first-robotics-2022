package org.firstinspires.ftc.teamcode.Subsystems.controllers;

import org.firstinspires.ftc.teamcode.Subsystems.Encoder;

/**
 * calculate always returns 0
 */
public class NullController extends Controller{
    public NullController(){
        super(new Encoder(()->0));
    }
    @Override
    public double calculate() {
        return 0;
    }
}
