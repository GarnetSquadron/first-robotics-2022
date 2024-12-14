package org.firstinspires.ftc.teamcode.Subsystems.outake;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.ServoSub;


public class PrimaryOuttakePivot extends SubsystemBase {
    public final ServoSub pivot;

    public PrimaryOuttakePivot(HardwareMap hardwareMap) {
         pivot = new ServoSub(hardwareMap,"primary pivot",1,0);
    }

    public void Up() {
        pivot.MoveToMax();
    }

    public void Down() {
        pivot.MoveToMin();
    }
}
//commit