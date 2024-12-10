package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.ServoSub;

public class IntakePivot extends SubsystemBase {
    ServoSub pivot;
    public IntakePivot(HardwareMap hardwaremap){
        pivot = new ServoSub(hardwaremap,"pivot",0,1);
    //hardwaremap.get(Servo.class, "pivot");

    }
    public void deploy(){
        pivot.MoveToMax();
    }
    public void undeploy(){
        pivot.MoveToMin();
    }
}
