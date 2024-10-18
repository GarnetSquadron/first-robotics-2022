package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Vision;

@TeleOp(name = "AutoWristTest")
public class AutoWrist extends LinearOpMode {
    Servo wrist;
    Vision vision = new Vision(hardwareMap,telemetry);
    @Override
    public void runOpMode(){
        wrist = hardwareMap.get(Servo.class, "wrist");
        vision.InitPipeline();
        waitForStart();
        while(opModeIsActive()){
            wrist.setPosition((vision.GetNearestSample().getAngleRad())/(2*Math.PI));
        }
    }
}
