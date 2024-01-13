package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
//IMU imports
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;


import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


@TeleOp(name = "\uD83C\uDF56") // ham emoji
public class TerryTeleop extends LinearOpMode {
    private TerryHardware terryHardware;
    private double defaultSensitivity = 1.0;
    private double slowSensitivity = 0.4;
    private double turboSensitivity = 1.5;
    private double currentSensitivity = 1.0;
    IMU imu;



    private DcMotor arm;//arm motor goes to port 1
    private DcMotor telearm;
    private Servo claw;
    private DcMotor lift;
     private DistanceSensor sensor;
    private double MaxClawPos = 0.3;
    private double MinClawPos = 0.6;
    private double MaxLiftPos = 6750;//DO NOt go over 7267
    private double MinLiftPos = 0;
    private double MaxArmPos = 565;
    private double MinArmPos = 10;

    public void resetEncoders() {
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);//WHY DOES THIS GIVE ERROR :(
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        telearm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void runWithoutEncoders() {
        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void runToPosition()  {
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);//WHY DOES THIS GIVE ERROR :(
        //arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void Lift(double power,double height) {//forward(1);forward(-1)
        //resetEncoders();   <--we dont want this
        lift.setTargetPosition((int) (height));
        //runWithoutEncoders();
//        telemetry.addData("lf encoder: ",lift.getCurrentPosition());
//        telemetry.update();

//-------------------------------------------------------------------------------------------------
        runToPosition();
//--------------------------------------------------------------------------------------------------
        lift.setPower(power);

        //--------------------------------Telemetry, gives data about position and makes sure it doesnt stop immediately.----------------------
//        while (lift.isBusy()) {
//            telemetry.addData("lf encoder: ",lift.getCurrentPosition());
//            telemetry.addData("power: ",lift.getPower());
//            telemetry.update();
//        }
//        //-------------------------End While--------------------------------------------------------
//        stop(); //stopping all motors
    }
    public void arm(double power,double height) {//forward(1);forward(-1)
        //resetEncoders();   <--we dont want this
        arm.setTargetPosition((int) (height));
//--------------------------------------------------------------------------------------------------
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//--------------------------------------------------------------------------------------------------
        arm.setPower(power);

        //--------------------------------Telemetry, gives data about position and makes sure it doesnt stop immediately.----------------------
//        while (arm.isBusy()) {
//            telemetry.addData("lf encoder: ",arm.getCurrentPosition());
//            telemetry.addData("power: ",arm.getPower());
//            telemetry.update();
//        }
        //-------------------------End While--------------------------------------------------------
        stop(); //stopping all motors
    }
    public void tele(double power,double height) {//forward(1);forward(-1)
        //resetEncoders();   <--we dont want this
        telearm.setTargetPosition((int) (height));
//--------------------------------------------------------------------------------------------------
        telearm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//--------------------------------------------------------------------------------------------------
        telearm.setPower(power);

        //--------------------------------Telemetry, gives data about position and makes sure it doesnt stop immediately.----------------------
//        while (telearm.isBusy()) {
//            telemetry.addData("lf encoder: ",arm.getCurrentPosition());
//            telemetry.addData("power: ",arm.getPower());
//            telemetry.update();
//        }
        //-------------------------End While--------------------------------------------------------
        stop(); //stopping all motors
    }
    

    @Override
    public void runOpMode() {

        //arm = hardwareMap.get(DcMotor.class, "arm");
        //arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        terryHardware = new TerryHardware(hardwareMap);
        terryHardware.initHardware();
        arm = hardwareMap.get(DcMotor.class, "arm");
        lift = hardwareMap.get(DcMotor.class, "lift");
        claw = hardwareMap.get(Servo.class, "claw");
        telearm = hardwareMap.get(DcMotor.class, "teleArm");
        sensor=hardwareMap.get(DistanceSensor.class, "distance");


        imu = hardwareMap.get(IMU.class, "imu");

        RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.UP;
        RevHubOrientationOnRobot.UsbFacingDirection  usbDirection  = RevHubOrientationOnRobot.UsbFacingDirection.FORWARD;
        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);
        imu.initialize(new IMU.Parameters(orientationOnRobot));
        imu.resetYaw();

