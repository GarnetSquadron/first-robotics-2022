package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name="Example1", group = "RoadRunnerStuff")
public class Example1 extends LinearOpMode {
    private DcMotor lf;
    private DcMotor rf;
    private DcMotor lb;
    private DcMotor rb;
    private IMU imu;

    public void forward() {
        lf.setPower(10);
        rf.setPower(10);
        lb.setPower(10);
        rb.setPower(10);
    }

    public void left() {
        lf.setPower(5);
        rf.setPower(15);
        lb.setPower(5);
        rb.setPower(15);
    }

    public void Stop() {
        lf.setPower(0);
        rf.setPower(0);
        lb.setPower(0);
        rb.setPower(0);
    }

    public void runOpMode() {
        lf = hardwareMap.get(DcMotor.class, "lf");
        rf = hardwareMap.get(DcMotor.class, "rf");
        lb = hardwareMap.get(DcMotor.class, "lb");
        rb = hardwareMap.get(DcMotor.class, "rb");
        imu = hardwareMap.get(IMU.class, "imu");
        imu.resetYaw();
        //-------------------------------------------------------------
        for (int i = 0; i < 4; i++) {
            forward();
            sleep(1000);
            while (imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) > 90) {
                left();
                sleep(1000);
            }
        }
        Stop();

    }
}