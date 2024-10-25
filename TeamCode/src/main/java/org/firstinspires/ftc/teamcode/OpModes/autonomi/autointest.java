package org.firstinspires.ftc.teamcode.OpModes.autonomi;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

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

        if blue||Yellow {
            Ti.setPower(-power);
            Fi.setPower(0);
            Bi.setPower(+power);
        }
        if red {
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
        @Override
        public void runOpMode(){
            WebcamName webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");
            int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "Id", hardwareMap.appContext.getPackageName());
            webcam1 = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);

            webcam1.setPipeline(new SampleDetectionPipelinePNP());

            webcam1.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
                @Override
                public void onOpened() {
                    webcam1.startStreaming(640, 360,OpenCvCameraRotation.UPRIGHT);
                }

                @Override
                public void onError(int errorCode) {
                    telemetry.addLine("unable to open camera :(");
                    telemetry.update();
                }
            });



        }


    }
//commit testing