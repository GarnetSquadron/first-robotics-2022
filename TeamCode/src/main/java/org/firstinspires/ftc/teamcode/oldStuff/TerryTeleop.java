package org.firstinspires.ftc.teamcode.oldStuff;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;


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
    private Servo funnel;
    private Servo launcher;
    private DcMotor lift;
    private DistanceSensor sensor;
    private double MaxClawPos = 0.45;
    private double MinClawPos = 0.9;
    private double MaxLiftPos = 6750;//DO NOt go over 7267
    private double MinLiftPos = 0;
    private double MaxArmPos = 627;
    private double MinArmPos = 0;
    private CRServo funnelWheel;


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
    public void FunnelWheel(){
        funnelWheel.setPower(1);

    }
    public void turn(double power){
        terryHardware.driveRobot(0, 0, power);
    }
    public void turn(double power, double degrees){
        //YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        //double initdirection = orientation.getYaw(AngleUnit.DEGREES);
        //imu.resetYaw();<-- again, we dont want this
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        if (Math.abs (orientation.getYaw(AngleUnit.DEGREES)) <=degrees) {


            turn(power);
            telemetry.addData("Yaw (Z)", "%.2f Deg. (Heading)", Math.abs(orientation.getYaw(AngleUnit.DEGREES)));
//          telemetry.addData("Yaw (Z)", "%.2f Deg. (Heading)", Math.abs(orientation.getYaw(AngleUnit.DEGREES)));
            telemetry.addData("Yaw (Z)", "%.2f Deg. (Heading)", Math.abs(orientation.getYaw(AngleUnit.DEGREES)));
            telemetry.addData("Yaw (Z)", "%.2f Deg. (Heading)", Math.abs(orientation.getYaw(AngleUnit.DEGREES)));
            telemetry.update();
            orientation = imu.getRobotYawPitchRollAngles();
        }
        else if (Math.abs (orientation.getYaw(AngleUnit.DEGREES)) >=degrees) {


            turn(-power);
            telemetry.addData("Yaw (Z)", "%.2f Deg. (Heading)", Math.abs(orientation.getYaw(AngleUnit.DEGREES)));
//          telemetry.addData("Yaw (Z)", "%.2f Deg. (Heading)", Math.abs(orientation.getYaw(AngleUnit.DEGREES)));
            telemetry.addData("Yaw (Z)", "%.2f Deg. (Heading)", Math.abs(orientation.getYaw(AngleUnit.DEGREES)));
            telemetry.addData("Yaw (Z)", "%.2f Deg. (Heading)", Math.abs(orientation.getYaw(AngleUnit.DEGREES)));
            telemetry.update();
            orientation = imu.getRobotYawPitchRollAngles();
        }
//        else {
//            turn(0);
//        }

    }
