package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class CrankSlideSubSystem extends SubsystemBase {
    private final Servo CrankL;
    private final Servo CrankR;

    public CrankSlideSubSystem(HardwareMap hardwareMap, String name1, String name2, String name3) {
        CrankL = hardwareMap.get(Servo.class, name1);
        CrankR = hardwareMap.get(Servo.class, name2);
    }
    public void Extend() {
        CrankL.setPosition(+160/360);
        CrankR.setPosition(-160/360);
    }
    public void Return() {
        CrankL.setPosition(-160/360);
        CrankR.setPosition(+160/360);
    }
}
