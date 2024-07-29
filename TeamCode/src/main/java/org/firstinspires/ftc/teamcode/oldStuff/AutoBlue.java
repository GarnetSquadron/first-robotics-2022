package org.firstinspires.ftc.teamcode.oldStuff;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "auto" + "blue")
public class AutoBlue extends LinearOpMode {




    //We are defining all motors here, as to manually control each motor rather than use terry hardware.
    private DcMotor lf;
    private DcMotor rf;
    private DcMotor lb;
    private DcMotor rb;
    private Servo claw;
    //private DcMotor arm;
    //private Servo claw;
    //ColorSensor colorSensor;
    //Wheel encoders
    //This is the gobilda encoder value that is used
    final double wheelUnitTicks = 537.7;

    //this is the gear ratio (this is to make it looks consistent with the arm encoders)
    final double wheelGearRatios = 1;

    //multiplies wheelUnitTicks by wheelGearRatios
    final double wheelRotation = (wheelUnitTicks * wheelGearRatios);

    //This is the circumference value for the gobilda wheels we use in inches
    final double wheelCircumference = 12.5663706144;

    //this is the wheelUnitTicks divided by wheelCircumference to get the amount of ticks to move one inch, this
    //makes it so we can just call on this value instead of pasting the number each time in code, as well as
    //the value can be multiplied by the amount of inches desired to make it more simple.
    final double wheelOneInch = (wheelRotation / wheelCircumference);

    public void move(double direction, double power) {
        lf.setPower(Math.sin(direction - Math.PI / 4) * power);
        rf.setPower(Math.sin(direction + Math.PI / 4) * power);
        lb.setPower(Math.sin(direction - Math.PI / 4) * power);
        rb.setPower(Math.sin(direction + Math.PI / 4) * power);
    }
//hello



    public void forward(double power) {//forward(1);forward(-1);
        lf.setPower(power);
        rf.setPower(power);
        lb.setPower(power);
        rb.setPower(power);

    }


