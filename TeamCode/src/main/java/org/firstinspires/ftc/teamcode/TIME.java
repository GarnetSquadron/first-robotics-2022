package org.firstinspires.ftc.teamcode;

import java.util.function.DoubleSupplier;

public class TIME {
    public static double getTime(){
        return (double)System.nanoTime() / 1.0E9;
    }
}
