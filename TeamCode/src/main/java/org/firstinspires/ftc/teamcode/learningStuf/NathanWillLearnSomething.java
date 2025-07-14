package org.firstinspires.ftc.teamcode.learningStuf;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardwareClasses.motors.RAWMOTOR;

public class NathanWillLearnSomething
{
    RAWMOTOR leftwheel;
    RAWMOTOR rightwheel;

    public NathanWillLearnSomething(HardwareMap hardwareMap)
    {
        leftwheel = new RAWMOTOR(hardwareMap, "viperleft");
        rightwheel = new RAWMOTOR(hardwareMap, "viperright");
    }

    public void setpower(double power, double curve)
    {
        leftwheel.setPower(power - curve);
        rightwheel.setPower(-power - curve);
    }
}
