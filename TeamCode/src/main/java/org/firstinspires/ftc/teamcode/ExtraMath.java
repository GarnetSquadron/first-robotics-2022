package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.enums.AngleUnitV2;

public class ExtraMath
{

    /**
     * Tau is superior to pi. Fight me
     */
    public static double Tau = Math.PI * 2;

    /**
     * if val is below min, it returns min. if val is above max, it returns max. otherwise, it just
     * returns val.
     *
     * @param val
     * @param max
     * @param min
     * @return
     */
    public static double Clamp(double val, double max, double min)
    {
        return Math.min(Math.max(val, min), max);
    }

    public static boolean withinRange(double val, double max, double min)
    {
        return val <= max && val >= min;
    }

    public static boolean ApproximatelyEqualTo(double val, double expectedVal, double tolerance)
    {
        return withinRange(val, expectedVal + tolerance, expectedVal - tolerance);
    }

    /**
     * returns input with minimum magnitude
     *
     * @return
     */
    public static double minMag(double num1, double num2)
    {
        if (Math.abs(num1) < Math.abs(num2)) {
            return num1;
        } else return num2;
    }

    public static double maxMag(double num1, double num2)
    {
        if (Math.abs(num1) > Math.abs(num2)) {
            return num1;
        } else return num2;
    }

    public static double convertFromRatio(double min, double max, double ratio)
    {
        return min + ratio * (max - min);
    }

    public static double convertToRatio(double min, double max, double val)
    {
        return (val - min) / (max - min);
    }

    public static double getFullCircle(AngleUnitV2 unit)
    {
        if (unit == AngleUnitV2.DEGREES) {
            return 360;
        }
        if (unit == AngleUnitV2.RADIANS) {
            return Tau;
        }
        if (unit == AngleUnitV2.REVOLUTIONS) {
            return 1;
        } else {
            return -1;//this is basically impossible
        }
    }

    public static double ConvertAngleUnit(double input, AngleUnitV2 inputUnit, AngleUnitV2 outputUnit)
    {
        return input * getFullCircle(outputUnit) / getFullCircle(inputUnit);
    }

    public static class integration
    {
        /**
         * https://en.wikipedia.org/wiki/Simpson%27s_rule
         *
         * @param i            initial value
         * @param intermediate value exactly inbetween i and f
         * @param f            final value
         * @return
         */
        public static double SimpsonsRule(double deltaT, double i, double intermediate, double f)
        {
            return deltaT * (
                    i + 4 * intermediate + f
            ) / 6;
        }

        public static double trapazoid(double deltaT, double i, double f)
        {
            return deltaT * (i + f) / 2;
        }

        public static double trapazoid(ValueAtTimeStamp val1, ValueAtTimeStamp val2)
        {
            return trapazoid(val2.getTimeStamp() - val1.getTimeStamp(), val1.getValue(), val2.getValue());
        }
    }

    public static double getAverageTimeDerivative(ValueAtTimeStamp val1, ValueAtTimeStamp val2)
    {
        return (val1.getValue() - val2.getValue()) / (val1.getTimeStamp() - val2.getTimeStamp());
    }
}
