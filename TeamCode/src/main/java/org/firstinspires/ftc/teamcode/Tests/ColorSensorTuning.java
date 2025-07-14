package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

@TeleOp(name = "ColorSensorTuning", group = "test")
@Disabled
public class ColorSensorTuning extends LinearOpMode
{
    ColorSensor cSensor;

    public void runOpMode()
    {
        cSensor = hardwareMap.get(ColorSensor.class, "ColorSensor");
        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("red", cSensor.red());
            telemetry.addData("green", cSensor.green());
            telemetry.addData("blue", cSensor.blue());
            telemetry.update();


            double HighestColorValue = Math.max(Math.max(cSensor.red(), cSensor.green()), cSensor.blue());

            if (HighestColorValue < 200)
                telemetry.addLine("No Color");
                //To detect red -
            else if (cSensor.red() > cSensor.green() && cSensor.red() > cSensor.blue()) {
                telemetry.addLine("red");
                //To detect yellow -
            } else if (cSensor.green() > cSensor.red() && cSensor.green() > cSensor.blue()) {
                telemetry.addLine("yellow");
                //To detect blue -
            } else if (cSensor.blue() > cSensor.red() && cSensor.blue() > cSensor.green())
                telemetry.addLine("blue");
            telemetry.update();
        }

    }

}
