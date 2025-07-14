package org.firstinspires.ftc.teamcode.encoders;

import static org.firstinspires.ftc.teamcode.ExtraMath.getAverageTimeDerivative;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;

import org.firstinspires.ftc.teamcode.ExtraMath;
import org.firstinspires.ftc.teamcode.ValueAtTimeStamp;
import org.firstinspires.ftc.teamcode.enums.AngleUnitV2;
import org.firstinspires.ftc.teamcode.time.TIME;

import java.util.function.DoubleSupplier;

public class Encoder
{
    ValueAtTimeStamp prevPos = new ValueAtTimeStamp(0, TIME.getTime());
    DoubleSupplier positionSupplier, velocitySupplier;
    double offset = 0, scale = 1;
    double CPR = 0;

    public Encoder(DoubleSupplier positionSupplier)
    {
        this.positionSupplier = positionSupplier;
        velocitySupplier = () -> getAverageTimeDerivative(prevPos, getCurrentPositionAndTime());
    }

    public Encoder(Encoder encoder)
    {
        this.positionSupplier = encoder.positionSupplier;
        this.velocitySupplier = encoder.velocitySupplier;
        this.scale = encoder.scale;
        this.offset = encoder.offset;
        this.prevPos = encoder.prevPos;
    }

    public Encoder(DoubleSupplier positionSupplier, DoubleSupplier velocitySupplier)
    {
        this.positionSupplier = positionSupplier;
        this.velocitySupplier = velocitySupplier;
    }

    public Encoder(Motor motor)
    {
        this(motor::getCurrentPosition, (motor.getClass() == MotorEx.class) ? (((MotorEx) motor)::getVelocity) : (motor::getRate));
        setCPR(motor);
        scaleToAngleUnit(AngleUnitV2.RADIANS);//Defaults to radians because radians are great
    }

    public double getPos()
    {
        return (positionSupplier.getAsDouble() + offset) * scale;
    }

    public void setTicks(double ticks)
    {
        offset = ticks - positionSupplier.getAsDouble();
    }

    public void setPosition(double position)
    {
        setTicks(position / scale);
    }

    public void setScale(double scale)
    {
        this.scale = scale;
    }

    public void scaleScaleBy(double scale)
    {
        this.scale *= scale;
    }

    public double getTicks()
    {
        return positionSupplier.getAsDouble();
    }

    public double getScale()
    {
        return scale;
    }

    public void scaleToAngleUnit(AngleUnitV2 unit)
    {
        scale = ExtraMath.ConvertAngleUnit(1 / (CPR), AngleUnitV2.REVOLUTIONS, unit);//it was off by a factor of 2 so I added the 2, idk why its like that
    }

    public void setCPR(double CPR)
    {
        this.CPR = CPR;
    }

    public void setCPR(Motor motor)
    {
        this.CPR = motor.getCPR();
    }

    public void setCPR(Motor.GoBILDA type)
    {
        this.CPR = type.getCPR();
    }

    public double getCPR()
    {
        return CPR;
    }

    /**
     * records the current position and time. must be run in a loop constantly in order to use
     * getVelocity()
     */
    public void updateVelocity()
    {
        prevPos = new ValueAtTimeStamp(getPos(), TIME.getTime());
    }

    public ValueAtTimeStamp getCurrentPositionAndTime()
    {
        return new ValueAtTimeStamp(getPos(), TIME.getTime());
    }

    public double getVelocity()
    {
        return velocitySupplier.getAsDouble() * scale;
    }

    public boolean isStopped()
    {
        return getVelocity() == 0;
    }
}
