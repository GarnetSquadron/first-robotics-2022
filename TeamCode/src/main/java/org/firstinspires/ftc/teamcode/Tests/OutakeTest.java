package org.firstinspires.ftc.teamcode.Tests;

import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.NonDriveHardware;

@TeleOp(name = "OutakeTest", group = "test")
public class OutakeTest extends LinearOpMode {


    @Override
    public void runOpMode(){
        NonDriveHardware.OutakeServo1 Servo1 = new NonDriveHardware.OutakeServo1(hardwareMap);
        NonDriveHardware.OutakeServo2 Servo2 = new NonDriveHardware.OutakeServo2(hardwareMap);
        Servo s1 = hardwareMap.get(Servo.class, "OutakeServo1");
        Servo s2 = hardwareMap.get(Servo.class, "OutakeServo2");
        waitForStart();
//        Actions.runBlocking(
//               new SequentialAction(
//                       new ParallelAction(
//                            Servo1.spin(1),
//                            Servo2.spin(1)
//                       )
//               )
//        );
        s1.setPower(1);
        s2.setPower(-1);
        while (opModeIsActive());
    }
}