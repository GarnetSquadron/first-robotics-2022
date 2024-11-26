package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class IntakePivot extends SubsystemBase {
    ServoSub pivot;
    public IntakePivot(HardwareMap hardwaremap){
        pivot = new ServoSub(hardwaremap,"pivot",0.8,0);
    //hardwaremap.get(Servo.class, "pivot");

    }
    public void deploy(){
        pivot.MoveToMax();
    }
    public void undeploy(){
        pivot.MoveToMin();
    }
}
