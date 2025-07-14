package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name = "#JamesMotorTest")
@Disabled
public class MotorTest extends OpMode
{
    DcMotor motor;

    @Override
    public void init()
    {
        motor = hardwareMap.get(DcMotor.class, "testMotor");
    }

    @Override
    public void loop()
    {
        motor.setPower(0);

        if (gamepad1.a) {
            int startPos = motor.getCurrentPosition();

            int targetPos = startPos + (int) (1425.1 / 4);

            motor.setTargetPosition(targetPos);
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motor.setPower(1);

            if (motor.isBusy()) {
                motor.setPower(1);
            } else {
                motor.setPower(0);
            }
        }

        if (gamepad1.b) {
            motor.setPower(0);
        }
    }
}