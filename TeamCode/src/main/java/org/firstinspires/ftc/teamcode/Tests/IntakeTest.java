package org.firstinspires.ftc.teamcode.Tests;

import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.NonDriveHardware;
@TeleOp(name = "IntakeTest", group = "test")
public class IntakeTest extends LinearOpMode {


    @Override
    public void runOpMode(){
        NonDriveHardware.IntakeServo1 Servo1 = new NonDriveHardware.IntakeServo1(hardwareMap);
        NonDriveHardware.IntakeServo2 Servo2 = new NonDriveHardware.IntakeServo2(hardwareMap);
        waitForStart();
        Actions.runBlocking(
               new SequentialAction(
                       new ParallelAction(
                            Servo1.spin(1),
                            Servo2.spin(1)
                       )
               )
        );
        while (opModeIsActive());
    }
}
