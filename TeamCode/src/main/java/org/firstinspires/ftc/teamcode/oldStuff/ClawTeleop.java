package org.firstinspires.ftc.teamcode.oldStuff;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
@TeleOp(name = "ServoTest")
@Disabled
public class ClawTeleop extends LinearOpMode {
    private Servo claw;

    public void runOpMode() {
        claw = hardwareMap.get(Servo.class, "claw");
        if(gamepad1.y){
            claw.setPosition(0.6);
        }
        if(gamepad1.b){
            claw.setPosition(0.3);
        }


    }

}
