package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.enums.AngleUnitV2;

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
    public static boolean withinRange(double val, double max, double min){
        return val<=max&&val>=min;
    }
    public static boolean ApproximatelyEqualTo(double val, double expectedVal, double tolerance){
        return withinRange(val,expectedVal+tolerance,expectedVal-tolerance);
    }
    public static double convertFromRatio(double min, double max,double ratio){
        return min+ratio*(max-min);
    }
    public static double convertToRatio(double min, double max,double val){
        return (val-min)/(max-min);
    }
    public static double getFullCircle(AngleUnitV2 unit){
        if(unit == AngleUnitV2.DEGREES){
            return 360;
        }
        if(unit == AngleUnitV2.RADIANS){
            return Tau;
        }
        if(unit == AngleUnitV2.REVOLUTIONS){
            return 1;
        }
        else {
            return -1;//this is basically impossible
        }
    }
    public static double ConvertUnit(double input, AngleUnitV2 inputUnit, AngleUnitV2 outputUnit){
        return input*getFullCircle(outputUnit)/getFullCircle(inputUnit);
    }
}
