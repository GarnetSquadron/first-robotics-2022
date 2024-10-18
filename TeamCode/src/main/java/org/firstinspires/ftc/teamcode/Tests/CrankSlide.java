package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "CrankSlide", group = "test")
public class CrankSlide extends LinearOpMode {
    Servo Crank;

    public void runOpMode(){
        Crank = hardwareMap.get(Servo.class, "crank");
        waitForStart();
        while (opModeIsActive()){
            if (gamepad1.x) {
                Crank.setPosition(0.5);
            }
            if (gamepad1.y) {
                Crank.setPosition(0);
            }
        }
    }
}
