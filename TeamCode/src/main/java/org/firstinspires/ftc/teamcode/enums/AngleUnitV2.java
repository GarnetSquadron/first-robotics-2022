package org.firstinspires.ftc.teamcode.enums;

public enum AngleUnitV2
{
    DEGREES(360),
    RADIANS(2 * Math.PI),
    REVOLUTIONS(1);//number from 0 to 1
    double fullRotation;

    AngleUnitV2(double fullRotation)
    {
        this.fullRotation = fullRotation;
    }

    public double convertUnits(double input, AngleUnitV2 inputUnit)
    {
        return (input / inputUnit.fullRotation) * this.fullRotation;
    }
}
