package org.firstinspires.ftc.teamcode.Tests;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


import org.firstinspires.ftc.teamcode.Subsystems.ServoAlignmentSub;


@TeleOp(name="James Servo Alignment", group = "test")

public class JamesServoAlignment extends OpMode {
     ServoAlignmentSub ServoAlignment;

     @Override
    public void loop() {
        if (gamepad1.y) {
            ServoAlignment.Up();
        }
        if (gamepad1.x) {
            ServoAlignment.Down();
        }
    }
    public void init(){
        ServoAlignment = new ServoAlignmentSub(hardwareMap,"AlignServo1", "AlignServo2");
    }
}
//commit