package org.firstinspires.ftc.teamcode.Tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.ActionDcMotor;
import org.firstinspires.ftc.teamcode.Subsystems.DcMotorSub;
@TeleOp(name = "Tuning pivot")
public class TuningPrimaryOuttakePivot extends OpMode {
    DcMotorSub pivot;
    double ExtForceCoefficient = 0.4;
    @Override
    public void init() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        pivot = new DcMotorSub(hardwareMap,"Primary Pivot",-450,450,0.0004,30);
        pivot.setExtTorqueFunction(theta-> ExtForceCoefficient *Math.sin(theta));
        pivot.setDesiredNetTorqueFunction(x->0.0);
    }

    @Override
    public void loop() {
        pivot.setTgPosTick(0);
        pivot.AccountForExtForces();
        pivot.setPower(0);
        if(gamepad1.a){
            pivot.setPower(1);
        }
        pivot.updatePower();
        telemetry.addData("tgtReached", pivot.TargetReached());
        telemetry.addData("pivot power", pivot.getPower());
        telemetry.update();
    }
}
