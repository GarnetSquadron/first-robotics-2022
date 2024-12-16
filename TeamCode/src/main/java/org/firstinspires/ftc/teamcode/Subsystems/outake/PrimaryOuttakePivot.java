package org.firstinspires.ftc.teamcode.Subsystems.outake;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.ServoSub;


public class PrimaryOuttakePivot extends SubsystemBase {
    public final ServoSub pivot;

    public PrimaryOuttakePivot(HardwareMap hardwareMap) {
         pivot = new ServoSub(hardwareMap,"primary pivot",0.95,0.3333333);
    }

    public void BucketPos() {
        pivot.MoveToMax();
    }

    public void TransferPos() {
        pivot.MoveToMin();
    }
}
//commit