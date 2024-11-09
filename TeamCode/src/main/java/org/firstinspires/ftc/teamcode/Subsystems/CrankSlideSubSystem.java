package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class CrankSlideSubSystem extends SubsystemBase {
    private final Servo CrankL;
    private final Servo CrankR;

    public CrankSlideSubSystem(HardwareMap hardwareMap, String name1, String name2) {
        CrankL = hardwareMap.get(Servo.class, name1);
        CrankR = hardwareMap.get(Servo.class, name2);
        CrankR.setDirection(Servo.Direction.REVERSE);
    }
    public void goToPos(double pos){
        CrankL.setPosition(2*pos/3.0);
        CrankR.setPosition(2*pos/3.0);
    }
    public void Extend() {
        goToPos(0);
    }
    public void Return() {
        goToPos(1);
    }
}
