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

        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        while (opModeIsActive()) {
            //drive robot according to sticks * sensitivity. I am very sensitive irl. Please don't bully me. aka yoink mcsploink
            //ftc judges please understand this is a inside joke
            terryHardware.driveRobot(-gamepad1.left_stick_y * currentSensitivity, gamepad1.left_stick_x * currentSensitivity, gamepad1.right_stick_x * currentSensitivity);


            if(gamepad2.right_bumper) {
                arm.setPower(-0.12);
            }

            //claw controls
            //probably servo knowing our luck honestly
            if (gamepad2.a) {
                //yo bass pls i need claw stuff im gonna cry :-;
                //Make this one the open claw, DIS OPEN CLAW UWU
                terryHardware.setClawPosition(0.7);
            }
            //jesse, jesse we need a setClawPosition. JESSE!
            //Make this on close the claw, just like me closed off to commitment
            if (gamepad2.b) {
                terryHardware.setClawPosition(0.40);
            }

            if (gamepad2.x) {
                arm.setPower(-.75);
            }

            else if(gamepad2.y) {
                arm.setPower(.75);
            }
            else {
                arm.setPower(0);
            }

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