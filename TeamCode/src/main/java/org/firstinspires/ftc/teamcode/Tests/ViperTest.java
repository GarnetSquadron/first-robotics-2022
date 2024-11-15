package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.ViperSlidesSubSystem;

@TeleOp(name = "ViperTest", group = "tests")
public class ViperTest extends OpMode {
    ViperSlidesSubSystem viperSlides;
    @Override
    public void init() {
        viperSlides = new ViperSlidesSubSystem(hardwareMap,"Viper1", "Viper2");
    }

    @Override
    public void loop() {
        if(gamepad1.y){
            viperSlides.SetPosExtend();
        }
        if(gamepad1.x){
            viperSlides.SetPosReturn();
        }
        viperSlides.runToTgPos();
    }
}
