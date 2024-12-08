package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.ServoSub;

public class ClawIntake {
    ServoSub Claw;
    public ClawIntake(HardwareMap hardwareMap){
        Claw = new ServoSub(hardwareMap,"intake claw",0,0.4);//TODO: tune this
    }
    public void close(){
        Claw.MoveToMin();
    }
    public void open(){
        Claw.MoveToMax();
    }

}
