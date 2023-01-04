package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "\uD83E\uDD5A")
public class BobOmni extends LinearOpMode {
    private BobHardware bobHardware;
    private double defaultSensitivity = 1.0;
    private double slowSensitivity = 0.5;
    private double turboSensitivity = 1.5;
    private double currentSensitivity = 1.0;
    @Override
    public void runOpMode() {
      bobHardware=new BobHardware(hardwareMap);
        bobHardware.initHardware();
        waitForStart();
        while (opModeIsActive()) {
            //drive robot according to sticks * sensitivity. I am very sensitive irl. Please don't bully me. aka yoink mcsploink
            //ftc judges please understand this is a inside joke
            bobHardware.driveRobot(-gamepad1.left_stick_y*currentSensitivity,gamepad1.left_stick_x*currentSensitivity,gamepad1.right_stick_x*currentSensitivity);

            //claw controls
            while(gamepad1.a) {
                //yo bass pls i need claw stuff im gonna cry :-;
                //Make this one the open claw, DIS OPEN CLAW UWU
                bobHardware.setClawPosition(0.5);
            }
            //jesse, jesse we need a setClawPosition. JESSE!
            //Make this on close the claw, just like me closed off to commitment
            while(gamepad1.b) {
                bobHardware.setClawPosition(0);
            }
            if(gamepad1.right_bumper){
                currentSensitivity = turboSensitivity;
            }
            else if(gamepad1.left_bumper){
                currentSensitivity = slowSensitivity;
            }
            else {
                currentSensitivity = defaultSensitivity;
            }
//            telemetry.addData("Left Front", lf.getPower());
//            telemetry.addData("Right Front", rf.getPower());
//            telemetry.addData("Left Back", lb.getPower());
//            telemetry.addData("Right Back", rb.getPower());
//            telemetry.addData("Claw Position", claw.getPosition());
//            telemetry.addData("a button" , gamepad1.a);
//            telemetry.addData("b button" , gamepad1.b);
//            telemetry.update();
        }
    }
}