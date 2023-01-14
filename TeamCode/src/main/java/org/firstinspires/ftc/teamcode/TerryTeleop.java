package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "better \uD83E\uDD5A")
public class TerryTeleop extends LinearOpMode {
    private TerryHardware terryHardware;
    private double defaultSensitivity = 1.0;
    private double slowSensitivity = 0.5;
    private double turboSensitivity = 1.5;
    private double currentSensitivity = 1.0;

    @Override
    public void runOpMode() {
        terryHardware = new TerryHardware(hardwareMap);
        terryHardware.initHardware();
        waitForStart();
        while (opModeIsActive()) {
            //drive robot according to sticks * sensitivity. I am very sensitive irl. Please don't bully me. aka yoink mcsploink
            //ftc judges please understand this is a inside joke
            terryHardware.driveRobot(-gamepad1.left_stick_y * currentSensitivity, gamepad1.left_stick_x * currentSensitivity, gamepad1.right_stick_x * currentSensitivity);

            //claw controls
            while (gamepad1.a) {
                //yo bass pls i need claw stuff im gonna cry :-;
                //Make this one the open claw, DIS OPEN CLAW UWU
                terryHardware.setClawPosition(0.5);
            }
            //jesse, jesse we need a setClawPosition. JESSE!
            //Make this on close the claw, just like me closed off to commitment
            while (gamepad1.b) {
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