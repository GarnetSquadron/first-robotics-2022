package org.firstinspires.ftc.teamcode.Tests;

import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "DoubleServoTest", group = "test")
public class DoubleServoTest extends LinearOpMode {
    ServoEx leftServo;
    ServoEx rightServo;

    public void runOpMode(){
        leftServo = new SimpleServo(hardwareMap,"leftServo", 0, 270);
        rightServo = new SimpleServo(hardwareMap,"rightServo", 0, 270);
        waitForStart();
        while (opModeIsActive()){
            if (gamepad1.x) {
                leftServo.setPosition(1);//up
            }

            if (gamepad1.a) {
                leftServo.setPosition(0.66666);
            }
            if (gamepad1.b) {
                leftServo.setPosition(0.33333);
            }
            if (gamepad1.y) {
                leftServo.setPosition(0);//down
            }
            if (gamepad1.left_stick_x!=0){
                leftServo.setPosition(0.5+gamepad1.left_stick_x*0.5);
            }

            if (gamepad1.dpad_left) {
                rightServo.setPosition(1);//up
            }

            if (gamepad1.dpad_down) {
                rightServo.setPosition(0.66666);
            }
            if (gamepad1.dpad_right) {
                rightServo.setPosition(0.33333);
            }
            if (gamepad1.dpad_up) {
                rightServo.setPosition(0);//down
            }
            if (gamepad1.right_stick_x!=0){
                rightServo.setPosition(0.5+gamepad1.left_stick_x*0.5);
            }
            if (gamepad1.left_bumper) {
                rightServo.setPosition(1);//down
                leftServo.setPosition(0);
            }
            if (gamepad1.right_bumper) {
                rightServo.setPosition(0);//down
                leftServo.setPosition(1);
            }

            telemetry.addData("leftPos", leftServo.getPosition());
            telemetry.addData("rightPos", rightServo.getPosition());
            telemetry.addData("leftAngle", leftServo.getAngle());
            telemetry.addData("rightAngle", rightServo.getAngle());
            telemetry.addData("rightAngle", rightServo.getVoltage());


            telemetry.update();
        }
    }
}
