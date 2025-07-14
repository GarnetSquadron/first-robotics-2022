package org.firstinspires.ftc.teamcode.hardwareClasses;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.ExtraMath;
import org.firstinspires.ftc.teamcode.enums.AngleUnitV2;

import java.util.function.DoubleSupplier;

/**
 * Servo that has a bunch of actions
 */
public class ActionServo
{
    public SERVO servo;
    public double softwareAngleRangeInDegrees;//the range of motion in degrees that the software limits allow
    public double zeroAngle = 0;//the ratio pos for 0 degrees
    public double min, max;

    /**
     * Servo that has a bunch of actions
     *
     * @param hardwareMap
     * @param name               name of the device
     * @param min                minimum position (a number from 0 to 1)
     * @param max                maximum position (a number from 0 to 1)
     * @param time               the system time supplier
     * @param t
     * @param hardwareAngleRange
     */
    public ActionServo(HardwareMap hardwareMap, String name, double min, double max, double t, DoubleSupplier time, double hardwareAngleRange, AngleUnitV2 unit)
    {
        servo = new SERVO(hardwareMap, name, min, max, time, t);
        this.min = min;
        this.max = max;
        SetHardwareAngleRange(hardwareAngleRange, unit);
    }

    public ActionServo(HardwareMap hardwareMap, String name, double min, double max, DoubleSupplier time)
    {
        this(hardwareMap, name, min, max, 0.5, time);
    }

    public ActionServo(HardwareMap hardwareMap, String name, double min, double max, double t, DoubleSupplier time)
    {
        this(hardwareMap, name, min, max, t, time, 255, AngleUnitV2.DEGREES);
    }

    public ActionServo(HardwareMap hardwareMap, String name, double min, double max, DoubleSupplier time, double hardwareAngleRange, AngleUnitV2 unit)
    {
        this(hardwareMap, name, min, max, 0.5, time, hardwareAngleRange, unit);
    }

    public void SetSoftwareAngleRange(double range, AngleUnitV2 unit)
    {
        softwareAngleRangeInDegrees = ExtraMath.ConvertAngleUnit(range, unit, AngleUnitV2.DEGREES);
    }

    public void SetHardwareAngleRange(double range, AngleUnitV2 unit)
    {
        softwareAngleRangeInDegrees = ExtraMath.ConvertAngleUnit(range, unit, AngleUnitV2.DEGREES) * Math.abs(max - min);
    }

    public void Set0Angle(double ratio)
    {
        zeroAngle = ratio;
    }

    public Action runToDegrees(double angle)
    {
        return runToRatio(zeroAngle + angle / softwareAngleRangeInDegrees);
    }

    public Action runToRad(double angle)
    {
        return runToDegrees(Math.toDegrees(angle));
    }

    public double getPosInDegrees()
    {
        return getRatioPos() * softwareAngleRangeInDegrees;
    }

    public double getPosInRad()
    {
        return Math.toRadians(getPosInDegrees());
    }

    public double getPos()
    {
        return servo.getPos();
    }

    public double getRatioPos()
    {
        return servo.getRatioPos();
    }

    public class MoveServoToRatio implements Action
    {
        double ratio;

        MoveServoToRatio(double ratio)
        {
            this.ratio = ratio;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket)
        {
            servo.goToRatio(ratio);
            return false;
        }
    }

    public class WaitForTargetReached implements Action
    {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket)
        {
            return !servo.targetReached();
        }
    }

    public Action runToRatio(double ratio)
    {
        return new SequentialAction(new MoveServoToRatio(ratio), new WaitForTargetReached());
    }

    public Action changePosBy(double delta)
    {
        return runToRatio(getRatioPos() - delta);
    }

    public Action changeAngleByRad(double deltaTheta)
    {
        return runToRad(getPosInRad() - deltaTheta);
    }

    public boolean AtMax()
    {
        return servo.atMax();
    }

}
