package org.firstinspires.ftc.teamcode.Tests;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


import org.firstinspires.ftc.teamcode.Subsystems.outake.PrimaryOuttakePivot;


@TeleOp(name="OuttakePivotTest", group = "test")

public class OuttakePivotTest extends OpMode {
     PrimaryOuttakePivot ServoAlignment;

     @Override
    public void loop() {
        if (gamepad1.y) {
            ServoAlignment.Up();
        }
        if (gamepad1.x) {
            ServoAlignment.Down();
        }
    }
    public void init(){
        ServoAlignment = new PrimaryOuttakePivot(hardwareMap);
    }
}
//commit