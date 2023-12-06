package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name = "\uD83C\uDF56") // ham emoji
public class TerryTeleop extends LinearOpMode {
    private TerryHardware terryHardware;
    private double defaultSensitivity = 1.0;
    private double slowSensitivity = 0.5;
    private double turboSensitivity = 1.5;
    private double currentSensitivity = 1.0;

    private DcMotor arm;
    private Servo claw;
    private DcMotor lift;
    private double MaxClawPos = 20;
    private double MinClawPos = 0;
    private double MaxLiftPos = 10;
    private double MinLiftPos = 0;
    private double MaxArmPos = 20;
    private double MinArmPos = 0;

    public void resetEncoders() {
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);//WHY DOES THIS GIVE ERROR :(
    }
    public void runToPosition()  {
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);//WHY DOES THIS GIVE ERROR :(
        //arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void Lift(double power,double height) {//forward(1);forward(-1)
        //resetEncoders();   <--we dont want this
        lift.setTargetPosition((int) (height));
//--------------------------------------------------------------------------------------------------
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
        runToPosition();
//--------------------------------------------------------------------------------------------------
        arm.setPower(power);

        //--------------------------------Telemetry, gives data about position and makes sure it doesnt stop immediately.----------------------
        while (arm.isBusy()) {
            telemetry.addData("lf encoder: ",arm.getCurrentPosition());
            telemetry.addData("power: ",arm.getPower());
            telemetry.update();
        }
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
        //claw = hardwareMap.get(Servo.class, "claw");
        resetEncoders();
//        runToPosition();
//      terryHardware.setClawPosition(1);
        waitForStart();
        boolean liftUp = false;
        boolean armUp = false;
        boolean clawUp = false;
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
            if(gamepad1.y){
                //claw.setPosition(MaxClawPos);
                clawUp=true;
            }
            if(gamepad1.b){
                //claw.setPosition(MinClawPos);
                clawUp=false;
            }
            if(gamepad1.x){
                Lift(-0.75,MinLiftPos);
                liftUp=false;
            }

            else if(gamepad1.a){
                Lift(0.75,MaxLiftPos);
                liftUp=true;
            }
            else {
                Lift(0,0);
                liftUp=false;
            }
            if(gamepad1.left_trigger>0){
                //arm(0.2,0);
                armUp=false;
            }
            if(gamepad1.right_trigger>0){
                //arm(0.75,10);
                armUp=true;
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
            telemetry.update();


            if (gamepad1.right_bumper) {
                currentSensitivity = turboSensitivity;
            } else if (gamepad1.left_bumper) {
                currentSensitivity = slowSensitivity;
            } else {
                currentSensitivity = defaultSensitivity;
            }

        }
    }
}