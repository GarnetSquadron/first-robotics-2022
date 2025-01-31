package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.BetterControllerClass;
import org.firstinspires.ftc.teamcode.BooleanChangeDetector;
import org.firstinspires.ftc.teamcode.BooleanToggler;
import org.firstinspires.ftc.teamcode.OpmodeActionSceduling.TeleOpActionScheduler;
import org.firstinspires.ftc.teamcode.Subsystems.outake.DcMotorPrimaryOuttakePivot;
import org.firstinspires.ftc.teamcode.enums.AngleUnit;

@TeleOp(name = "pivot motor test",group = "test")
public class DcMotorPivotTest extends OpMode {
    DcMotorPrimaryOuttakePivot pivot;
    TeleOpActionScheduler scheduler;
    BetterControllerClass GPad1;
    BooleanChangeDetector A,B,X,Y;
    @Override
    public void init() {
        pivot = new DcMotorPrimaryOuttakePivot(hardwareMap);
        scheduler = new TeleOpActionScheduler();
        GPad1 = new BetterControllerClass(gamepad1);
        A = new BooleanChangeDetector(GPad1::A);
        B = new BooleanChangeDetector(GPad1::B);
        X = new BooleanChangeDetector(GPad1::X);
        Y = new BooleanChangeDetector(GPad1::Y);
    }

    @Override
    public void loop() {
        A.update();
        B.update();
        X.update();
        Y.update();
        if(A.getState()){
            scheduler.start(pivot.BucketPos(),"bucket");
        }
        if(B.getState()){
            scheduler.start(pivot.SpecimenOnChamberPos(),"bucket");
        }
        if(X.getState()){
            scheduler.start(pivot.pivot.GoToAngle(180,AngleUnit.DEGREES),"bucket");
        }
        telemetry.addData("ticks",pivot.pivot.getPos());
        telemetry.addData("angle",pivot.pivot.getAngle(AngleUnit.DEGREES));
        telemetry.addData("tgtTicks",pivot.pivot.getTargetPos());
        telemetry.addData("tgtAngle",pivot.pivot.getTargetAngle(AngleUnit.DEGREES));
        telemetry.update();
        scheduler.update();
    }
}
