package org.firstinspires.ftc.teamcode.Subsystems;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Pipelines.SamplePipeline;
public class ViperSlidesSubSystem extends SubsystemBase{
    private final DcMotor l;
    private final DcMotor r;
    public ViperSlidesSubSystem(HardwareMap hardwareMap, String name1, String name2){
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
    public void Stop() {
        l.setPower(0);
        r.setPower(0);
    }

}
