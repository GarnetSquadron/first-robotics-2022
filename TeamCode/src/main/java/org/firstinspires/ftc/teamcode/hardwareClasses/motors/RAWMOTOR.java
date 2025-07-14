package org.firstinspires.ftc.teamcode.hardwareClasses.motors;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.ExtraMath;
import org.firstinspires.ftc.teamcode.encoders.Encoder;

public class RAWMOTOR
{
    MotorEx motor;
    Encoder encoder;
    double maxPower = 1;

    public RAWMOTOR(HardwareMap hardwareMap, String name)
    {
        motor = new MotorEx(hardwareMap, name);
        setEncoderToMotorEncoder();
    }

    public void setPower(double power)
    {
        motor.set(ExtraMath.Clamp(power, maxPower, -maxPower));
    }

    public double getPower()
    {
        return motor.get();
    }

    public void setMaxPower(double maxPower)
    {
        this.maxPower = maxPower;
    }

    /**
     * Assumes that the sign of velocity is the same as the sign of the power, ie when the velocity
     * vector points counterclockwise, it should have the same sign as the sign of the force vector
     * when it points counterclockwise. This is how it should be ideally
     */
    public boolean isMovingInTheDirectionOfForce()
    {
        return Math.signum(encoder.getVelocity()) == Math.signum(getPower());
    }

    public void stop()
    {
        motor.set(0);
    }

    public Motor getFtcLibMotor()
    {
        return motor;
    }

    public void setEncoder(Encoder encoder)
    {
        this.encoder = encoder;
    }

    public void setEncoderToMotorEncoder()
    {
        this.encoder = new Encoder(motor);
    }

    public Encoder getEncoder()
    {
        return encoder;
    }

    public void setEncoderPosition(double position)
    {
        encoder.setTicks(position);
    }

    public void reverseMotor()
    {
        motor.setInverted(!motor.getInverted());
    }

}
