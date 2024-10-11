package org.firstinspires.ftc.teamcode;
import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ftc.LynxFirmware;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.stream.CameraStreamSource;
import org.firstinspires.ftc.teamcode.SampleDetectionPipelinePNP;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Point3;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;
import java.util.ArrayList;
public class Vision {
    OpenCvWebcam webcam1 = null;
    //public OpenCvCamera cam1;
    public CameraStreamSource camera;
    HardwareMap hardwareMap;
    Telemetry telemetry;
    public SampleDetectionPipelinePNP SamplePipeline = new SampleDetectionPipelinePNP();
    public Vision(HardwareMap hardwareMap, Telemetry t){
        this.hardwareMap = hardwareMap;
        telemetry=t;
    }
    public void InitPipeline(){
        WebcamName webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "Id", hardwareMap.appContext.getPackageName());
        webcam1 = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);

        webcam1.setPipeline(SamplePipeline);

        webcam1.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam1.startStreaming(640, 360,OpenCvCameraRotation.UPRIGHT);
                telemetry.addLine("SUCCESSFULLY OPENED CAM =D");
                telemetry.update();
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addLine("FAILED TO OPEN CAM =(");
                telemetry.update();
            }
        });
    }
    public void Calibrate(){

    }
    public ArrayList<SampleDetectionPipelinePNP.AnalyzedStone> GetSampleList(){
        return SamplePipeline.getDetectedStones();
    }
    public double getDistanceAway(int i){
        SampleDetectionPipelinePNP.AnalyzedStone p = GetSampleList().get(i);
        return Math.hypot(Math.hypot(p.getX(),p.getY()),p.getZ());
    }
    public SampleDetectionPipelinePNP.AnalyzedStone GetNearestSample(){
        ArrayList<SampleDetectionPipelinePNP.AnalyzedStone> SampleList = GetSampleList();
        double MinDistance = getDistanceAway(0);
        int index=0;
        for(int i=1; i<SampleList.size(); i++){
            double d = getDistanceAway(i);
            if(d>=MinDistance){
                MinDistance = d;
                index = i;
            }
        }
        return SampleList.get(index);
    }
}
