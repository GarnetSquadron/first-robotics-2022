package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "TerryEncoder") // ham emoji
public class TerryTeleopEncoder extends LinearOpMode {
    private TerryHardware terryHardware;
    private double defaultSensitivity = 1.0;
    private double slowSensitivity = 0.5;
    private double turboSensitivity = 2;
    private double currentSensitivity = 1.0;

    private DcMotor arm;

    @Override
    public void runOpMode() {
        arm = hardwareMap.get(DcMotor.class, "arm");
        terryHardware = new TerryHardware(hardwareMap);
        terryHardware.initHardware();
//      terryHardware.setClawPosition(1);
        waitForStart();

        arm.setTargetPosition(0);
//TODO: Remove this if we run a auto that does this.
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
// // 2,786.2 * 1/99.5 * 3/1
        //unitTicks taken from https://www.gobilda.com/5203-series-yellow-jacket-planetary-gear-motor-99-5-1-ratio-24mm-length-8mm-rex-shaft-60-rpm-3-3-5v-encoder/
        final double unitTicks = 2786.2;
        //gearRatios is 1/99 * 3/1
        final double gearRatios = 0.03015075;
        //this makes the full equation 2,786.2 (unitTicks) * 1/99.5 * 3/1 (gearRatios) and defines it to armRotation
        final double armRotation = (unitTicks * gearRatios);
        //1 is placeholder for now
        final double angle = 1;

        //taking angel and making it into a percent
        final double percentAngle = angle/360;
        //will define max rotation
        final double allowedRotation = (percentAngle * armRotation);

        while (opModeIsActive()) {
            //Every loop, get the arm current position. For some reason, java required me to do this.
            //I do as the robot gods ask.
            int armPos = arm.getCurrentPosition();
            //drive robot according to sticks * sensitivity. I am very sensitive irl. Please don't bully me. aka yoink mcsploink
            //ftc judges please understand this is a inside joke
            //COMMENTED OUT FOR ENCODER USE
            //terryHardware.driveRobot(-gamepad1.left_stick_y * currentSensitivity, gamepad1.left_stick_x * currentSensitivity, gamepad1.right_stick_x * currentSensitivity);
            //todo: remove this once encoder arm work is done. will be useless.
            if(gamepad2.right_bumper) {
                arm.setPower(-0.12);
            }

            //claw controls
            //check if the servo is plugged in the right way, if this is broken.

            if (gamepad2.a) {
                //opens claw
                terryHardware.setClawPosition(0.4);
            }

            //jesse, jesse we need a setClawPosition. JESSE!
            //Closes claw, just like me closed off to commitment
            if (gamepad2.b) {
                terryHardware.setClawPosition(0.05);
            }

            //arm pos code ahead. checks if in between a amount, then moves according to gamepad 2 right stick.
            //todo: replace -50000 with number we want as ending number.
            if (arm.getTargetPosition() >= 0 && arm.getTargetPosition() <= -50000){
                arm.setTargetPosition((armPos -= (20 * (gamepad2.right_stick_y))));
            }

            else if (arm.getTargetPosition() >= 20){
                arm.setTargetPosition((armPos -= java.lang.Math.abs(gamepad2.right_stick_y)));
            }
            else if (arm.getTargetPosition() <= -50000){
                arm.setTargetPosition((armPos += java.lang.Math.abs(gamepad2.right_stick_y)));
            }

//            if (gamepad2.x) {
//                arm.setPower(-.75);
//            }
//
//            else if(gamepad2.y) {
//                arm.setPower(.75);
//            }
//            else {
//                arm.setPower(0);
//            }
            if (gamepad1.right_bumper) {
                currentSensitivity = turboSensitivity;
            } else if (gamepad1.left_bumper) {
                currentSensitivity = slowSensitivity;
            } else {
                currentSensitivity = defaultSensitivity;
            }
            telemetry.addData("Current Position",arm.getCurrentPosition());
            telemetry.addData("Target Position",arm.getTargetPosition());
        }
    }
}
//static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION)
// DRIVE_GEAR_REDUCTION = 1/99.5

