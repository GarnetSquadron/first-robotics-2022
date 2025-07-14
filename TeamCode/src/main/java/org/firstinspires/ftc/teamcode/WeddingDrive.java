package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class WeddingDrive
{
    DcMotorEx lf, rf, lb, rb, leftViper, rightViper;

    public WeddingDrive(HardwareMap hardwareMap)
    {
        lf = hardwareMap.get(DcMotorEx.class, "lf");
        rf = hardwareMap.get(DcMotorEx.class, "rf");
        lb = hardwareMap.get(DcMotorEx.class, "lb");
        rb = hardwareMap.get(DcMotorEx.class, "rb");
        //rightViper = hardwareMap.get(DcMotorEx.class, "rightViper");
        //leftViper = hardwareMap.get(DcMotorEx.class, "rightViper");
        //rightViper.setDirection(DcMotorSimple.Direction.REVERSE);
        lf.setDirection(DcMotorSimple.Direction.REVERSE);
        rb.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void tankDrive(double leftInput, double rightInput)
    {
        lf.setPower(leftInput);
        lb.setPower(leftInput);
        rf.setPower(rightInput);
        rb.setPower(rightInput);
    }

    public void intuitiveDrive(double xInput, double yInput)
    {
        tankDrive(yInput + xInput, yInput - xInput);
    }

    public void vipers(double power)
    {
        rightViper.setPower(power);
        leftViper.setPower(power);
    }

    public void run(double power, double xInput, double yInput)
    {
        //vipers(power);
        intuitiveDrive(xInput, yInput);
    }
}
