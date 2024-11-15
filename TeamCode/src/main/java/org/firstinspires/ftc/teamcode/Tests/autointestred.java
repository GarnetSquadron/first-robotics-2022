package org.firstinspires.ftc.teamcode.Tests;


//import static org.firstinspires.ftc.teamcode.Tests.autointestred.State.EJECTING;
//import static org.firstinspires.ftc.teamcode.Tests.autointestred.State.HOLDING;
//import static org.firstinspires.ftc.teamcode.Tests.autointestred.State.INTAKING;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.CRServo;
//import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.Subsystems.ColorSensorSubSystem;
import org.firstinspires.ftc.teamcode.Subsystems.TriangleIntake;
import org.firstinspires.ftc.teamcode.commands.TriangleIntakeCommand;
import org.firstinspires.ftc.teamcode.enums.Color;
//import org.firstinspires.ftc.teamcode.Subsystems.CrankSlideSubSystem;
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.firstinspires.ftc.robotcore.external.stream.CameraStreamSource;
//import org.firstinspires.ftc.teamcode.Pipelines.SampleDetectionPipelinePNP;
//import org.openftc.easyopencv.OpenCvCamera;
//import org.openftc.easyopencv.OpenCvCameraFactory;
//import org.openftc.easyopencv.OpenCvCameraRotation;
//import org.openftc.easyopencv.OpenCvWebcam;

@TeleOp(name="auto intake test red", group = "test")

public class autointestred extends LinearOpMode {
    TriangleIntake triangleIntake = new TriangleIntake(hardwareMap,"IntakeServo1", "IntakeServo2", "IntakeServo3","pivot");
    //  CrankSlideSubSystem crankSlideSubSystem = new CrankSlideSubSystem(hardwareMap, "CrankL","CrankR");
    ColorSensorSubSystem cSensor;
    TriangleIntakeCommand triangleIntakeCommand;

    public enum State{
        INTAKING,
        EJECTING,
        HOLDING
    }
    public void Onstart(){
        while(!triangleIntakeCommand.isFinished()){
            triangleIntakeCommand.execute();
        }
    }

    public void runOpMode(){
        cSensor = new ColorSensorSubSystem(hardwareMap,"ColorSensor");
        triangleIntake = new TriangleIntake(hardwareMap,"IntakeServo1", "IntakeServo2", "IntakeServo3","pivot");
        triangleIntakeCommand = new TriangleIntakeCommand(triangleIntake, cSensor, Color.RED);
        waitForStart();
        Onstart();
    }
}



//commit