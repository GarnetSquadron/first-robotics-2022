package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.ExtraMath;
import org.firstinspires.ftc.teamcode.ValueAtTimeStamp;
import org.firstinspires.ftc.teamcode.time.TIME;


@TeleOp(name = "acceleration test", group = "tests")
@Disabled
public class AccelerationTest extends LinearOpMode
{
    //RAWMOTOR motor;
    DcMotorEx motor;
    ValueAtTimeStamp prevVel = new ValueAtTimeStamp(0, 0);

    @Override
    public void runOpMode() throws InterruptedException
    {
        motor = hardwareMap.get(DcMotorEx.class, "Primary Pivot");
        waitForStart();
        motor.setPower(1);
        sleep(100);
        while (opModeIsActive()) {
            telemetry.addData("velocity", motor.getVelocity());
            if (motor.getVelocity() != 0) {
                telemetry.addData("acceleration", ExtraMath.getAverageTimeDerivative(prevVel, new ValueAtTimeStamp(motor.getVelocity(), TIME.getTime())));
                prevVel = new ValueAtTimeStamp(motor.getVelocity(), TIME.getTime());
                motor.setPower(1);
            } else {
                motor.setPower(0);
                break;
            }
            telemetry.update();
            sleep(1);
        }
    }
}
