package org.firstinspires.ftc.teamcode.learningStuf;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.hardwareClasses.motors.RAWMOTOR;

public class WeddingVipers {
    RAWMOTOR left;
    RAWMOTOR right;
    int UpperLimit=100;
    int LowerLimit=0;
    public WeddingVipers(HardwareMap hardwareMap) {
        left = new RAWMOTOR(hardwareMap, "viperleft");
        right = new RAWMOTOR(hardwareMap, "viperright");
    }
    public void setpower(double power){
        if(inRange()) {
            left.setPower(power);
            right.setPower(-power);
        }
        else{
            left.setPower(0);
            right.setPower(0);
        }


    }
    public boolean inRange(){
        return left.getEncoder().getPos() > LowerLimit && left.getEncoder().getPos() < UpperLimit;
    }

}
