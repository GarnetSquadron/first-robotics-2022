package org.firstinspires.ftc.teamcode.learningStuf;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Subsystems.hardwareClasses.motors.LimitedMotor;
import org.firstinspires.ftc.teamcode.Subsystems.hardwareClasses.motors.RAWMOTOR;
import org.firstinspires.ftc.teamcode.enums.AngleUnitV2;

public class RingArm {
    LimitedMotor arm;
    Servo wrist;

    public RingArm (HardwareMap hardwareMap) {
        arm = new LimitedMotor(hardwareMap, "armmotor", 0, 100);
        arm.getEncoder().scaleToAngleUnit(AngleUnitV2.DEGREES);
        wrist = hardwareMap.get(Servo.class, "wristservo");

    }
    public void setPosition(double angle){
        arm.runToPosition(angle);
        wrist.setPosition(angle/270);
    }


}
