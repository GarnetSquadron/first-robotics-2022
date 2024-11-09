package org.firstinspires.ftc.teamcode.OpModes.autonomi;

import static org.firstinspires.ftc.teamcode.OpModes.autonomi.autointest.State.EJECTING;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.autointest.State.HOLDING;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.autointest.State.INTAKING;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.Subsystems.TriangleIntake;
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
    TriangleIntake triangleIntake = new TriangleIntake(hardwareMap,"IntakeServo1", "IntakeServo2", "IntakeServo3");

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
                if (HighestColorValue < 250) {
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


                if(result==HOLDING) {

                    triangleIntake.hold();

//                //run some rotation code for main arm servo here
//
//                sleep(1000);
//
//                send();
//
//                sleep(1000);
//
//                hold()
//
//                //run some rotation code for main arm servo here
//
//                sleep (1000);
                }

                else if(result ==EJECTING) {
                    triangleIntake.eject();
                }
                else{
                    triangleIntake.intake();
                }
                telemetry.addData("TI power",Ti.getPower());
                telemetry.update();
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





