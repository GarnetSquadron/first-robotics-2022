package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.Subsystems.ServoSub;

public class IntakeClawSub extends SubsystemBase {
    public final ServoSub SERVO;


    public IntakeClawSub(HardwareMap hardwareMap) {
        SERVO = new ServoSub(hardwareMap, "intake claw", 0.33, 0);
    }

    public void open() {
        SERVO.MoveToMax();
    }

    public void close() {
        SERVO.MoveToMin();
    }
}

