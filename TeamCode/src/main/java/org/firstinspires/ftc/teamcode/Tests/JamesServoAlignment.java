package org.firstinspires.ftc.teamcode.Tests;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


import org.firstinspires.ftc.teamcode.Subsystems.ServoAlignmentSub;


@TeleOp(name="James Servo Alignment", group = "test")

public class JamesServoAlignment extends LinearOpMode{
     ServoAlignmentSub ServoAlignment = new ServoAlignmentSub(hardwareMap,"AlignServo1", "AlignServo2");

    public void Onstart(){
        ServoAlignment.Up();
        sleep(5000);
        ServoAlignment.Down();
    }
    public void runOpMode(){
        waitForStart();
        Onstart();
    }
}
//commit