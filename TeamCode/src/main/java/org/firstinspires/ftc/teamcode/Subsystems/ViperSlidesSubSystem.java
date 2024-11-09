package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.arcrobotics.ftclib.command.SubsystemBase;

public class ViperSlidesSubSystem extends SubsystemBase{
    private final DcMotor l;
    private final DcMotor r;
    public ViperSlidesSubSystem(HardwareMap hardwareMap, String name1, String name2, String name3){
        l = hardwareMap.get(DcMotor.class,name1);
        r = hardwareMap.get(DcMotor.class,name2);

    }
    public void Extend() {
        l.setPower(-0.25);
        r.setPower(+0.25);
    }
    public void Return() {
        l.setPower(+0.25);
        r.setPower(-0.25);
    }
}
