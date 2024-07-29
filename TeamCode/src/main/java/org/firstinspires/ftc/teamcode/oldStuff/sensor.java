package org.firstinspires.ftc.teamcode.oldStuff;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
//import com.google.blocks.ftcrobotcontroller.runtime.ColorRangeSensorAccess;
@Autonomous(name = "sensor")
public class sensor extends LinearOpMode {
    private DistanceSensor sensor;
    @Override
    public void runOpMode() throws InterruptedException {

        sensor=hardwareMap.get(DistanceSensor.class, "distance");
        while (!isStopRequested()) {
            telemetry.addData("Distance (cm)", sensor.getDistance(DistanceUnit.CM));
            telemetry.update();
        }
    }
}
