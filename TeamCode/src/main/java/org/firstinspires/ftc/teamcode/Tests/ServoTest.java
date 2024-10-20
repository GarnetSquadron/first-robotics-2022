package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "ServoTest", group = "test")
public class ServoTest extends LinearOpMode {
    Servo servo;

    public void runOpMode(){
        servo = hardwareMap.get(Servo.class, "crank");
        waitForStart();
        while (opModeIsActive()){
            if (gamepad1.x) {
                servo.setPosition(1);
            }
            if (gamepad1.y) {
                servo.setPosition(0);
            }
        }
    }
}
