package org.firstinspires.ftc.teamcode;
import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Autonomous(name="ColorSensor")
//todo: I, Bass, want this to score one cone. This is a stretch goal, but a important one. After color sensors
public class colorSensorAuto extends LinearOpMode {
    private TerryHardware terryHardware;
    public void runOpMode(){
        ColorSensor colorSensor;
        colorSensor = hardwareMap.colorSensor.get("color");

        terryHardware = new TerryHardware(hardwareMap);
        terryHardware.initHardware();
        //waitForStart, so we don't move before dictated time.
        waitForStart();
        //Makes robot move backwards, this gets the color sensor on the back up close to the cone.
        terryHardware.driveRobot(-0.75,0,0);
        sleep(1300);
        //stops the robot before running the color checks
        terryHardware.driveRobot(0,0,0);
        sleep(700);
        //Checks for color red, if red is detected above a value of 100, it strafes left
        if(colorSensor.red() > 100){
            terryHardware.driveRobot(0, 0.5,0);
            //todo: find out how many milliseconds it must drive sideways. also find out how much the green must drive sideways.
            sleep(500);
            terryHardware.driveRobot(0,0,0);
        }
        //Checks for color green, if green detected above value of 100, it strafes right
        else if(colorSensor.green() > 100){
            terryHardware.driveRobot(0,-0.5,0);
            //todo: ditto of above comment, find out the milliseconds it must sleep.
            sleep(500);
            terryHardware.driveRobot(0,0,0);
        }
        //Telemetry for debug
        //todo: delete telemetry before competition
        while (opModeIsActive()){
            telemetry.addData("Red",colorSensor.red());
            telemetry.addData("Green",colorSensor.green());
            telemetry.addData("Blue",colorSensor.blue());
            telemetry.addData("Alpha",colorSensor.alpha());
            telemetry.addData("ARGB", colorSensor.argb());
            telemetry.update();

        }
    }
}
