package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.SampleDetectionPipelinePNP;
import org.firstinspires.ftc.teamcode.Vision;

@TeleOp(name = "VisionTest",group = "test")
public class VisionTest extends LinearOpMode {
    Vision vision = new Vision(hardwareMap,telemetry);

    @Override
    public void runOpMode(){
        vision.InitPipeline();
        waitForStart();
    }
}
