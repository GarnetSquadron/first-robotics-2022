package org.firstinspires.ftc.teamcode.depricated.failedMotorClasses;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.ExtraMath;
import org.firstinspires.ftc.teamcode.enums.AngleUnitV2;

import java.util.function.Function;

import kotlin.jvm.functions.Function2;

/**
 * Class to keep all DcMotor actions that can be used for multiple different motors
 */
public class DcMotorSub extends SubsystemBase
{
    private final Motor motor;
    private final DcMotorEx m;
    private final int MaxPos;
    private final int MinPos;
    private final double PosCoefficient;
    private double newPosCoefficient;
    private final double velCoefficient;
    private int tgtPos;
    double zeroDegreesTick = 0;
    PController controller;
    private double power;
    /**
     * takes the angle in radians and returns the external force at that point
     */
    Function<Double, Double> extTorqueFunction = x -> 0.0;
    Function2<Integer, Double, Double> desiredNetTorqueFunction;
    Function<Integer, Double> newDesiredNetTorqueFunction;
    boolean externalForceAccountingMode = false;
    /**
     * actual encoder position-position that we want to call it
     */
    private int PosError = 0;//the amount that its set position differs from the real position
    double tolerance = 10;
    double ticksInFullCircle = 1425.1;

    public DcMotorSub(HardwareMap hardwareMap, String MotorName, int minPos, int maxPos, double posCoefficient, double velCoefficient, double maxPower, double tolerance)
    {
        motor = new Motor(hardwareMap, MotorName);
        m = hardwareMap.get(DcMotorEx.class, MotorName);
        motor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        motor.resetEncoder();
        MaxPos = maxPos;
        MinPos = minPos;
        PosCoefficient = posCoefficient;
        newPosCoefficient = posCoefficient;
        this.velCoefficient = velCoefficient;
        this.tolerance = tolerance;
        //controller = new PController(posCoefficient);
        //desiredNetTorqueFunction = (x,v)->PosCoefficient*x+velCoefficient*v;
        setPD(PosCoefficient, velCoefficient, maxPower);
        //newDesiredNetTorqueFunction = x->newPosCoefficient*x;
    }

    public DcMotorSub(HardwareMap hardwareMap, String MotorName, int minPos, int maxPos, double posCoefficient, double tolerance)
    {
        this(hardwareMap, MotorName, minPos, maxPos, posCoefficient, 0, 1, tolerance);
    }

    public void setNewPosCoefficient(double c)
    {
        newPosCoefficient = c;
        newDesiredNetTorqueFunction = x -> newPosCoefficient * x;
    }

    public void setPD(double p, double d)
    {
        setPD(p, d, 1);
    }

    public void setPD(double p, double d, double maxPower)
    {
        desiredNetTorqueFunction = (x, v) -> ExtraMath.Clamp(p * x + d * v, maxPower, -maxPower);
    }

    //region setTgPos
    public void setTgPosTick(int pos, double tolerance)
    {
        ExtraMath.Clamp(pos, MaxPos, MinPos);//prevent the motor from moving outside of its range, to avoid accidently breaking stuff
        // set the run mode
        motor.setRunMode(Motor.RunMode.PositionControl);
        externalForceAccountingMode = false;//my code is scuffed rn and I apologize
// set and get the position coefficient
        motor.setPositionCoefficient(PosCoefficient);


        tgtPos = pos;// an integer representing
        // desired tick count
// set the target position
        motor.setTargetPosition(tgtPos + PosError);


        motor.set(0);

// set the tolerance

        motor.setPositionTolerance(tolerance);   // allowed maximum error
    }

    public void setTgPosTick(int pos)
    {
        setTgPosTick(pos, tolerance);
    }

    public void setTgPosRatio(double posRatio, double tolerance)
    {
        setTgPosTick(getPosFromRatio(MinPos, MaxPos, posRatio), tolerance);
    }

    public void setTgPosRatio(double posRatio)
    {
        setTgPosRatio(posRatio, tolerance);
    }

    public void setTgPosAngle(double angle, AngleUnitV2 unit, double tolerance)
    {
        setTgPosTick(getTicksFromAngle(angle, unit), tolerance);
    }

