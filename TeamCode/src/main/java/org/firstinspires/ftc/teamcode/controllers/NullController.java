package org.firstinspires.ftc.teamcode.controllers;

import org.firstinspires.ftc.teamcode.encoders.Encoder;

/**
 * calculate always returns 0
 */
public class NullController extends Controller
{
    public NullController()
    {
        super();
        setEncoder(new Encoder(() -> 0));
    }

    @Override
    public double calculate()
    {
        return 0;
    }
}
