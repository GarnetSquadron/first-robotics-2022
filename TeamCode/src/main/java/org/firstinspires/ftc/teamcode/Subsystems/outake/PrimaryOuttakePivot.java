package org.firstinspires.ftc.teamcode.Subsystems.outake;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.ServoSub;


public class PrimaryOuttakePivot extends SubsystemBase {
    public final ServoSub left;
    public final ServoSub right;
    public PrimaryOuttakePivot(HardwareMap hardwareMap) {
         left = new ServoSub(hardwareMap,"AlignServo1",0,1);
         right = new ServoSub(hardwareMap,"AlignServo2",1,0);
    }

    public void Up() {
        left.MoveToMax();
        right.MoveToMax();
    }

    public void Down() {
        left.MoveToMin();
        right.MoveToMin();
    }
}
//commit