package org.firstinspires.ftc.teamcode.Tests;


import static android.os.SystemClock.sleep;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.depricated.failedMotorClasses.DcMotorSub;
import org.firstinspires.ftc.teamcode.enums.AngleUnitV2;

@TeleOp(name = "AATuning pivot")
@Disabled
public class TuningPrimaryOuttakePivot extends OpMode
{
    DcMotorSub pivot;
    double ExtForceCoefficient = 0.1;
    double velCoefficient = 0;
    double posCoefficient = 0.004;
    double tolerance = 60;
    double maxPower = 1;

    @Override
    public void init()
    {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        pivot = new DcMotorSub(hardwareMap, "Primary Pivot", 0, 950, 0.004, 0, 0.5, tolerance);//min and max need to be tuned
        pivot.setExtTorqueFunction(theta -> -ExtForceCoefficient * Math.cos(theta));
        pivot.setPosition(0);
        //pivot.setDesiredNetTorqueFunction((x,v)->0.0);
    }

    @Override
    public void loop()
    {
        pivot.setTgPosTick(450);
        pivot.AccountForExtForces();
        pivot.setPower(0);
        if (gamepad1.a) {
            pivot.setPower(1);
        }
        if (gamepad1.b) {
            velCoefficient += 0.0005;
            sleep(200);
        }
        if (gamepad1.x) {
            velCoefficient -= 0.0005;
            sleep(200);
        }
        if (gamepad1.right_bumper) {
            posCoefficient += 0.0005;
            sleep(200);
        }
        if (gamepad1.left_bumper) {
            posCoefficient -= 0.0005;
            sleep(200);
        }
        if (gamepad1.left_trigger > 0) {
            maxPower -= 0.1;
            sleep(200);
        }
        if (gamepad1.right_trigger > 0) {
            maxPower += 0.1;
            sleep(200);
        }
        pivot.setPD(posCoefficient, velCoefficient, maxPower);
        pivot.updatePower();
        telemetry.addLine("press x and b to adjust D");
        telemetry.addLine("press left and right bumper to adjust P");
        telemetry.addLine("left trigger to increase max power and right trigger to decrease it");
        telemetry.addLine("hold a to run the motor");
        telemetry.addData("tgtReached", pivot.TargetReached());
        telemetry.addData("pivot power", pivot.getPower());
        telemetry.addData("pivot velocity", pivot.getSpeed());
        telemetry.addData("pivot degrees", pivot.getPosInAngle(AngleUnitV2.DEGREES));
        telemetry.addData("pivot ticks", pivot.getPos());
        telemetry.addData("P", posCoefficient);
        telemetry.addData("D", velCoefficient);
        telemetry.addData("max power", maxPower);
        telemetry.update();
    }
}
