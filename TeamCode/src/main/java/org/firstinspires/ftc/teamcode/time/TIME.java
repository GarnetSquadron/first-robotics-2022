package org.firstinspires.ftc.teamcode.time;

public class TIME
{
    public static double getTime()
    {
        return (double) System.nanoTime() / 1.0E9;
    }
}