    public void setTgPosAngle(double angle, AngleUnitV2 unit)
    {
        setTgPosAngle(angle, unit, tolerance);
    }

    //endregion setTgPos
    public void setPower(double power)
    {
        this.power = power;
    }

    /**
     * subtracts the external torques from the
     */
    public void setNetTorque(double power)
    {
        motor.set(power - extTorqueFunction.apply(getPosInAngle(AngleUnitV2.RADIANS)));
    }

    public void setExtTorqueFunction(Function<Double, Double> function)
    {
        extTorqueFunction = function;
    }

    public void setDesiredNetTorqueFunction(Function2<Integer, Double, Double> function)
    {
        desiredNetTorqueFunction = function;
    }

    public void AccountForExtForces()
    {
        externalForceAccountingMode = true;
        power = 1;
    }

    public void StopAccountingForExtForces()
    {
        externalForceAccountingMode = false;
    }

    public void updatePower()
    {
        if (externalForceAccountingMode) {
            if (power == 0) {
                motor.set(0);
            } else {
                motor.setRunMode(Motor.RunMode.RawPower);
                int deltaPos = getTargetPos() - getPos();
                double velocity = motor.getRate();
                double toler = 40;
                setNetTorque(desiredNetTorqueFunction.invoke(deltaPos, motor.getRate()));
            }
        } else {
            motor.set(power);
        }
    }

    //region runToTgPos
    public void runToTgPos()
    {
        if (!TargetReached()) {
            setPower(1);
        } else {
            stop();// stop the motor
        }
    }

    /**
     * runs at max vel because speeeeeeeeeeeeeeed
     *
     * @param holdPower
     */
    public void runToTgPosAndHoldIt(double holdPower)
    {
        runToTgPosAndHoldIt(holdPower, 1);
    }

    /**
     * runs at whatever power you want because mechanical doesn't like fun things.
     *
     * @param holdPower
     */
    public void runToTgPosAndHoldIt(double holdPower, double runningPower)
    {
        StopAccountingForExtForces();
        if (!TargetReached()) {
            setPower(runningPower);
        } else {
            setPower(holdPower);// keep the motor up
        }
    }

    //endregion
    public void JustKeepRunning(double power)
    {
        motor.setRunMode(Motor.RunMode.RawPower);
        externalForceAccountingMode = false;
        setPower(power);
    }

    public void stop()
    {
        power = 0;
        motor.stopMotor();
    }

    int getPosFromRatio(int min, int max, double pos)
    {
        return min + (int) Math.round(pos * (max - min));
    }

    public boolean TargetReached()
    {
        return ExtraMath.ApproximatelyEqualTo(getPos(), tgtPos, tolerance);
    }

    public int getPos()
    {
        return motor.getCurrentPosition() - PosError;
    }

    public double getRatioPos()
    {
        return ExtraMath.convertToRatio(getPos(), MinPos, MaxPos);
    }

    public double getPosInAngle(AngleUnitV2 unit)
    {
        return getAngleFromTicks(getPos(), unit);
    }

    public int getTargetPos()
    {
        return tgtPos;
    }

    public double getTargetAngle(AngleUnitV2 unit)
    {
        return getAngleFromTicks(tgtPos, unit);
    }

    public double getCurrent()
    {
        return m.getCurrent(CurrentUnit.AMPS);
    }

    public void setPosition(int position)
    {
        PosError = motor.getCurrentPosition() - position;
    }

    public void setAngle(double angle, AngleUnitV2 unit)
    {
        setPosition(getTicksFromAngle(angle, unit));
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

    public double getError()
    {
        return PosError;
    }

    public void setZeroDegrees(int ticks)
    {
        zeroDegreesTick = ticks;
    }

    public double getAngleFromTicks(int ticks, AngleUnitV2 unit)
    {
        return ExtraMath.getFullCircle(unit) * (ticks + zeroDegreesTick) / ticksInFullCircle;
    }

    public int getTicksFromAngle(double angle, AngleUnitV2 unit)
    {
        return (int) Math.round(angle * ticksInFullCircle / ExtraMath.getFullCircle(unit) - zeroDegreesTick);
    }

    public boolean inExtForceMode()
    {
        return externalForceAccountingMode;
    }
}

