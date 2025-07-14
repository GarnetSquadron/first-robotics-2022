package org.firstinspires.ftc.teamcode.Tests;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.time.TIME;

@TeleOp(name = "delay test")
@Disabled
public class SensorDelayTest extends OpMode
{
    DistanceSensor distanceSensor;
    Motor encoder;

    @Override
    public void init()
    {
        distanceSensor = hardwareMap.get(Rev2mDistanceSensor.class, "viper distance sensor");
        encoder = new Motor(hardwareMap, "LeftViper");
    }

    double sensorTime, encoderTime, sensorDistance, encoderDistance;

    @Override
    public void loop()
    {
        telemetry.addData("distance difference", sensorDistance - distanceSensor.getDistance(DistanceUnit.INCH));
        sensorTime = TIME.getTime();
        sensorDistance = distanceSensor.getDistance(DistanceUnit.INCH);
        telemetry.addData("time", TIME.getTime() - sensorTime);
        encoderTime = TIME.getTime();
        encoderDistance = encoder.getDistance();
        telemetry.addData("encoder time", TIME.getTime() - encoderTime);
        telemetry.update();
    }
}
