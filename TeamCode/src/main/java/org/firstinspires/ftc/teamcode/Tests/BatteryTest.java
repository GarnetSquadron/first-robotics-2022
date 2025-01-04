package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "BatteryTest")

public class BatteryTest extends OpMode {
    DcMotor lf;
    DcMotor rf;
    DcMotor lb;
    DcMotor rb;


    @Override
    public void init() {
        lf.setPower(-1);
        rf.setPower(1);
        lb.setPower(-1);
        rb.setPower(1);
    }

    @Override
    public void loop() {

    }
}
