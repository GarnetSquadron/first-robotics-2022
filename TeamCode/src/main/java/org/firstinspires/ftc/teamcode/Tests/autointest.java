package org.firstinspires.ftc.teamcode.Tests;

import static org.firstinspires.ftc.teamcode.Tests.autointest.State.EJECTING;
import static org.firstinspires.ftc.teamcode.Tests.autointest.State.HOLDING;
import static org.firstinspires.ftc.teamcode.Tests.autointest.State.INTAKING;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.Subsystems.TriangleIntake;
import org.firstinspires.ftc.teamcode.Subsystems.CrankSlideSubSystem;
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.firstinspires.ftc.robotcore.external.stream.CameraStreamSource;
//import org.firstinspires.ftc.teamcode.Pipelines.SampleDetectionPipelinePNP;
//import org.openftc.easyopencv.OpenCvCamera;
//import org.openftc.easyopencv.OpenCvCameraFactory;
//import org.openftc.easyopencv.OpenCvCameraRotation;
//import org.openftc.easyopencv.OpenCvWebcam;

@TeleOp(name="auto intake test", group = "test")

//imports from vision.java.

public class autointest extends LinearOpMode {
    TriangleIntake triangleIntake = new TriangleIntake(hardwareMap,"IntakeServo1", "IntakeServo2", "IntakeServo3","pivot");
    CrankSlideSubSystem crankSlideSubSystem = new CrankSlideSubSystem(hardwareMap, "CrankL","CrankR");
    CRServo Ti;
    CRServo Fi;
    CRServo Bi;
    ColorSensor cSensor;

    public enum State{
        INTAKING,
        EJECTING,
        HOLDING
    }
    public void Onstart(){

        triangleIntake.intake();

            while(opModeIsActive()){
                telemetry.addData("red", cSensor.red());
                telemetry.addData("green", cSensor.green());
                telemetry.addData("blue", cSensor.blue());

                double HighestColorValue = Math.max(Math.max(cSensor.red(), cSensor.green()),cSensor.blue());
                State result = INTAKING;
                if (HighestColorValue < 200) {
                    telemetry.addLine("No Color");
                }
                else if (cSensor.red() > cSensor.green() && cSensor.red() > cSensor.blue()) {
                    telemetry.addLine("red");
                    result = EJECTING;
                } else if (cSensor.green() > cSensor.red() && cSensor.green() > cSensor.blue()) {
                    telemetry.addLine("yellow");
                    result = HOLDING;
                } else if (cSensor.blue() > cSensor.red() && cSensor.blue() > cSensor.green()){
                    telemetry.addLine("blue");
                    result = HOLDING;
                }else{
                    telemetry.addLine("Else has been reached");
                }

                if(result == HOLDING) {

                triangleIntake.hold();

                crankSlideSubSystem.Return();

                triangleIntake.send();

                crankSlideSubSystem.Extend();

                }

                else if(result == EJECTING) {

                    long duration = 1500;
                    long startTime = System.currentTimeMillis();

                    while (System.currentTimeMillis() - startTime < duration) {

                        triangleIntake.eject();

                    }
                }

                else{
                    triangleIntake.intake();

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

                    



