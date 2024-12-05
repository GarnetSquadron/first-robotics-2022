package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Subsystems.ServoSub;
import org.firstinspires.ftc.teamcode.Subsystems.Wrist;

public class wristTest extends OpMode {
    Wrist s;

    @Override
    public void init() {
        s = new Wrist(hardwareMap);
    }

    @Override
    public void loop() {
        Wrist.runToRatio(gamepad1.left_stick_x);
    }
}
