package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
//import com.google.blocks.ftcrobotcontroller.runtime.ColorRangeSensorAccess;


public class VoidsAndThings {


    private HardwareMap hardwareMap;

    // Define a constructor that allows the OpMode to pass a reference to itself.
    public VoidsAndThings(HardwareMap voidsAndThings) {
        hardwareMap = voidsAndThings;
    }

    //We are defining all motors here, as to manually control each motor rather than use terry hardware.
    private DcMotor lf;
    private DcMotor rf;
    private DcMotor lb;
    private DcMotor rb;
    private DistanceSensor sensor;
    DcMotor lift;
    Servo claw;
    ColorSensor Fsensor;
    ColorSensor Bsensor;


    //private DcMotor arm;
    //private Servo claw;
    //ColorSensor colorSensor;
    IMU imu;
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






    public void resetEncoders() {
        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void runWithoutEncoders() {
        lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void runToPosition()  {
        lf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void setTgPos(double distance1, double distance2, double distance3, double distance4){
        lf.setTargetPosition((int) (distance1*wheelOneInch));
        rf.setTargetPosition((int) (distance2*wheelOneInch));
        lb.setTargetPosition((int) (distance3*wheelOneInch));
        rb.setTargetPosition((int) (distance4*wheelOneInch));
    }
    public void forward(double power) {//forward(1);forward(-1);
        lf.setPower(power);
        rf.setPower(power);
        lb.setPower(power);
        rb.setPower(power);

    }
    public void forward(double power,double distance) {//forward(1);forward(-1);
        //rb.resetDeviceConfigurationForOpMode();
        resetEncoders();
        setTgPos(distance, distance, distance, distance);

        runToPosition();
//        lf.setTargetPosition((int) (distance*wheelOneInch));
//        rf.setTargetPosition((int) (-distance*wheelOneInch));
//        lb.setTargetPosition((int) (distance*wheelOneInch));
//        rb.setTargetPosition((int) (-distance*wheelOneInch));

        forward(power);

        /*while (lf.isBusy() && lb.isBusy() && rf.isBusy() && rb.isBusy()) {
            *//*
            lf.setPower(power);
            rf.setPower(power);
            lb.setPower(-power);
            rb.setPower(power);

            *//*
            telemetry.addData("rb encoder: ",rb.getCurrentPosition());
            telemetry.addData("power: ",rb.getPower());
            telemetry.addData("lb encoder: ",lb.getCurrentPosition());
            telemetry.addData("power: ",lb.getPower());
            telemetry.addData("rf encoder: ",rf.getCurrentPosition());
            telemetry.addData("power: ",rf.getPower());
            telemetry.addData("lf encoder: ",lf.getCurrentPosition());
            telemetry.addData("power: ",lf.getPower());
            telemetry.addData("Distance (cm)", sensor.getDistance(DistanceUnit.CM));
            telemetry.update();
        }*/


        forward(0);


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
        lf.setPower(power);
        rf.setPower(-power);
        lb.setPower(-power);
        rb.setPower(power);

    }
    public void right(double power,double distance) {//forward(1);forward(-1);
        //rb.resetDeviceConfigurationForOpMode();
        resetEncoders();
        setTgPos(distance, -distance, -distance, distance);

        runToPosition();
//        lf.setTargetPosition((int) (distance*wheelOneInch));
//        rf.setTargetPosition((int) (-distance*wheelOneInch));
//        lb.setTargetPosition((int) (distance*wheelOneInch));
//        rb.setTargetPosition((int) (-distance*wheelOneInch));

        right(power);
    }

    public void left(double power) {
        lf.setPower(-power);
        rf.setPower(power);
        lb.setPower(power);
        rb.setPower(-power);

    }
    public void left(double power,double distance) {//forward(1);forward(-1);
        //rb.resetDeviceConfigurationForOpMode();
        resetEncoders();
        setTgPos(-distance, distance, distance, -distance);

        runToPosition();
//        lf.setTargetPosition((int) (distance*wheelOneInch));
//        rf.setTargetPosition((int) (-distance*wheelOneInch));
//        lb.setTargetPosition((int) (distance*wheelOneInch));
//        rb.setTargetPosition((int) (-distance*wheelOneInch));

        left(power);
    }

    public void Stop() {
        lf.setPower(0);
        rf.setPower(0);
        lb.setPower(0);
        rb.setPower(0);

    }

    public int SpikeCheck() {
        int max1= 10;
        int max2= 20;
        int spike;
        int i=0;
        forward(0.25,1);
        left(0.25,-2);
        while(sensor.getDistance(DistanceUnit.INCH)>=30) {
            right(0.25, 2);
            i+=2;
        }
        if (i<max1)
            spike=1;
        else if (i<max2)
            spike=2;
        else
            spike=3;
        left(0.25, 2*i);

        return spike;

    }

    //this is test for the first scrimmage
    public void autoScrimmage() {
        int spike=SpikeCheck();
        forward(-.25, -28);
        //sleep(200);
        if(spike==1){ //Put yes statement here for detection of spike marker
            forward(-.25, -9);
            forward(.25, 9);
        } //end of yes statement
        else{ //Put no statement here for detection of spike marker
            right(.25,3);
            if(spike==2){ //Put yes statement here for detection of spike marker
                forward(-.25, -4);
                forward(.25, 4);
                right(-.25,-3);
            } //end of yes statement
            else { //Put no statement here for detection of spike marker
                right(-.25, -6);
                forward(-.25, -4);
                forward(.25, 4);
                right(.25, 3);
            }

        }
        //forward(.25, 28);

    }
    private double minRed=20;
    //minimum amount of red in a gamepiece
    public boolean GetColorF(){
        return minRed<Fsensor.red();
    }
    public boolean GetColorB(){
        return minRed<Bsensor.red();
    }
    public void turn(double power){
        lf.setPower(power);
        rf.setPower(power);
        lb.setPower(power);
        rb.setPower(power);
    }
    public void turn(double power, double degrees){
        runWithoutEncoders();
        //YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        //double initdirection = orientation.getYaw(AngleUnit.DEGREES);
        imu.resetYaw();
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        while (Math.abs (orientation.getYaw(AngleUnit.DEGREES)) <=degrees-3) {


            turn(power);
//            telemetry.addData("Yaw (Z)", "%.2f Deg. (Heading)", orientation.getYaw(AngleUnit.DEGREES));
//            telemetry.addData("Pitch (X)", "%.2f Deg.", orientation.getPitch(AngleUnit.DEGREES));
//            telemetry.addData("Roll (Y)", "%.2f Deg.\n", orientation.getRoll(AngleUnit.DEGREES));
//            telemetry.update();
            orientation = imu.getRobotYawPitchRollAngles();
        }
    }
    public void initHardware()    {

        lf = hardwareMap.get(DcMotor.class, "lf");
        rf = hardwareMap.get(DcMotor.class, "rf");
        lb = hardwareMap.get(DcMotor.class, "lb");
        rb = hardwareMap.get(DcMotor.class, "rb");
        lf.setDirection(DcMotorSimple.Direction.REVERSE);
        lb.setDirection(DcMotorSimple.Direction.REVERSE);
        //DistanceSensor sensor;
        //sensor=hardwareMap.get(DistanceSensor.class, "distance");
        Fsensor=hardwareMap.get(ColorSensor.class, "");
        Fsensor=hardwareMap.get(ColorSensor.class, "");
        imu = hardwareMap.get(IMU.class, "imu");
        RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.UP;
        RevHubOrientationOnRobot.UsbFacingDirection  usbDirection  = RevHubOrientationOnRobot.UsbFacingDirection.FORWARD;

        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);
        imu.initialize(new IMU.Parameters(orientationOnRobot));
        //arm = robotHardware.get(DcMotor.class,"arm");
        //claw = robotHardware.get(Servo.class, "claw");
        /*
        YOU MUST ADD THIS TO ANYTHING THAT USES ENCODERS AFTER DEFINING MOTORS
        lf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        */
        //set the encoders to be enabled
        //lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //sets all motors to run without encoders. remember to set a target before turning encoders on!
        lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //lf.setDirection(DcMotor.Direction.REVERSE);
        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //lb.setDirection(DcMotor.Direction.REVERSE);
        lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        System.out.print("Initialized!");
    }


    /*public void driveRobot(double drive, double strafe, double turn) {
        // the proper formula is left stick y, left stick x, right stick x. I.E you want to input those
        // values into this function to get it to be able to drive. Current sensitivity must be defined - default
        // as of 12/16/2022 is 1.0
        lf.setPower(
                GAIN_DRIVE * drive * currentSensitivity
                        - GAIN_STRAFE * strafe * currentSensitivity
                        + GAIN_TURN * turn * currentSensitivity
        );
        rf.setPower(
                GAIN_DRIVE * drive * currentSensitivity
                        + GAIN_STRAFE * strafe * currentSensitivity
                        - GAIN_TURN * turn * currentSensitivity
        );
        lb.setPower(
                GAIN_DRIVE * drive * currentSensitivity
                        + GAIN_STRAFE * strafe * currentSensitivity
                        + GAIN_TURN * turn * currentSensitivity
        );
        rb.setPower(
                GAIN_DRIVE * drive * currentSensitivity
                        - GAIN_STRAFE * strafe * currentSensitivity
                        - GAIN_TURN * turn * currentSensitivity
        );
   }
*/

}



