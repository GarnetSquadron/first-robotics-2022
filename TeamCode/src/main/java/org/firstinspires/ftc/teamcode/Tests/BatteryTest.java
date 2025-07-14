package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "BatteryTest")

public class BatteryTest extends OpMode
{
    DcMotor lf;
    DcMotor rf;
    DcMotor lb;
    DcMotor rb;


    @Override
    public void init()
    {
        lf = hardwareMap.get(DcMotor.class, "lf");
        rf = hardwareMap.get(DcMotor.class, "rf");
        lb = hardwareMap.get(DcMotor.class, "lb");
        rb = hardwareMap.get(DcMotor.class, "rb");
        lf.setPower(-1);
        rf.setPower(1);
        lb.setPower(-1);
        rb.setPower(1);
    }

    @Override
    public void loop()
    {

    }
}
