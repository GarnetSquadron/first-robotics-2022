package org.firstinspires.ftc.teamcode.Subsystems.outake;
import com.acmerobotics.roadrunner.Action;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.ActionServo;
import org.firstinspires.ftc.teamcode.Subsystems.ServoSub;

import java.util.function.DoubleSupplier;


public class PrimaryOuttakePivot{
    public ActionServo pivot;

    public PrimaryOuttakePivot(HardwareMap hardwareMap, DoubleSupplier time) {
         pivot = new ActionServo(hardwareMap,"primary pivot",0.95,0.3333333,time);
    }

    public Action BucketPos() {
        return pivot.runToRatio(1);
    }

    public Action TransferPos() {
        return pivot.runToRatio(0);
    }
}
//commit