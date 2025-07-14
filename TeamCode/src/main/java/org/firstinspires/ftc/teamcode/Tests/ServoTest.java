package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "ServoTest", group = "test")
@Disabled
public class ServoTest extends LinearOpMode
{
    Servo servo;

    public void runOpMode()
    {
        servo = hardwareMap.get(Servo.class, "servo");
        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.x) {
                servo.setPosition(1);//up
            }

            if (gamepad1.a) {
                servo.setPosition(0.66666);
            }
            if (gamepad1.b) {
                servo.setPosition(0.33333);
            }
            if (gamepad1.y) {
                servo.setPosition(0);//down
            }
            if (gamepad1.left_stick_x != 0) {
                servo.setPosition(0.5 + gamepad1.left_stick_x * 0.5);
            }
            telemetry.addData("position", servo.getPosition());
            telemetry.update();
        }
    }
}
