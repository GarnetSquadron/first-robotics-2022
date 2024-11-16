package org.firstinspires.ftc.teamcode.OpModes.autonomi;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Subsystems.TriangleIntake;
import org.firstinspires.ftc.teamcode.oldStuff.VoidsAndThings;

public class AnthonyAuto extends LinearOpMode {
    TriangleIntake triangleIntake = new TriangleIntake(hardwareMap,"IntakeServo1", "IntakeServo2", "IntakeServo3","pivot");
    DcMotor lf;
    DcMotor rf;
    DcMotor lb;
    DcMotor rb;
    final double wheelUnitTicks = 537.7;
    final double wheelGearRatios = 1;
    final double wheelRotation = (wheelUnitTicks * wheelGearRatios);
    final double wheelCircumference = 12.5663706144;
    final double wheelOneInch = (wheelRotation / wheelCircumference);

    public void forward(double power) {
        lf.setPower(power);
        rf.setPower(power);
        lb.setPower(power);
        rb.setPower(power);
    }
    public void turn(double power) {
        lf.setPower(-power);
        rf.setPower(power);
        lb.setPower(-power);
        rb.setPower(power);
    }
    public void striaferight(double power) {
        lf.setPower(power);
        rf.setPower(-power);
        lb.setPower(-power);
        rb.setPower(power);
    }

    public void striafeleft(double power) {
        lf.setPower(-power);
        rf.setPower(power);
        lb.setPower(power);
        rb.setPower(-power);
    }
    public void Stop() {
        lf.setPower(0);
        rf.setPower(0);
        lb.setPower(0);
        rb.setPower(0);
    }
    public void Red
    @Override
    public void runOpMode() throws InterruptedException {

        }


    }
}