//    public void tele(double power,double height) {//forward(1);forward(-1)
//        //resetEncoders();   <--we dont want this
//        //telearm.setTargetPosition((int) (height));
////--------------------------------------------------------------------------------------------------
//        //telearm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
////--------------------------------------------------------------------------------------------------
//        boolean inbound=Math.signum(power)*telearm.getCurrentPosition()<Math.signum(power)*height;//
//        if (inbound) {
//            telearm.setPower(power);
//        }
//        else {
//            telearm.setPower(0);
//        }
//        telemetry.addData("in bounds: ",inbound);
//        //--------------------------------Telemetry, gives data about position and makes sure it doesnt stop immediately.----------------------
////        while (telearm.isBusy()) {
////            telemetry.addData("lf encoder: ",arm.getCurrentPosition());
////            telemetry.addData("power: ",arm.getPower());
////            telemetry.update();
////        }
//        //-------------------------End While--------------------------------------------------------
//         //stopping all motors
//    }

    public void tele() {
        if(gamepad2.left_stick_y<-0.1){
            telearm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            telearm.setPower(-1);
        }
        else if(gamepad2.left_stick_y>0.1){
            telearm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            telearm.setPower(1);
        }
        else{
            telearm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            telearm.setPower(0);
        }



//        if (telearm.getCurrentPosition()<0){
//            if(gamepad2.left_stick_y<-0.1){
//                telearm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//                telearm.setPower(0);
//            }
//            else if(gamepad2.left_stick_y>0.1){
//                telearm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//                telearm.setPower(1);
//            }
//            else
//                telearm.setPower(0);
//    }
//        else{
//            if(gamepad2.left_stick_y<-0.1){
//                telearm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//                telearm.setPower(-1);
//            }
//            else if(gamepad2.left_stick_y>0.1){
//                telearm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//                telearm.setPower(1);
//            }
//            else
//            telearm.setPower(0);
//        }
    }
    @Override
    public void runOpMode() {

        //arm = hardwareMap.get(DcMotor.class, "arm");
        //arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        terryHardware = new TerryHardware(hardwareMap);
        terryHardware.initHardware();
        arm = hardwareMap.get(DcMotor.class, "arm");
        lift = hardwareMap.get(DcMotor.class, "par");
        claw = hardwareMap.get(Servo.class, "claw");
        //funnel = hardwareMap.get(Servo.class, "funnel");
        funnelWheel = hardwareMap.get(CRServo.class, "funnel");
        telearm = hardwareMap.get(DcMotor.class, "teleArm");
        sensor=hardwareMap.get(DistanceSensor.class, "distance");

        imu = hardwareMap.get(IMU.class, "imu");
        launcher = hardwareMap.get(Servo.class, "launcher");


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




        double time = 0;
        double funnelTime = 0;
        double teleTime = 0;
        boolean funnelTimeBegin=false;
        telemetry.addLine("running");
        while (opModeIsActive()) {


            //ftc judges please understand this is a inside joke
            if(gamepad1.right_trigger>0){
                //align with backdrop if button pressed
                telemetry.addData("IMU:", true);
                turn(0.25,0);

            }
            else {
                //otherwise drive robot according to sticks * sensitivity
                terryHardware.driveRobot(gamepad1.left_stick_y * currentSensitivity, -gamepad1.left_stick_x * currentSensitivity, gamepad1.right_stick_x * currentSensitivity);
            }
            //terryHardware.move(Math.atan2(-gamepad1.left_stick_y,gamepad1.left_stick_x),Math.hypot(gamepad1.left_stick_y,gamepad1.left_stick_x));
            //^working on this universal strafing function

            //arm.setPower(gamepad2.left_stick_y * 0.75);

            if(gamepad2.right_bumper) {
                telearm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                telearm.setPower(1);
            }
            if(gamepad2.left_bumper){
                arm.setPower(-0.35);
            }
            if(gamepad2.right_trigger>0){
                arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }
//            if(gamepad2.right_trigger>0){
//                telearm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//                telearm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//            }


            if(gamepad2.dpad_down){
                claw.setPosition(MaxClawPos);
                clawUp=true;
            }
            if(gamepad2.dpad_up){
                claw.setPosition(MinClawPos);
                clawUp=false;
            }
            if(gamepad2.x){
                //funnel.setPosition(0);
                //Uncomment below for cont servo
                funnelWheel.setPower(1);
                if(!funnelTimeBegin) {
                    funnelTime = getRuntime();
                    funnelTimeBegin = true;
                }
            }
            if(funnelTime+1.6<=getRuntime()){
                //Uncomment below for cont servo
                funnelWheel.setPower(0);
                funnelTimeBegin = false;
                //funnelTime=getRuntime();
            }
//            else if(funnel.getPosition()<0.4){
//                //funnel.setPosition(0.4);
//            }
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

            //**********Finish tommorow***********
            if(gamepad2.right_stick_y<-.1){
                //arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                //arm.setPower(-0.45);
                arm(-0.35,MinArmPos);
                armUp=false;
                time = getRuntime();
            }
            else if(gamepad2.right_stick_y>.1) {
                arm(0.15,MaxArmPos);
                armUp=false;
                time = getRuntime();
            }
            else if(arm.getCurrentPosition()>626){
                arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                arm.setPower(0);
                //arm(0.15,MaxArmPos);
                armUp=true;
            }
            else if (time+1<=getRuntime()){
                //arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                //arm.setPower(0.25);
                
                
                
                arm(0.15, MaxArmPos);
                armUp = true;
            }
//            if(gamepad2.left_stick_y<0){
//                telearm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//                telearm.setPower(-1);
//                //tele(1,3500);
//                teleTime = getRuntime();
//                teleUp=false;
//            }
//            else if(teleTime+3<=getRuntime()){
//                telearm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//                telearm.setPower(1);
//                //tele(-1,0);
//                teleUp=true;
//            }
//            else{
//                tele(0,0);
//                teleUp=false;
//            }
                tele();
            if (gamepad1.dpad_up){
                launcher.setPosition(0.9);
            }
//            if(gamepad2.left_stick_y<0){
//                telearm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
////              telearm.setPower(-1);
//                tele(-0.2,0);
//                teleUp=false;
//            }
//            else if(gamepad2.left_stick_y>0){
//                telearm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
////              telearm.setPower(1);
//                tele(0.2,100);
//                teleUp=true;
//            }
//            else{
//                telearm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
////              telearm.setPower(-1);
//                tele(-0.2,0);
//                teleUp=false;
//            }



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

            telemetry.addData("joystick:",gamepad2.left_stick_y);
            telemetry.addData("Lift:",liftUp);
            telemetry.addData("arm:",armUp);
            telemetry.addData("claw:",clawUp);
            telemetry.addData("telescoping:",teleUp);
            telemetry.addData("clawPos:",claw.getPosition());
            telemetry.addData("arm encoder: ",arm.getCurrentPosition());
            telemetry.addData("power: ",arm.getPower());
            telemetry.addData("direction: ",claw.getDirection());
            telemetry.addData("arm busy?",arm.isBusy());
            telemetry.addData("telescoping encoder",telearm.getCurrentPosition());
            //telemetry.addData("FunnelPos",funnel.getPosition());
            telemetry.addData("left trigger", gamepad1.left_trigger);
            telemetry.addData("right trigger", gamepad1.right_trigger);
            telemetry.addData("Time:",time);
            telemetry.addData("RunTime:",getRuntime());
            telemetry.addData("FunnelBegin:",funnelTimeBegin);

            telemetry.update();


            if (gamepad1.right_bumper) {
                currentSensitivity = turboSensitivity;
            } else if (gamepad1.left_bumper) {
                currentSensitivity = slowSensitivity;
//                if (sensor.getDistance(DistanceUnit.INCH)<=4){
//                    currentSensitivity = 0;
//                }
            } else {
                currentSensitivity = defaultSensitivity;
            }

        }
    }
}