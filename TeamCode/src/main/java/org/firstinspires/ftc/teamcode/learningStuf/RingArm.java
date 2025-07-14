package org.firstinspires.ftc.teamcode.learningStuf;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.enums.AngleUnitV2;
import org.firstinspires.ftc.teamcode.hardwareClasses.motors.LimitedMotor;

public class RingArm
{
    LimitedMotor arm;
    Servo wrist;

    public RingArm(HardwareMap hardwareMap)
    {
        arm = new LimitedMotor(hardwareMap, "armmotor", 0, 100);
        arm.getEncoder().setCPR(Motor.GoBILDA.RPM_117);
        arm.getEncoder().scaleToAngleUnit(AngleUnitV2.DEGREES);
        arm.setPID(0.02, 0.01, 0);
        wrist = hardwareMap.get(Servo.class, "wristservo");

    }

    public void setPosition(double angle)
    {
        arm.setTargetPosition(angle);
        wrist.setPosition((90 - angle) / 270);
    }

    public void update()
    {
        arm.runToTargetPosition();
    }

    public double getPosition()
    {
        return arm.getEncoder().getPos();
    }


}
