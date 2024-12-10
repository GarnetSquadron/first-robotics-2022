package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.outake.ViperSlidesSubSystem;

@TeleOp(name = "ViperTest", group = "tests")
public class ViperTest extends OpMode {
    ViperSlidesSubSystem viperSlides;
    @Override
    public void init() {
        viperSlides = new ViperSlidesSubSystem(hardwareMap);
    }

    @Override
    public void loop() {
        if(gamepad1.y){
            viperSlides.SetTgPosToRetract();
        }
        if(gamepad1.x){
            viperSlides.SetTgPosToExtend();
        }
        viperSlides.runToTgPos();
    }
}
