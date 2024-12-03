package org.firstinspires.ftc.teamcode.Subsystems;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.arcrobotics.ftclib.command.SubsystemBase;

public class IntakeClawSub extends SubsystemBase {
    public final ServoSub left;
    public final ServoSub right;

    public IntakeClawSub(HardwareMap hardwareMap, String name1, String name2) {
        left = new ServoSub(hardwareMap, name1, 1, 0);
        right = new ServoSub(hardwareMap, name2, 0, 1);
    }

    public void Open() {
        left.MoveToMax();
        right.MoveToMax();
    }

    public void Close() {
        left.MoveToMin();
        right.MoveToMin();
    }
}
