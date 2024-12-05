package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Subsystems.ServoSub;

@TeleOp(name = "ServoTest", group = "test")
public class ServoTest extends LinearOpMode {
    Servo servo;
    ServoSub s;

    public void runOpMode(){
        servo = hardwareMap.get(Servo.class, "servo");
        s = new ServoSub(hardwareMap, "servo", 0,1);
        waitForStart();
        while (opModeIsActive()){
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
        }
    }
}
