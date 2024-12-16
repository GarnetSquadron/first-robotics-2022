package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.outake.Outtake;

@TeleOp(name = "outtake test")
public class outtakeTest extends OpMode {
    Outtake outtake;
    @Override
    public void init() {
        outtake = new Outtake(hardwareMap);
    }

    @Override
    public void loop() {
        if(gamepad1.x){
            outtake.BucketPos();
        }
        if (gamepad1.y){
            outtake.TransferPos();
        }
        if (gamepad1.a){
            outtake.claw.open();
        }
        if (gamepad1.b){
            outtake.claw.close();
        }
    }
}
