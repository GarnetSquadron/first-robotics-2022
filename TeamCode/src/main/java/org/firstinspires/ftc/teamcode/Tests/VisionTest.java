package org.firstinspires.ftc.teamcode.Tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardwareClasses.ActionServo;
import org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff.cv.Pipelines.SamplePipeline;
import org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff.cv.Vision;

@TeleOp(name = "VisionTest", group = "test")
@Disabled
public class VisionTest extends LinearOpMode
{

    Vision vision = new Vision(hardwareMap, telemetry);
    ActionServo servo;

    @Override
    public void runOpMode()
    {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        TelemetryPacket packet = new TelemetryPacket();
        vision.InitPipeline();
        servo = new ActionServo(hardwareMap, "servo", 0, 1, this::getRuntime);
        waitForStart();
        String JankTelemetry;//ftc dash doesnt like telemetry.clear() for some reason, so Im doing this
        while (opModeIsActive()) {
//            if(vision.GetSampleList().size()>0) {
//                JankTelemetry = "Sample detected";
//                telemetry.addData("Screen Coords y",  vision.getNearestSample().getCoordsOnScreen().y);
//                telemetry.addData("Screen Coords x", vision.getNearestSample().getCoordsOnScreen().x);
//                telemetry.addData("angle", vision.getNearestSample().getAngleRad());
//                telemetry.addData("distance away",vision.getNearestSample().getPos().y);
//
//            }
//            else {
//                JankTelemetry = "no sample detected :(";
//            }
            //servo.goToRatio(gamepad1.left_stick_x*0.5+0.5);
            servo.runToRatio(0.25).run(packet);
            double angle = Math.PI / 2 - servo.getPos() * Math.PI;
            vision.setAngle(angle);
            telemetry.addData("cam angle", angle);
            double i = 0;
            for (SamplePipeline.AnalyzedStone Sample : vision.GetListWithColor("Red")) {
                i++;

                telemetry.addData("Screen Coords y " + i, Sample.getCoordsOnScreen().y);
                telemetry.addData("Screen Coords x " + i, Sample.getCoordsOnScreen().x);
                telemetry.addData("angle " + i, Sample.getAngleRad());
                telemetry.addData("real x " + i, Sample.getPos().x);
                telemetry.addData("real y " + i, Sample.getPos().y);
                telemetry.addData("color " + i, Sample.getColor());
                telemetry.addData("width " + i, Sample.width);
                telemetry.addData("length" + i, Sample.length);
            }
            telemetry.addData("brightness", vision.getBrightness());
            // telemetry.addData("", JankTelemetry);


            telemetry.update();

        }
    }
}
