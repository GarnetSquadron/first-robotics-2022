package org.firstinspires.ftc.teamcode;

public class ExtraMath {
    static {
        /**
         * Tau is superior to pi. Fight me
         */
        double Tau = Math.PI*2;

    }
    public static double Clamp(double val, double max, double min){
        return Math.min(Math.max(val,min),max);
    }
}