    public void resetEncoders() {
        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void runToPosition()  {
        lf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void forward(double power,double distance) {//forward(1);forward(-1);
        //rb.resetDeviceConfigurationForOpMode();
        resetEncoders();
        lf.setTargetPosition((int) (distance*wheelOneInch));
        rf.setTargetPosition((int) (distance*wheelOneInch));
        lb.setTargetPosition((int) (distance*wheelOneInch));
        rb.setTargetPosition((int) (distance*wheelOneInch));

        runToPosition();
//        lf.setTargetPosition((int) (distance*wheelOneInch));
//        rf.setTargetPosition((int) (-distance*wheelOneInch));
//        lb.setTargetPosition((int) (distance*wheelOneInch));
//        rb.setTargetPosition((int) (-distance*wheelOneInch));

        lf.setPower(power);
        rf.setPower(power);
        lb.setPower(power);
        rb.setPower(power);

        while (lf.isBusy() && lb.isBusy() && rf.isBusy() && rb.isBusy()) {
            /*
            lf.setPower(power);
            rf.setPower(power);
            lb.setPower(-power);
            rb.setPower(power);

            */
            telemetry.addData("rb encoder: ",rb.getCurrentPosition());
            telemetry.addData("power: ",rb.getPower());
            telemetry.addData("lb encoder: ",lb.getCurrentPosition());
            telemetry.addData("power: ",lb.getPower());
            telemetry.addData("rf encoder: ",rf.getCurrentPosition());
            telemetry.addData("power: ",rf.getPower());
            telemetry.addData("lf encoder: ",lf.getCurrentPosition());
            telemetry.addData("power: ",lf.getPower());
            telemetry.update();
        }


        lf.setPower(0);
        rf.setPower(0);
        lb.setPower(0);
        rb.setPower(0);


        /*
        while(Math.abs(rb.getCurrentPosition())<distance*wheelOneInch) {
            telemetry.addData("encoder: ",rb.getCurrentPosition());
            telemetry.update();
            //System.out.println(rb.getCurrentPosition());
            lf.setPower(power);
            rf.setPower(power);
            lb.setPower(power);
            rb.setPower(power);
        }

        lf.setPower(0);
        rf.setPower(0);
        lb.setPower(0);
        rb.setPower(0);

         */


    }

    public void backward(double power) {
        lf.setPower(-power);
        rf.setPower(-power);
        lb.setPower(-power);
        rb.setPower(-power);

    }
    public void backward(double power,double distance) {//forward(1);forward(-1);
        //rb.resetDeviceConfigurationForOpMode();
        resetEncoders();
        lf.setTargetPosition((int) (-distance * wheelOneInch));
        rf.setTargetPosition((int) (-distance * wheelOneInch));
        lb.setTargetPosition((int) (-distance * wheelOneInch));
        rb.setTargetPosition((int) (-distance * wheelOneInch));

        runToPosition();
//        lf.setTargetPosition((int) (distance*wheelOneInch));
//        rf.setTargetPosition((int) (-distance*wheelOneInch));
//        lb.setTargetPosition((int) (distance*wheelOneInch));
//        rb.setTargetPosition((int) (-distance*wheelOneInch));

        lf.setPower(-power);
        rf.setPower(-power);
        lb.setPower(-power);
        rb.setPower(-power);
    }
    public void right(double power) {
        lf.setPower(-power);
        rf.setPower(power);
        lb.setPower(-power);
        rb.setPower(power);

    }
    public void right(double power,double distance) {//forward(1);forward(-1);
        //rb.resetDeviceConfigurationForOpMode();
        resetEncoders();
        lf.setTargetPosition((int) (-distance * wheelOneInch));
        rf.setTargetPosition((int) (distance * wheelOneInch));
        lb.setTargetPosition((int) (-distance * wheelOneInch));
        rb.setTargetPosition((int) (distance * wheelOneInch));

        runToPosition();
//        lf.setTargetPosition((int) (distance*wheelOneInch));
//        rf.setTargetPosition((int) (-distance*wheelOneInch));
//        lb.setTargetPosition((int) (distance*wheelOneInch));
//        rb.setTargetPosition((int) (-distance*wheelOneInch));

        lf.setPower(-power);
        rf.setPower(power);
        lb.setPower(-power);
        rb.setPower(power);
    }

    public void left(double power) {
        lf.setPower(power);
        rf.setPower(-power);
        lb.setPower(power);
        rb.setPower(-power);

    }
    public void left(double power,double distance) {//forward(1);forward(-1);
        //rb.resetDeviceConfigurationForOpMode();
        resetEncoders();
        lf.setTargetPosition((int) (distance * wheelOneInch));
        rf.setTargetPosition((int) (-distance * wheelOneInch));
        lb.setTargetPosition((int) (distance * wheelOneInch));
        rb.setTargetPosition((int) (-distance * wheelOneInch));

        runToPosition();
//        lf.setTargetPosition((int) (distance*wheelOneInch));
//        rf.setTargetPosition((int) (-distance*wheelOneInch));
//        lb.setTargetPosition((int) (distance*wheelOneInch));
//        rb.setTargetPosition((int) (-distance*wheelOneInch));

        lf.setPower(power);
        rf.setPower(-power);
        lb.setPower(power);
        rb.setPower(-power);
    }

    public void Stop() {
        lf.setPower(0);
        rf.setPower(0);
        lb.setPower(0);
        rb.setPower(0);

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


    //this is test for the first scrimmage
    public void autoScrimmage() {
        forward(-.25, -28);
        sleep(200);
        if(true){ //Put yes statement here for detection of spike marker
            forward(-.25, -9);
            forward(.25, 9);
        } //end of yes statement
        else{ //Put no statement here for detection of spike marker
            right(.25,3);
            if(true){ //Put yes statement here for detection of spike marker
                forward(-.25, -4);
                forward(.25, 4);
                right(-.25,-3);
            } //end of yes statement
            else { //Put no statement here for detection of spike marker
                right(-.25,-6);
                forward(-.25, -4);
                forward(.25, 4);
                right(.25,3);
            }

        }
        //forward(.25, 28);

    }
    //--------------------------------------------------------------------
    public void autoScrimmage2() {
        forward(-.25, -48);
        sleep(200);
        forward(-.25, -5);
        forward(.25, 5);
    }

    //-------------------------------------------------------------------

    public void autoScrimmage3() {
        forward(-.25, -38);
        forward(.25, 26);
        lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right(.25);
        sleep(1600);
        forward(0);
        forward(.25,5);
        forward(-.25,-36);
    }

    //-----------------------------------------------------

    public void autoScrimmage5() {
        striaferight(-.25);
        sleep(1000);
        forward(0);
    }

    public void quarterTurn() {
        right(1);
        sleep(200);
        forward(0);
    }

    public void autoScrimmagered() {
        lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        striaferight(.25);
        sleep(3400);
        forward( .25,-40);
        forward( .25,5);

    }

    public void autoScrimmageblue() {
        lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        striaferight(-.25);
        sleep(3400);
        forward( .25,-40);
        forward( .25,5);

    }




    @Override
    public void runOpMode() throws InterruptedException {
        lf = hardwareMap.get(DcMotor.class, "lf");
        rf = hardwareMap.get(DcMotor.class, "rf");
        lb = hardwareMap.get(DcMotor.class, "lb");
        rb = hardwareMap.get(DcMotor.class, "rb");
        rf.setDirection(DcMotorSimple.Direction.REVERSE);
        rb.setDirection(DcMotorSimple.Direction.REVERSE);
        //arm = hardwareMap.get(DcMotor.class, "arm");
        //claw = hardwareMap.get(Servo.class, "claw");
        //colorSensor = hardwareMap.colorSensor.get("color");
        waitForStart();
        autoScrimmageblue();

    }
    }