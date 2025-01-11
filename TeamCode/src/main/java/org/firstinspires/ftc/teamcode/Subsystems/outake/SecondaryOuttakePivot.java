package org.firstinspires.ftc.teamcode.Subsystems.outake;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.ActionServo;
import org.firstinspires.ftc.teamcode.Subsystems.ServoSub;

import java.util.function.DoubleSupplier;

public class SecondaryOuttakePivot {
    public ActionServo pivot;
    public SecondaryOuttakePivot(HardwareMap hardwareMap, DoubleSupplier time) {
        pivot = new ActionServo(hardwareMap,"secondary pivot",0.1,1,0.5,time,270);
    }

    public Action BucketPos() {
        return pivot.runToRatio(1);
    }
    public Action outOfTheWayOfIntakePos(){
        return pivot.runToRatio(0.5);
    }

    public Action TransferPos() {
        return pivot.runToRatio(0.03);
    }
}
