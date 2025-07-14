package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.GamepadClasses.BetterControllerClass;
import org.firstinspires.ftc.teamcode.OpmodeActionSceduling.TeleOpActionScheduler;
import org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff.outake.DcMotorPrimaryOuttakePivot;
import org.firstinspires.ftc.teamcode.inputmodifiers.BooleanChangeDetector;

@Disabled
@TeleOp(name = "pivot motor test", group = "test")
public class DcMotorPivotTest extends OpMode
{
    DcMotorPrimaryOuttakePivot pivot;
    TeleOpActionScheduler scheduler;
    BetterControllerClass GPad1;
    BooleanChangeDetector A, B, X, Y;

    @Override
    public void init()
    {
        pivot = new DcMotorPrimaryOuttakePivot(hardwareMap);
        scheduler = new TeleOpActionScheduler();
        GPad1 = new BetterControllerClass(gamepad1);
        A = new BooleanChangeDetector(GPad1::A);
        B = new BooleanChangeDetector(GPad1::B);
        X = new BooleanChangeDetector(GPad1::X);
        Y = new BooleanChangeDetector(GPad1::Y);
    }

    @Override
    public void loop()
    {
        A.update();
        B.update();
        X.update();
        Y.update();
        if (A.getState()) {
            scheduler.start(pivot.BucketPos(), "bucket");
        }
        if (B.getState()) {
            scheduler.start(pivot.SpecimenOnChamberPos(), "bucket");
        }
        if (X.getState()) {
            scheduler.start(pivot.pivot.runToPosition(180), "bucket");
        }
//        telemetry.addData("ticks",pivot.pivot.getPos());
//        telemetry.addData("angle",pivot.pivot.getAngle(AngleUnitV2.DEGREES));
//        telemetry.addData("tgtTicks",pivot.pivot.getTargetPos());
//        telemetry.addData("tgtAngle",pivot.pivot.getTargetAngle(AngleUnitV2.DEGREES));
        telemetry.update();
        scheduler.update();
    }
}
