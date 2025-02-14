package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Hang {
    ActionDcMotor hangmotor;
    public Hang (HardwareMap hardwareMap){
        hangmotor = new ActionDcMotor(hardwareMap,"hangmotor",1,10,0.2,100);

    }
    public Action Hang(){
        hangmotor.GoToAngle()
    }
}
