package org.firstinspires.ftc.teamcode.Subsystems.outake;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.ServoSub;

public class OuttakeClaw {
    ServoSub claw;
    public OuttakeClaw(HardwareMap hardwareMap){
        claw = new ServoSub(hardwareMap,"outtake claw",0,0.4,500);//TODO: tune this
    }
    public void close(){
        claw.MoveToMin();
    }
    public void open(){
        claw.MoveToMax();
    }
}
