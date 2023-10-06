package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.JavaUtil;

public class teleop1 extends LinearOpMode {
    public void move(double direction, double power){
        lf.setPower(Math.sin(direction-Math.PI/4)*power);
        rf.setPower(Math.sin(direction+Math.PI/4)*power);
        lb.setPower(Math.sin(direction-Math.PI/4)*power);
        rb.setPower(Math.sin(direction+Math.PI/4)*power);
    }
    //We are defining all motors here, as to manually control each motor rather than use terry hardware.
    private DcMotor lf;
    private DcMotor rf;
    private DcMotor lb;
    private DcMotor rb;
    private DcMotor arm;
    private Servo claw;
    private
    ColorSensor colorSensor;

    public void runOpMode() throws InterruptedException {
        lf = hardwareMap.get(DcMotor.class, "lf");
        rf = hardwareMap.get(DcMotor.class, "rf");
        lb = hardwareMap.get(DcMotor.class, "lb");
        rb = hardwareMap.get(DcMotor.class, "rb");
        arm = hardwareMap.get(DcMotor.class, "arm");
        claw = hardwareMap.get(Servo.class, "claw");
        colorSensor = hardwareMap.colorSensor.get("color");
        waitForStart();


    }

}
