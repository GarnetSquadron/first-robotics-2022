package org.firstinspires.ftc.teamcode.Tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Vision;

@TeleOp(name = "VisionTest",group = "test")
public class VisionTest extends LinearOpMode {

    Vision vision = new Vision(hardwareMap,telemetry);

    @Override
    public void runOpMode(){
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        vision.InitPipeline(hardwareMap);
        waitForStart();
        while (opModeIsActive()){
            telemetry.addData("coords", vision.Pipeline.RedCoords);
            telemetry.update();
        }
    }
}
