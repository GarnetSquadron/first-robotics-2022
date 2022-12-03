package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "\uD83E\uDD5A")
public class BobOmni extends LinearOpMode {
    private static final double GAIN_DRIVE = 0.8;
    private static final double GAIN_STRAFE = -0.8;
    private static final double GAIN_TURN = 0.6;

    private boolean turbo = false;

    private DcMotor lf;
    private DcMotor rf;
    private DcMotor lb;
    private DcMotor rb;

    private double getGain(double gain) {
        return turbo ? Math.copySign(1.0, gain) : gain;
    }

    @Override
    public void runOpMode() {
        lf = hardwareMap.get(DcMotor.class, "lf");
        rf = hardwareMap.get(DcMotor.class, "rf");
        lb = hardwareMap.get(DcMotor.class, "lb");
        rb = hardwareMap.get(DcMotor.class, "rb");

        lf.setDirection(DcMotor.Direction.REVERSE);
        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lb.setDirection(DcMotor.Direction.REVERSE);
        lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();
        while (opModeIsActive()) {
            turbo = gamepad1.right_bumper;

            lf.setPower(
                    - getGain(GAIN_DRIVE)*gamepad1.left_stick_y
                            + getGain(GAIN_STRAFE)*gamepad1.left_stick_x
                            + getGain(GAIN_TURN)*gamepad1.right_stick_x
            );
            rf.setPower(
                    - getGain(GAIN_DRIVE)*gamepad1.left_stick_y
                            - getGain(GAIN_STRAFE)*gamepad1.left_stick_x
                            - getGain(GAIN_TURN)*gamepad1.right_stick_x
            );
            lb.setPower(
                    - getGain(GAIN_DRIVE)*gamepad1.left_stick_y
                            - getGain(GAIN_STRAFE)*gamepad1.left_stick_x
                            + getGain(GAIN_TURN)*gamepad1.right_stick_x
            );
            rb.setPower(
                    - getGain(GAIN_DRIVE)*gamepad1.left_stick_y
                            + getGain(GAIN_STRAFE)*gamepad1.left_stick_x
                            - getGain(GAIN_TURN)*gamepad1.right_stick_x
            );

            telemetry.addData("Left Front", lf.getPower());
            telemetry.addData("Right Front", rf.getPower());
            telemetry.addData("Left Back", lb.getPower());
            telemetry.addData("Right Back", rb.getPower());
            telemetry.update();
        }
    }
}