        resetEncoders();
//        runToPosition();
//      terryHardware.setClawPosition(1);
        waitForStart();
        boolean liftUp = false;
        boolean armUp = false;
        boolean clawUp = false;
        boolean teleUp = false;





        telemetry.addLine("running");
        while (opModeIsActive()) {

            //drive robot according to sticks * sensitivity. I am very sensitive irl. Please don't bully me. aka yoink mcsploink
            //ftc judges please understand this is a inside joke
            terryHardware.driveRobot(-gamepad1.left_stick_y * currentSensitivity, gamepad1.left_stick_x * currentSensitivity, gamepad1.right_stick_x * currentSensitivity);
            //terryHardware.move(Math.atan2(-gamepad1.left_stick_y,gamepad1.left_stick_x),Math.hypot(gamepad1.left_stick_y,gamepad1.left_stick_x));
            //^working on this universal strafing function

            //arm.setPower(gamepad2.left_stick_y * 0.75);

            if(gamepad2.right_bumper) {
                //arm.setPower(-0.1);
            }
            if(gamepad2.y){
                claw.setPosition(MaxClawPos);
                clawUp=true;
            }
            if(gamepad2.b){
                claw.setPosition(MinClawPos);
                clawUp=false;
            }
            if(gamepad1.a){
                Lift(-0.75,MinLiftPos);
                liftUp=false;
            }

            else if(gamepad1.x){
                Lift(0.75,MaxLiftPos);
                liftUp=true;
            }
            else {
                Lift(0,0);
                liftUp=false;
            }
            if(gamepad1.left_trigger>0){
                arm(-0.35,MinArmPos);
                armUp=false;
            }
            if(gamepad1.right_trigger>0){
                arm(0.15,MaxArmPos);
                armUp=true;
            }
            if(gamepad2.left_trigger>0){
                tele(0.2,0);
                teleUp=false;
            }
            if(gamepad2.right_trigger>0){
                tele(0.2,10);
                teleUp=true;
            }



            //claw controls
            //probably servo knowing our luck honestly
            /*
            if (gamepad2.back) {
                //full open - DO NOT USE UNLESS YOU ARE HAVING SOME SERIOUS ISSUES WITH CLAW.
                terryHardware.setClawPosition(1);
            }
            if (gamepad2.a) {
                //yo bass pls i need claw stuff im gonna cry :-;
                //Make this one the open claw, DIS OPEN CLAW UWU
                terryHardware.setClawPosition(0.4);
            }
            //jesse, jesse we need a setClawPosition. JESSE!
            //Make this on close the claw, just like me closed off to commitment
            if (gamepad2.b) {
                terryHardware.setClawPosition(0.05);
            }


            */


            telemetry.addData("Lift:",liftUp);
            telemetry.addData("arm:",armUp);
            telemetry.addData("claw:",clawUp);
            telemetry.addData("telescoping:",teleUp);
            telemetry.addData("clawPos:",claw.getPosition());
            telemetry.addData("arm encoder: ",arm.getCurrentPosition());
            telemetry.addData("power: ",arm.getPower());
            telemetry.addData("direction: ",claw.getDirection());
            telemetry.addData("arm busy?",arm.isBusy());
            telemetry.update();


            if (gamepad1.right_bumper) {
                currentSensitivity = turboSensitivity;
            } else if (gamepad1.left_bumper) {
                currentSensitivity = slowSensitivity;
                if (sensor.getDistance(DistanceUnit.INCH)<=6){
                    currentSensitivity = 0;
                }
            } else {
                currentSensitivity = defaultSensitivity;
            }

        }
    }
}