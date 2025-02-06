package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.controller.PController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.ExtraMath;

public class BetterMotorClass {
    MotorEx motor;
    public BetterMotorClass(HardwareMap hardwareMap,String name){
        motor = new MotorEx(hardwareMap,name);
    }
}
