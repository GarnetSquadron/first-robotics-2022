package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp(name = "\uD83C\uDF56") // ham emoji
public class TerryTeleop extends LinearOpMode {
    private TerryHardware terryHardware;
    private double defaultSensitivity = 1.0;
    private double slowSensitivity = 0.5;
    private double turboSensitivity = 1.5;
    private double currentSensitivity = 1.0;

    private DcMotor arm;

    @Override
    public void runOpMode() {
        arm = hardwareMap.get(DcMotor.class, "arm");
        terryHardware = new TerryHardware(hardwareMap);
        terryHardware.initHardware();
        waitForStart();
        while (opModeIsActive()) {
            //drive robot according to sticks * sensitivity. I am very sensitive irl. Please don't bully me. aka yoink mcsploink
            //ftc judges please understand this is a inside joke
            terryHardware.driveRobot(-gamepad1.left_stick_y * currentSensitivity, gamepad1.left_stick_x * currentSensitivity, gamepad1.right_stick_x * currentSensitivity);

            arm.setPower(-gamepad2.right_stick_y * 0.25);

            while(gamepad2.right_bumper) {
                arm.setPower(-0.1); //TODO: needs to be slower. Fixed direction, do not change this to positive lol
            }

            //claw controls
            //TODO: this does not work at all for some reason. either claw is not defined, or you need a new servo.
            //probably servo knowing our luck honestly
            while (gamepad2.a) {
                //yo bass pls i need claw stuff im gonna cry :-;
                //Make this one the open claw, DIS OPEN CLAW UWU
                terryHardware.setClawPosition(0.5);
            }
            //jesse, jesse we need a setClawPosition. JESSE!
            //Make this on close the claw, just like me closed off to commitment
            while (gamepad2.b) {
                terryHardware.setClawPosition(0);
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