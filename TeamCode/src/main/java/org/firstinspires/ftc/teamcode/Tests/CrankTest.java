package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.CrankSlideSubSystem;


@TeleOp(name = "crankTest", group = "tests")
public class CrankTest extends OpMode {
    CrankSlideSubSystem crank;
    @Override
    public void init() {
        crank = new CrankSlideSubSystem(hardwareMap);
    }

    @Override
    public void loop() {
        if(gamepad1.x){
            crank.undeploy();
        }
        if(gamepad1.y){
            crank.deploy();
        }
    }
}
