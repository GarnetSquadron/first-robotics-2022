package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.pathing.roadrunner.drives.MecanumDrive;

@TeleOp(name = "DriveMotorTest")
@Disabled
public class DriveMotorTest extends OpMode
{

    MecanumDrive drive;
    DcMotor lf, rf, lb, rb;

    @Override
    public void init()
    {
        lf = hardwareMap.get(DcMotor.class, "lf");
        rf = hardwareMap.get(DcMotor.class, "rf");
        lb = hardwareMap.get(DcMotor.class, "lb");
        rb = hardwareMap.get(DcMotor.class, "rb");
    }

    @Override
    public void loop()
    {
        lf.setPower(0);
        rf.setPower(0);
        lb.setPower(0);
        rb.setPower(0);
        if (gamepad1.a) {
            lf.setPower(1);
            telemetry.addLine("lf");
        }
        if (gamepad1.b) {
            rf.setPower(1);
            telemetry.addLine("rf");
        }
        if (gamepad1.x) {
            lb.setPower(1);
            telemetry.addLine("lb");
        }
        if (gamepad1.y) {
            rb.setPower(1);
            telemetry.addLine("rb");
        }
        telemetry.update();
    }
}
