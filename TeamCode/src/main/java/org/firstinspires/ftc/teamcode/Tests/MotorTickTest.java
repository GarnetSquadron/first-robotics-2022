package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Motor Tick Test", group = "tuning")
@Disabled
public class MotorTickTest extends LinearOpMode
{
    DcMotor motor;

    @Override

    public void runOpMode() throws InterruptedException
    {
        motor = hardwareMap.get(DcMotor.class, "LeftViper");
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("Motor ticks", motor.getCurrentPosition());
            telemetry.update();

        }
    }
}
