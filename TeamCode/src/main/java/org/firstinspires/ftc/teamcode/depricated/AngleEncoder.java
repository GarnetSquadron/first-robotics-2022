package org.firstinspires.ftc.teamcode.depricated;

import com.arcrobotics.ftclib.hardware.motors.Motor;

import org.firstinspires.ftc.teamcode.ExtraMath;
import org.firstinspires.ftc.teamcode.encoders.Encoder;
import org.firstinspires.ftc.teamcode.enums.AngleUnitV2;

import java.util.function.DoubleSupplier;

public class AngleEncoder extends Encoder
{
    /**
     * @param supplier a function that returns the radians
     */
    public AngleEncoder(DoubleSupplier supplier)
    {
        super(supplier);
    }

    public AngleEncoder(Motor motor)
    {
        super(motor);
    }

    /**
     * @param supplier         a function that returns the radians
     * @param velocitySupplier a function that returns the radians/second
     */
    public AngleEncoder(DoubleSupplier supplier, DoubleSupplier velocitySupplier)
    {
        super(supplier, velocitySupplier);
    }

    public double getPos(AngleUnitV2 unit)
    {
        return ExtraMath.ConvertAngleUnit(getPos(), AngleUnitV2.RADIANS, unit);
    }

    public double getVelocity(AngleUnitV2 unit)
    {
        return ExtraMath.ConvertAngleUnit(getPos(), AngleUnitV2.RADIANS, unit);
    }

    public void setPos(double pos, AngleUnitV2 unit)
    {
        setTicks(ExtraMath.ConvertAngleUnit(pos, AngleUnitV2.RADIANS, unit));
    }

}
