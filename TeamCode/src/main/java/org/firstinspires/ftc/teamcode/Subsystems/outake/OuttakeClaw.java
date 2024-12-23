package org.firstinspires.ftc.teamcode.Subsystems.outake;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.ActionServo;
import org.firstinspires.ftc.teamcode.Subsystems.ServoSub;

import java.util.function.DoubleSupplier;

public class OuttakeClaw {
    ActionServo claw;

    public OuttakeClaw(HardwareMap hardwareMap, DoubleSupplier time) {
        claw = new ActionServo(hardwareMap, "outtake claw", 0, 0.4, time,500);//TODO: tune this
    }

    public Action Close = claw.runToRatio(1);

    public Action Open = claw.runToRatio(0);
}
