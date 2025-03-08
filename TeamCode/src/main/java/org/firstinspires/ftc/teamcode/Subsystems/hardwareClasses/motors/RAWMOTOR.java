package org.firstinspires.ftc.teamcode.Subsystems.hardwareClasses.motors;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class RAWMOTOR {
    MotorEx motor;
    public RAWMOTOR(HardwareMap hardwareMap, String name){
        motor = new MotorEx(hardwareMap,name);
    }
    public void setPower(double power){
        motor.set(power);
    }
    public void stop(){
        motor.set(0);
    }

}
