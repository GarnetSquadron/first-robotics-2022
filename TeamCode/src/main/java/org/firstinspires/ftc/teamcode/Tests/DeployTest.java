package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.Intake;

@TeleOp(name = "Claw Deploy Test",group = "test")
@Disabled
public class DeployTest extends OpMode {
    Intake claw;
    @Override
    public void init() {
        claw = new Intake(hardwareMap);
    }

    @Override
    public void loop() {
        if(gamepad1.x){
            claw.deploy(1);
        }
        if(gamepad1.y) {
            claw.undeploy();
        }
    }
}
