package org.firstinspires.ftc.teamcode.OpModes.teleops;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.pathing.roadrunner.drives.MecanumDrive;
import org.firstinspires.ftc.teamcode.pathing.roadrunner.drives.TankDrive;
import org.firstinspires.ftc.teamcode.pathing.roadrunner.tuning.TuningOpModes;
///import ViperSlidesSubSystem;

@TeleOp(name = "Headless Drive")
public class HeadlessDrive extends LinearOpMode
{
    //ViperSlidesSubSystem viperSlidesSubSystem = new ViperSlidesSubSystem();
    DcMotorEx lf, rf, lb, rb;

    /**
     * put this in a loop so that it updates the position
     *
     * @param drive   the MecanumDrive instance
     * @param gamepad the gamepad you want to use for driving
     */
    public static void RunHeadlessDrive(MecanumDrive drive, Gamepad gamepad)
    {
        double direction = MecanumDrive.pose.heading.toDouble();
        drive.setDrivePowers(new PoseVelocity2d(
                new Vector2d(
                        -Math.sin(MecanumDrive.pose.heading.toDouble()) * gamepad.left_stick_x - Math.cos(MecanumDrive.pose.heading.toDouble()) * gamepad.left_stick_y,
                        -Math.cos(MecanumDrive.pose.heading.toDouble()) * gamepad.left_stick_x + Math.sin(MecanumDrive.pose.heading.toDouble()) * gamepad.left_stick_y
                ),
                -gamepad.right_stick_x
        ));
    }

    @Override
    public void runOpMode() throws InterruptedException
    {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        lb = hardwareMap.get(DcMotorEx.class, "lb");
        rb = hardwareMap.get(DcMotorEx.class, "rb");
        lf = hardwareMap.get(DcMotorEx.class, "lf");
        rf = hardwareMap.get(DcMotorEx.class, "rf");

        if (TuningOpModes.DRIVE_CLASS.equals(MecanumDrive.class)) {
            MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

            waitForStart();

            while (opModeIsActive()) {
                drive.updatePoseEstimate();
                RunHeadlessDrive(drive, gamepad1);
                telemetry.addData("lb current", lb.getCurrent(CurrentUnit.AMPS));
                telemetry.addData("rb current", rb.getCurrent(CurrentUnit.AMPS));
                telemetry.addData("lf current", lf.getCurrent(CurrentUnit.AMPS));
                telemetry.addData("rf current", rf.getCurrent(CurrentUnit.AMPS));
                telemetry.update();

//                telemetry.addData("x", drive.pose.position.x);
//                telemetry.addData("y", drive.pose.position.y);
//                telemetry.addData("heading (deg)", Math.toDegrees(drive.pose.heading.toDouble()));
//                telemetry.update();

//                TelemetryPacket packet = new TelemetryPacket();
//                packet.fieldOverlay().setStroke("#3F51B5");
//                Drawing.drawRobot(packet.fieldOverlay(), drive.pose);
//                FtcDashboard.getInstance().sendTelemetryPacket(packet);
            }
        } else if (TuningOpModes.DRIVE_CLASS.equals(TankDrive.class)) {
            TankDrive drive = new TankDrive(hardwareMap, new Pose2d(0, 0, 0));

            waitForStart();

            while (opModeIsActive()) {
                drive.setDrivePowers(new PoseVelocity2d(
                        new Vector2d(
                                -gamepad1.left_stick_y,
                                0.0
                        ),
                        -gamepad1.right_stick_x
                ));

                drive.updatePoseEstimate();
                telemetry.addData("lb current", lb.getCurrent(CurrentUnit.AMPS));
                telemetry.addData("rb current", rb.getCurrent(CurrentUnit.AMPS));
                telemetry.addData("lf current", lf.getCurrent(CurrentUnit.AMPS));
                telemetry.addData("rf current", rf.getCurrent(CurrentUnit.AMPS));
                telemetry.update();

//                telemetry.addData("x", drive.pose.position.x);
//                telemetry.addData("y", drive.pose.position.y);
//                telemetry.addData("heading (deg)", Math.toDegrees(drive.pose.heading.toDouble()));
//                telemetry.update();

//                TelemetryPacket packet = new TelemetryPacket();
//                packet.fieldOverlay().setStroke("#3F51B5");
//                Drawing.drawRobot(packet.fieldOverlay(), drive.pose);
//                FtcDashboard.getInstance().sendTelemetryPacket(packet);

            }
        } else {
            throw new RuntimeException();
        }

        if (gamepad1.right_bumper) {
            //viperSlidesSubSystem.Extend();
        } else if (gamepad1.left_bumper) {
            //viperSlidesSubSystem.Return();
        } else {
            //viperSlidesSubSystem.Stop();
        }
    }

}
