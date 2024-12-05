package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class ClawIntake {
    ServoSub Claw;
    public ClawIntake(HardwareMap hardwareMap){
        Claw = new ServoSub(hardwareMap,"intake",0,0.4);//TODO: tune this
    }
    public void close(){
        Claw.MoveToMin();
    }
    public void open(){
        Claw.MoveToMax();
    }
}
