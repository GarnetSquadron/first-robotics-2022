package org.firstinspires.ftc.teamcode;

public class ExtraMath {

    /**
     * Tau is superior to pi. Fight me
     */
    public static double Tau = Math.PI*2;

    /**
     * if val is below min, it returns min. if val is above max, it returns max. otherwise, it just returns val.
     * @param val
     * @param max
     * @param min
     * @return
     */
    public static double Clamp(double val, double max, double min){
        return Math.min(Math.max(val,min),max);
    }
}
