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
    private double MaxLiftPos = 20;
    private double MinLiftPos = 0;
    public void resetEncoders() {
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void runToPosition()  {
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
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
        while (lift.isBusy()) {
            telemetry.addData("lf encoder: ",lift.getCurrentPosition());
            telemetry.addData("power: ",lift.getPower());
            telemetry.update();
        }
        //-------------------------End While--------------------------------------------------------
        stop(); //stopping all motors
    }

    @Override
    public void runOpMode() {
        resetEncoders();
        runToPosition();
        //arm = hardwareMap.get(DcMotor.class, "arm");
        //arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        terryHardware = new TerryHardware(hardwareMap);
        terryHardware.initHardware();

//      terryHardware.setClawPosition(1);
        waitForStart();
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
                claw.setPosition(MaxClawPos);
            }
            if(gamepad1.b){
                claw.setPosition(MinClawPos);
            }
            if(gamepad1.x){
                Lift(1,20);
            }
            if(gamepad1.a){
                Lift(-1,0);
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