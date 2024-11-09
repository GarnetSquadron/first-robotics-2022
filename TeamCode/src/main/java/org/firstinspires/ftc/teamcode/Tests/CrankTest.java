package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Subsystems.CrankSlideSubSystem;


public class CrankTest extends OpMode {
    CrankSlideSubSystem crank;
    @Override
    public void init() {
        crank = new CrankSlideSubSystem(hardwareMap,"CrankLeft","CrankLeft");
    }

    @Override
    public void loop() {
        if(gamepad1.x){
            crank.Extend();
        }
        if(gamepad2.y){
            crank.Return();
        }
    }
}
