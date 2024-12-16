package org.firstinspires.ftc.teamcode.Subsystems.outake;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.ServoSub;

public class SecondaryOuttakePivot {
    public final ServoSub pivot;
    public SecondaryOuttakePivot(HardwareMap hardwareMap) {
        pivot = new ServoSub(hardwareMap,"secondary pivot",0.9,0.1);
    }

    public void BucketPos() {
        pivot.MoveToMax();
    }

    public void TransferPos() {
        pivot.MoveToMin();
    }
}
