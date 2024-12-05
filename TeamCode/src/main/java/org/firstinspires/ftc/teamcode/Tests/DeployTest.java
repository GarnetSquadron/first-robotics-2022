package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.commands.CrankAndPivotClaw;

public class DeployTest extends OpMode {
    CrankAndPivotClaw claw;
    @Override
    public void init() {
        claw = new CrankAndPivotClaw(hardwareMap);
    }

    @Override
    public void loop() {
        if(gamepad1.x){
            claw.deploy(1);
        }
        else {
            claw.undeploy();
        }
    }
}
