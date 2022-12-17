package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "\uD83E\uDD5A")
public class BobOmni extends LinearOpMode {
   private BobHardware bobHardware;

    @Override
    public void runOpMode() {
      bobHardware.init();
        waitForStart();
        while (opModeIsActive()) {
            //yoink mcshloink
            bobHardware.driveRobot(-gamepad1.left_stick_y,gamepad1.left_stick_x,gamepad1.right_stick_x);

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