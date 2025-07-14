package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.ExtraMath;

/**
 * Class to keep all DcMotor actions that can be used for multiple different motors
 */
public class ViperMotorSub extends SubsystemBase
{
    private final Motor motor;
    private final DcMotorEx m;
    private final int MaxPos;
    private final int MinPos;
    private final double PosCoefficient;
    private int tgtPos;
    private int PosError = 0;//the amount that its set position differs from the real position
    double tolerance = 100;
    HardwareMap hardwareMap;

    public ViperMotorSub(HardwareMap hardwareMap, String MotorName, int minPos, int maxPos, double posCoefficient)
    {
        motor = new Motor(hardwareMap, MotorName);
        m = hardwareMap.get(DcMotorEx.class, MotorName);
        motor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        motor.resetEncoder();
        MaxPos = maxPos;
        MinPos = minPos;
        PosCoefficient = posCoefficient;
    }

    public void setTgPosRatio(double posRatio, double tolerance)
    {
        // set the run mode
        motor.setRunMode(Motor.RunMode.PositionControl);

// set and get the position coefficient
        motor.setPositionCoefficient(PosCoefficient);


        tgtPos = getPosFromRatio(MinPos, MaxPos, posRatio);// an integer representing
        // desired tick count
// set the target position
        motor.setTargetPosition(tgtPos);


        motor.set(0);

// set the tolerance
        motor.setPositionTolerance(tolerance);   // allowed maximum error

// perform the control loop

    }

    public void runToTgPos()
    {
        if (!TargetReached()) {
            motor.set(1);
        } else {
            motor.stopMotor();// stop the motor
        }
    }

    public void runToTgPosAndHoldIt(double holdPower)
    {
        if (!TargetReached()) {
            motor.set(1);
        } else {
            motor.set(holdPower);// keep the motor up
        }
    }

    public void stop()
    {
        motor.stopMotor();
    }

    int getPosFromRatio(int min, int max, double pos)
    {
        return min + (int) Math.round(pos * (max - min));
    }

    public boolean TargetReached()
    {
        return ExtraMath.ApproximatelyEqualTo(motor.getCurrentPosition(), tgtPos, tolerance);
    }

    public int getPos()
    {
        return motor.getCurrentPosition() + PosError;
    }

    public int getTargetPos()
    {
        return tgtPos;
    }

    public double getCurrent()
    {
        return m.getCurrent(CurrentUnit.AMPS);
    }

    public void setPosition(int position)
    {
        PosError = position - motor.getCurrentPosition();
    }

    public double getPower()
    {
        return motor.motor.getPower();
    }

    public double getSpeed()
    {
        return motor.getRate();
    }

    public void setEncoder(Motor encoder)
    {
        motor.encoder = encoder.encoder;
    }

    public Motor getMotor()
    {
        return motor;
    }

    public void ReverseMotor()
    {
        motor.motor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

}

