package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeClawSub;

@TeleOp(name="IntakeClawTest", group = "test")
public class IntakeClawTest extends OpMode {
    IntakeClawSub ServoAlignment;
    @Override
    public void loop() {
        if (gamepad1.y) {
            ServoAlignment.Open();
        }
        if (gamepad1.x) {
            ServoAlignment.Close();
        }
    }

    public void init() {
        ServoAlignment = new IntakeClawSub(hardwareMap,"ClawServo");
    }
}