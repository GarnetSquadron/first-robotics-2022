package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.stream.CameraStreamSource;
import org.firstinspires.ftc.teamcode.Pipelines.SampleDetectionPipelineAngledCam;
import org.firstinspires.ftc.teamcode.Pipelines.SampleDetectionPipelinePNP;
import org.firstinspires.ftc.teamcode.Pipelines.SamplePipeline.AnalyzedStone;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;
import java.util.ArrayList;
public class Vision {
    OpenCvWebcam webcam1 = null;
    //public OpenCvCamera cam1;
    public CameraStreamSource camera;
    HardwareMap hardwareMap;
    Telemetry telemetry;
    WebcamName webcamName;
    Servo s;
    public SampleDetectionPipelineAngledCam Pipeline = new SampleDetectionPipelineAngledCam();
    public Vision(HardwareMap hardwareMap, Telemetry t){
        this.hardwareMap = hardwareMap;
        telemetry=t;
    }
    public void InitPipeline(HardwareMap hardwareMap){
        webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "Id", hardwareMap.appContext.getPackageName());
        webcam1 = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);

        webcam1.setPipeline(Pipeline);

        webcam1.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam1.startStreaming(640, 360,OpenCvCameraRotation.UPRIGHT);
                FtcDashboard.getInstance().startCameraStream(webcam1,0);
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
    public ArrayList<AnalyzedStone> GetSampleList(){
        return Pipeline.getDetectedStones();
    }
    public double getDistanceAway(int i, ArrayList<AnalyzedStone> SampleList){
        AnalyzedStone p;
        if(SampleList.size()>i) {
            p = SampleList.get(i);
            return Math.hypot(p.getPos().x,p.getPos().y);
        }
        return -1;
    }
    public AnalyzedStone getNearestSample(){

        ArrayList<AnalyzedStone> SampleList = GetSampleList();
        if(SampleList.size()==0){
            return null;
        }
        double MinDistance = getDistanceAway(0,SampleList);
        int index=0;
        for(int i=1; i<SampleList.size(); i++){
            double d = getDistanceAway(i,SampleList);
            if(d>=MinDistance){
                MinDistance = d;
                index = i;
            }
        }
        return SampleList.get(index);
    }
}