package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.Subsystems.ServoSub;

public class IntakeClawSub extends SubsystemBase {
    public final ServoSub SERVO;


    public IntakeClawSub(HardwareMap hardwareMap, String name1) {
        SERVO = new ServoSub(hardwareMap, name1, 0, 0.33);
    }

    public void Open() {
        SERVO.MoveToMax();
    }

    public void Close() {
        SERVO.MoveToMin();
    }
}

