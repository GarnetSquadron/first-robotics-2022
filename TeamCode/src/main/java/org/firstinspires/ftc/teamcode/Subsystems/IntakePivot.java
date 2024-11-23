package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class IntakePivot extends SubsystemBase {
    Servo pivot;
    public IntakePivot(HardwareMap hardwaremap){
        pivot = hardwaremap.get(Servo.class, "pivot");

    }
    public void deploy(){
        pivot.setPosition(0);
    }
    public void undeploy(){
        pivot.setPosition(0.8);
    }
}
