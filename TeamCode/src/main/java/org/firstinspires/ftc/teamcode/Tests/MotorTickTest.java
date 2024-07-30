package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.NonDriveHardware;

@TeleOp(name="Motor Tick Test", group="tuning")
public class MotorTickTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        NonDriveHardware nonDriveHardware = new NonDriveHardware(hardwareMap, 0, 0, 0);
        waitForStart();
        while(opModeIsActive()){
            telemetry.addData("Lift ticks",NonDriveHardware.lift.getCurrentPosition());
            telemetry.addData("Arm ticks",NonDriveHardware.arm.getCurrentPosition());
            telemetry.update();

        }
    }
}
