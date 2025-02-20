package org.firstinspires.ftc.teamcode.Tests;


import static android.os.SystemClock.sleep;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.ActionDcMotor;
import org.firstinspires.ftc.teamcode.Subsystems.DcMotorSub;
@TeleOp(name = "AATuning pivot")
public class TuningPrimaryOuttakePivot extends OpMode {
    DcMotorSub pivot;
    double ExtForceCoefficient = 0.4;
    double velCoefficient = 0;
    double posCoefficient = 0.004;
    double tolerance = 60;
    @Override
    public void init() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        pivot = new DcMotorSub(hardwareMap,"Primary Pivot",0,950,0.004,0,tolerance);//min and max need to be tuned
        pivot.setExtTorqueFunction(theta-> -ExtForceCoefficient *Math.cos(theta));
        //pivot.setDesiredNetTorqueFunction((x,v)->0.0);
    }

    @Override
    public void loop() {
        pivot.setTgPosTick(450);
        pivot.AccountForExtForces();
        pivot.setPower(0);
        if (gamepad1.a){
            pivot.setPower(1);
        }
        if (gamepad1.b){
            velCoefficient +=0.005;
            sleep(200);
        }
        if (gamepad1.x){
            velCoefficient -=0.005;
            sleep(200);
        }
        if (gamepad1.right_bumper){
            posCoefficient +=0.0005;
            sleep(200);
        }
        if (gamepad1.left_bumper){
            posCoefficient -=0.0005;
            sleep(200);
        }
        pivot.setPD(posCoefficient,velCoefficient);
        pivot.updatePower();
        telemetry.addLine("press x and b to adjust P");
        telemetry.addLine("press left and right bumper to adjust D");
        telemetry.addLine("hold a to run the motor");
        telemetry.addData("tgtReached", pivot.TargetReached());
        telemetry.addData("pivot power", pivot.getPower());
        telemetry.addData("P", velCoefficient);
        telemetry.addData("D", posCoefficient);
        telemetry.update();
    }
}
