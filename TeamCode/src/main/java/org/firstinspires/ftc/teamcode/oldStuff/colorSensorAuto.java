package org.firstinspires.ftc.teamcode.oldStuff;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.JavaUtil;

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
        terryHardware.driveRobot(-0.5,0,0);
        sleep(800);
        //stops the robot before running the color checks
        terryHardware.driveRobot(0,0,0);
        sleep(700);
        //Checks for the hue of red, if it is detected, it stays in zone (2)
        if(JavaUtil.rgbToHue(colorSensor.red(),colorSensor.green(),colorSensor.blue())<30){
            terryHardware.driveRobot(-0.5,0,0);
            sleep(400);
            terryHardware.driveRobot(0,0,0);
        }
        //Checks for color green, if so, it strafes right
        else if(JavaUtil.rgbToHue(colorSensor.red(),colorSensor.green(),colorSensor.blue())<160 && JavaUtil.rgbToHue(colorSensor.red(),colorSensor.green(),colorSensor.blue())>120){
            terryHardware.driveRobot(-0.5,0,0);
            sleep(400);
            terryHardware.driveRobot(0,-0.5,0);
            //todo: ditto of below comment, find out the milliseconds it must sleep.
            sleep(1550);
            terryHardware.driveRobot(0,0,0);
        }
        //checks for the hue yellow, if detected, it will strafe left
        else if(JavaUtil.rgbToHue(colorSensor.red(),colorSensor.green(),colorSensor.blue())<100 && JavaUtil.rgbToHue(colorSensor.red(),colorSensor.green(),colorSensor.blue())>40){
            terryHardware.driveRobot(-0.5,0,0);
            sleep(400);
            terryHardware.driveRobot(0, 0.5,0);
            //todo: find out how many milliseconds it must drive sideways. also find out how much the green must drive sideways.
            sleep(1550);
            terryHardware.driveRobot(0,0,0);
        }
        else{
            terryHardware.driveRobot(-0.5,0,0);
            sleep(400);
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
            telemetry.addData("hue ig???", JavaUtil.rgbToHue(colorSensor.red(),colorSensor.green(),colorSensor.blue()));
            telemetry.update();

        }
    }
}
