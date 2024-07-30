package org.firstinspires.ftc.teamcode.oldStuff;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


//This is a modified version of BobOmni. This includes a slow mode, but I don't know if it was up to
//standards. Thus, this copy was created.
@TeleOp(name = "test egg")
@Disabled
public class bobomnicopy extends LinearOpMode {
    //omni/mechanum wheels variable definition
    private static final double GAIN_DRIVE = 0.8;
    private static final double GAIN_STRAFE = -0.8;
    private static final double GAIN_TURN = 0.6;
    //part of above, this variable exists as a place holder to check if the turbo is pressed
    private boolean turbo = false;

    //define motors and servos
    private DcMotor lf;
    private DcMotor rf;
    private DcMotor lb;
    private DcMotor rb;
    private Servo claw;
    //sensitivities outside of currentSensitivity should not be changed during opmode.
    //they are intended as base sensitivities. tweak them in code, not with a if statement.
    private double defaultSensitivity = 1.0;
    private double slowSensitivity = 0.5;
    private double currentSensitivity = 1.0;

    private double getGain(double gain) {
        return turbo ? Math.copySign(1.0, gain) : gain;
    }

    @Override
    public void runOpMode() {

        lf = hardwareMap.get(DcMotor.class, "lf");
        rf = hardwareMap.get(DcMotor.class, "rf");
        lb = hardwareMap.get(DcMotor.class, "lb");
        rb = hardwareMap.get(DcMotor.class, "rb");
        claw = hardwareMap.get(Servo.class, "claw");

        lf.setDirection(DcMotor.Direction.REVERSE);
        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lb.setDirection(DcMotor.Direction.REVERSE);
        lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        System.out.print("Initialized!");
        waitForStart();
        while (opModeIsActive()) {
            lf.setPower(
                    -GAIN_DRIVE * gamepad1.left_stick_y * currentSensitivity
                            + GAIN_STRAFE * gamepad1.left_stick_x * currentSensitivity
                            + GAIN_TURN * gamepad1.right_stick_x * currentSensitivity
            );
            rf.setPower(
                    -GAIN_DRIVE * gamepad1.left_stick_y * currentSensitivity
                            - GAIN_STRAFE * gamepad1.left_stick_x * currentSensitivity
                            - GAIN_TURN * gamepad1.right_stick_x * currentSensitivity
            );
            lb.setPower(
                    -GAIN_DRIVE * gamepad1.left_stick_y * currentSensitivity
                            - GAIN_STRAFE * gamepad1.left_stick_x * currentSensitivity
                            + GAIN_TURN * gamepad1.right_stick_x * currentSensitivity
            );
            rb.setPower(
                    -GAIN_DRIVE * gamepad1.left_stick_y * currentSensitivity
                            + GAIN_STRAFE * gamepad1.left_stick_x * currentSensitivity
                            - GAIN_TURN * gamepad1.right_stick_x * currentSensitivity
            );
            //turbo button. these are variables for easier changes, easy enough if you don't
            //even understand code. that's the only reason they are variables.

            if (gamepad1.left_bumper)
                currentSensitivity = slowSensitivity;
            else
                currentSensitivity = defaultSensitivity;
            //claw controls

            if (gamepad1.a) {
                claw.setPosition(.5);
                //sleep(100);
            } else if (gamepad1.b) {
                claw.setPosition(0);
                //sleep(100);
                //sleep probably causes the issue. just going to comment this out, and use a low number to change it
            }

            telemetry.addData("Left Front", lf.getPower());
            telemetry.addData("Right Front", rf.getPower());
            telemetry.addData("Left Back", lb.getPower());
            telemetry.addData("Right Back", rb.getPower());
            telemetry.addData("Claw Position", claw.getPosition());
            telemetry.addData("a button", gamepad1.a);
            telemetry.addData("b button", gamepad1.b);
            telemetry.update();
        }
    }
}
