package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "MotorDefTest")
@Disabled
public class MotorDefTest extends LinearOpMode
{
    DcMotor motor1;
    DcMotor motor2;

    @Override
    public void runOpMode()
    {
        motor1 = hardwareMap.get(DcMotor.class, "arm");
        motor2 = motor1;
        waitForStart();
        motor2.getCurrentPosition();
    }
}
