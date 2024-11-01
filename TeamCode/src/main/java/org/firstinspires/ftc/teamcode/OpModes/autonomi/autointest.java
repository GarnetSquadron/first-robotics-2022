package org.firstinspires.ftc.teamcode.OpModes.autonomi;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.firstinspires.ftc.robotcore.external.stream.CameraStreamSource;
//import org.firstinspires.ftc.teamcode.SampleDetectionPipelinePNP;
//import org.openftc.easyopencv.OpenCvCamera;
//import org.openftc.easyopencv.OpenCvCameraFactory;
//import org.openftc.easyopencv.OpenCvCameraRotation;
//import org.openftc.easyopencv.OpenCvWebcam;

@TeleOp(name="auto intake test", group = "test")

//imports from vision.java.

    public class autointest extends LinearOpMode {
    CRServo Ti;
    CRServo Fi;
    CRServo Bi;
    ColorSensor cSensor;

    public void intake() {
        Ti.setPower(0);
        Fi.setPower(-1);
        Bi.setPower(+1);
    }

    public void eject() {
        Ti.setPower(+1);
        Fi.setPower(-1);
        Bi.setPower(0);
    }

    public void hold() {
        Ti.setPower(0);
        Fi.setPower(0);
        Bi.setPower(0);
    }

//    public void send() {
//        Ti.setPower(-1);
//        Fi.setPower(0);
//        Bi.setPower(+1);
//    }


    public void Onstart(){

        intake();

            while(opModeIsActive()){
                telemetry.addData("red", cSensor.red());
                telemetry.addData("green", cSensor.green());
                telemetry.addData("blue", cSensor.blue());
                telemetry.update();


                double HighestColorValue = Math.max(Math.max(cSensor.red(), cSensor.green()),cSensor.blue());
                boolean result = false;
                if (HighestColorValue < 250)
                    telemetry.addLine("No Color");
                else if (cSensor.red() > cSensor.green() && cSensor.red() > cSensor.blue()) {
                    telemetry.addLine("red");
                } else if (cSensor.green() > cSensor.red() && cSensor.green() > cSensor.blue()) {
                    telemetry.addLine("yellow");
                    result = true;
                } else if (cSensor.blue() > cSensor.red() && cSensor.blue() > cSensor.green()){
                    telemetry.addLine("blue");
                    result = true;
                }
                telemetry.update();



                if(result) {
                    hold();

//                //run some rotation code for main arm servo here
//
//                sleep(1000);
//
//                Ti.setPower(-1);
//                Fi.setPower(0);
//                Bi.setPower(+1);
//
//                //run some rotation code for main arm servo here
//
//                sleep (1000);
//
//                Ti.setPower(0);
//                Fi.setPower(-1);
//                Bi.setPower(+1);
                }


                if(!result) {
                    eject();
                    sleep(1500);
                    intake();
                }
            }
        }
    public void runOpMode(){
        cSensor = hardwareMap.get(ColorSensor.class, "ColorSensor");
        Ti = hardwareMap.get(CRServo.class,"IntakeServo1");
        Fi = hardwareMap.get(CRServo.class,"IntakeServo2");
        Bi = hardwareMap.get(CRServo.class,"IntakeServo3");
        waitForStart();
        Onstart();

    }
    }





