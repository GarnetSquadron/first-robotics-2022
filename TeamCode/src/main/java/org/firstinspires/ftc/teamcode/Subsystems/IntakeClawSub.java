package org.firstinspires.ftc.teamcode.Subsystems;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.arcrobotics.ftclib.command.SubsystemBase;

public class IntakeClawSub extends SubsystemBase {
    public final ServoSub SERVO;

    public IntakeClawSub(HardwareMap hardwareMap, String name1) {
        SERVO = new ServoSub(hardwareMap, name1, 1, 0);
    }

    public void Open() {
        SERVO.MoveToMax();
    }

    public void Close() {
        SERVO.MoveToMin();
    }
}
