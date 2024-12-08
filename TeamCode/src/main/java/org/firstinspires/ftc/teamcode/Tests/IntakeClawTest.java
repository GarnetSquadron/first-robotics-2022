package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeClawSub;

@TeleOp(name="IntakeClawTest", group = "test")
public class IntakeClawTest extends OpMode {
    IntakeClawSub ServoAlignment;
    @Override
    public void loop() {
        if (gamepad1.a) {
            ServoAlignment.open();
        }
        if (gamepad1.b) {
            ServoAlignment.close();
        }
    }

    public void init() {
        ServoAlignment = new IntakeClawSub(hardwareMap);
    }
}