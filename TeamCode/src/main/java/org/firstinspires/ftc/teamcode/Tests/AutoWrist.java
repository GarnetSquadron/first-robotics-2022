package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff.cv.Pipelines.SamplePipeline.AnalyzedStone;
import org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff.cv.Vision;

@TeleOp(name = "AutoWristTest", group = "tests")
@Disabled
public class AutoWrist extends LinearOpMode
{
    Servo wrist;
    Vision vision = new Vision(hardwareMap, telemetry);

    @Override
    public void runOpMode()
    {
        wrist = hardwareMap.get(Servo.class, "wrist");
        vision.InitPipeline();
        waitForStart();
        while (opModeIsActive()) {
            AnalyzedStone Sample = vision.getNearestSample();
            if (Sample != null) {
                double pos = (Sample.getAngleRad()) / (2 * Math.PI);
                //adjust for a possible 180 degree rotation
                if (pos + 0.5 < wrist.getPosition()) {
                    pos += 0.5;
                } else if (pos - 0.5 > wrist.getPosition()) {
                    pos -= 0.5;
                }
                double tolerance = 0.01;
                if (Math.abs(pos - wrist.getPosition()) > tolerance) {
                    wrist.setPosition(pos);
                }
                telemetry.addData("rotation", wrist.getPosition());
                telemetry.update();
            }
        }
    }
}
