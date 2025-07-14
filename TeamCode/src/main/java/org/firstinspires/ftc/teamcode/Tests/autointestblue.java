package org.firstinspires.ftc.teamcode.Tests;

//import static org.firstinspires.ftc.teamcode.Tests.autointestblue.State.EJECTING;
//import static org.firstinspires.ftc.teamcode.Tests.autointestblue.State.HOLDING;
//import static org.firstinspires.ftc.teamcode.Tests.autointestblue.State.INTAKING;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.TriangleIntakeCommand;
import org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff.TriangleIntake.ColorSensorSubSystem;
import org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff.TriangleIntake.TriangleIntake;
import org.firstinspires.ftc.teamcode.enums.Color;
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.firstinspires.ftc.robotcore.external.stream.CameraStreamSource;
//import org.firstinspires.ftc.teamcode.Pipelines.SampleDetectionPipelinePNP;
//import org.openftc.easyopencv.OpenCvCamera;
//import org.openftc.easyopencv.OpenCvCameraFactory;
//import org.openftc.easyopencv.OpenCvCameraRotation;
//import org.openftc.easyopencv.OpenCvWebcam;


@TeleOp(name = "auto intake test blue", group = "test")
@Disabled

//imports from vision.java.


public class autointestblue extends LinearOpMode
{
    TriangleIntake triangleIntake;
    //CrankSlideSubSystem crankSlideSubSystem = new CrankSlideSubSystem(hardwareMap, "CrankL","CrankR");
    ColorSensorSubSystem cSensor;
    TriangleIntakeCommand triangleIntakeCommand;

    public enum State
    {
        INTAKING,
        EJECTING,
        HOLDING
    }

    public void Onstart()
    {
        while (!triangleIntakeCommand.isFinished() && opModeIsActive()) {
            triangleIntakeCommand.execute();
        }
    }

    public void runOpMode()
    {
        cSensor = new ColorSensorSubSystem(hardwareMap, "ColorSensor");
        triangleIntake = new TriangleIntake(hardwareMap, "Ti", "Fi", "Bi", "pivot");
        triangleIntakeCommand = new TriangleIntakeCommand(triangleIntake, cSensor, Color.BLUE, telemetry);
        waitForStart();
        Onstart();
    }
}


