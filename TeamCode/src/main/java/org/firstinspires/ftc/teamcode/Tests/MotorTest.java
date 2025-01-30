package org.firstinspires.ftc.teamcode.Tests;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
@TeleOp(name = "#JamesMotorTest")
public class MotorTest extends OpMode {
    DcMotor motor;

    @Override
    public void init() {
        motor = hardwareMap.get(DcMotor.class, "testMotor");
    }
//1425.1 ticks per rotation
    @Override
    public void loop() {
        motor.setPower(0);
        if (gamepad1.a) {
            double startPos = motor.getCurrentPosition();
            while(motor.getCurrentPosition() > (startPos + (1425.1/4))){
                motor.setPower(1);
            }
        }
        if (gamepad1.b) {
            motor.setPower(0);
        }
    }
}
