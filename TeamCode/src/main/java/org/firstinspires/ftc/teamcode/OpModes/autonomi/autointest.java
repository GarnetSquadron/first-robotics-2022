package org.firstinspires.ftc.teamcode.OpModes.autonomi;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.stream.CameraStreamSource;
import org.firstinspires.ftc.teamcode.SampleDetectionPipelinePNP;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

@TeleOp(name="auto intake test", group = "test")

//imports from vision.java

    public class autointest extends LinearOpMode {
        OpenCvWebcam webcam1 = null;
        //public OpenCvCamera cam1;
        public CameraStreamSource camera;
        public SampleDetectionPipelinePNP SamplePipeline = new SampleDetectionPipelinePNP();
    CRServo Ti;
    CRServo Fi;
    CRServo Bi;
    public void Onstart(){

        Ti.setPower(0);
        Fi.setPower(-power);
        Bi.setPower(+power);

        if (blue||yellow) {
            Ti.setPower(0);
            Fi.setPower(0);
            Bi.setPower(0);

    //run some rotation code for main arm servo here

            sleep(1000);
            Ti.setPower(-power);
            Fi.setPower(0);
            Bi.setPower(+power);
        }
        if (toString().equals("red")) {
            Ti.setPower(+power);
            Fi.setPower(-power);
            Bi.setPower(0);
        }
        else{
            Ti.setPower(0);
            Fi.setPower(-power);
            Bi.setPower(+power);
        }
    }

    public class ColorSensorTuning extends LinearOpMode {
        ColorSensor cSensor;
        public void runOpMode(){
            cSensor = hardwareMap.get(ColorSensor.class, "ColorSensor");
            waitForStart();

            while(opModeIsActive()){
                telemetry.addData("red", cSensor.red());
                telemetry.addData("green", cSensor.green());
                telemetry.addData("blue", cSensor.blue());
                telemetry.update();


                double HighestColorValue = Math.max(Math.max(cSensor.red(), cSensor.green()),cSensor.blue());

                if (HighestColorValue < 200)
                    telemetry.addLine("No Color");
                else if (cSensor.red() > cSensor.green() && cSensor.red() > cSensor.blue()) {
                    telemetry.addLine("red");
                } else if (cSensor.green() > cSensor.red() && cSensor.green() > cSensor.blue()) {
                    telemetry.addLine("yellow");
                } else if (cSensor.blue() > cSensor.red() && cSensor.blue() > cSensor.green())
                    telemetry.addLine("blue");
                telemetry.update();}
             }
        }
    }


