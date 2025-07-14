package org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff.cv;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.stream.CameraStreamSource;
import org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff.cv.Pipelines.SampleDetectionPipelineAngledCam;
import org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff.cv.Pipelines.SamplePipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

import java.util.ArrayList;

public class Vision
{
    OpenCvWebcam webcam1 = null;
    //public OpenCvCamera cam1;
    public CameraStreamSource camera;
    HardwareMap hardwareMap;
    Telemetry telemetry;
    WebcamName webcamName;
    boolean streamOpened = false;
    Servo s;

    public Vision(HardwareMap hardwareMap, Telemetry t)
    {
        this.hardwareMap = hardwareMap;
        telemetry = t;
        webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "Id", hardwareMap.appContext.getPackageName());
        webcam1 = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);

        webcam1.setPipeline(Pipeline);
    }

    public SampleDetectionPipelineAngledCam Pipeline = new SampleDetectionPipelineAngledCam();

    public void InitPipeline()
    {
        webcam1.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                webcam1.startStreaming(1280, 720, OpenCvCameraRotation.UPRIGHT);
                FtcDashboard.getInstance().startCameraStream(webcam1, 0);
                telemetry.addLine("SUCCESSFULLY OPENED CAM =D");
                telemetry.update();
                streamOpened = true;
            }

            @Override
            public void onError(int errorCode)
            {
                telemetry.addLine("FAILED TO OPEN CAM =(");
                telemetry.update();
            }
        });
    }

    public void closeCam()
    {
        webcam1.stopStreaming();
        streamOpened = false;
    }

    public void Calibrate()
    {

    }

    public ArrayList<SamplePipeline.AnalyzedStone> GetSampleList()
    {
        return Pipeline.getDetectedStones();
    }

    public ArrayList<SamplePipeline.AnalyzedStone> GetListWithColor(String color)
    {
        ArrayList<SamplePipeline.AnalyzedStone> SampleList = GetSampleList();
        ArrayList<SamplePipeline.AnalyzedStone> n = new ArrayList<>();
        for (SamplePipeline.AnalyzedStone Sample : SampleList) {
            if (Sample.getColor() == color) {
                n.add(Sample);
            }
        }
        return n;
    }

    public double getDistanceAway(int i, ArrayList<SamplePipeline.AnalyzedStone> SampleList)
    {
        SamplePipeline.AnalyzedStone p;
        if (SampleList.size() > i && i >= 0) {
            p = SampleList.get(i);
            return Math.hypot(p.getPos().x, p.getPos().y);
        }
        return -1;//if i is out of bounds, return a number that is not possible
    }

    public SamplePipeline.AnalyzedStone getNearestSample()
    {

        ArrayList<SamplePipeline.AnalyzedStone> SampleList = GetSampleList();
        if (SampleList.size() == 0) {
            return null;
        }
        double MinDistance = getDistanceAway(0, SampleList);
        int index = 0;
        for (int i = 1; i < SampleList.size(); i++) {
            double d = getDistanceAway(i, SampleList);
            if (d >= MinDistance) {
                MinDistance = d;
                index = i;
            }
        }
        return SampleList.get(index);
    }

    public void setAngle(double angle)
    {
        Pipeline.setAngle(angle);
    }

    public double getBrightness()
    {
        return Pipeline.brightness;
    }
}
