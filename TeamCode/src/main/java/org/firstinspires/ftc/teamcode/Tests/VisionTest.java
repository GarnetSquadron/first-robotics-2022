package org.firstinspires.ftc.teamcode.Tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Pipelines.SamplePipeline;
import org.firstinspires.ftc.teamcode.Subsystems.ServoSub;
import org.firstinspires.ftc.teamcode.Subsystems.Vision;

@TeleOp(name = "VisionTest",group = "test")
public class VisionTest extends LinearOpMode {

    Vision vision = new Vision(hardwareMap,telemetry);
    ServoSub servo;

    @Override
    public void runOpMode(){
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        vision.InitPipeline();
        servo = new ServoSub(hardwareMap,"servo",0,1);
        waitForStart();
        String JankTelemetry;//ftc dash doesnt like telemetry.clear() for some reason, so Im doing this
        while (opModeIsActive()){
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
            servo.goToRatio(0.25);
            double angle = Math.PI/2-servo.getPos()*Math.PI;
            vision.setAngle(angle);
            telemetry.addData("cam angle",angle);
            double i = 0;
            for(SamplePipeline.AnalyzedStone Sample:vision.GetListWithColor("Red")){
                i++;

                telemetry.addData("Screen Coords y "+i,  Sample.getCoordsOnScreen().y);
                telemetry.addData("Screen Coords x "+i, Sample.getCoordsOnScreen().x);
                telemetry.addData("angle "+i, Sample.getAngleRad());
                telemetry.addData("real x "+i,Sample.getPos().x);
                telemetry.addData("real y "+i,Sample.getPos().y);
                telemetry.addData("color "+i,Sample.getColor());
                telemetry.addData("width "+i,Sample.width);
                telemetry.addData("length"+i,Sample.length);
            }
            telemetry.addData("brightness", vision.getBrightness());
           // telemetry.addData("", JankTelemetry);



            telemetry.update();

        }
    }
}
