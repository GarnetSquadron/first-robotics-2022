package org.firstinspires.ftc.teamcode.Subsystems.outake;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorColor;
import org.firstinspires.ftc.teamcode.AngleEncoder;
import org.firstinspires.ftc.teamcode.Subsystems.ActionDcMotor;
import org.firstinspires.ftc.teamcode.Subsystems.Encoder;
import org.firstinspires.ftc.teamcode.Subsystems.controllers.ArmOnAPivotController;
import org.firstinspires.ftc.teamcode.Subsystems.controllers.Controller;
import org.firstinspires.ftc.teamcode.Subsystems.hardwareClasses.motors.ACTIONMOTOR;
import org.firstinspires.ftc.teamcode.enums.AngleUnitV2;

import java.lang.reflect.InvocationTargetException;

@Config
public class DcMotorPrimaryOuttakePivot{
    public ACTIONMOTOR pivot;
    public Encoder encoder;
    double tolerance = 60;
    double powerCoefficient, minHeight;
    public DcMotorPrimaryOuttakePivot(HardwareMap hardwareMap){
        pivot = new ACTIONMOTOR(hardwareMap,"Primary Pivot");//min and max need to be tuned
        encoder = new Encoder(pivot.getFtcLibMotor());
        pivot.setTolerance(tolerance);
        pivot.setEncoder(encoder);
        pivot.setPID(0.4,0,0);
        pivot.setExtTorqueController(new ArmOnAPivotController(Math.PI/2,0.1));
    }
    public Action goToPosWithCorrectSpeed(double angle){
        return pivot.runToPosition(angle);
    }
    public Action BucketPos() {
        return pivot.runToPosition(Math.toRadians(135));
    }
    public Action SpecimenOnWallPos(double angle) {
        return goToPosWithCorrectSpeed(angle);
    }
    public Action SpecimenOnChamberPos() {
        return goToPosWithCorrectSpeed(0);
    }
    public Action SpecimenOnChamberPosV2() {
        return new SequentialAction(prepareForSpecimenOnChamberPos(), new InstantAction(()->pivot.setTargetPower(0.9)),new SleepAction(0.8));//TODO: try increasing duration, and then decreasing power to keep the robot together.
        /*   new SleepAction(0.1), pivot.goUntilStoppedAndThenRampPowerUntilItsStoppedAgain(1,0.001)*//*goToPosWithCorrectSpeed(120,AngleUnitV2.DEGREES)*/
    }
    public Action SpecimenOnChamberPosV3(){
        return pivot.runToPosition(Math.toRadians(20));
    }
    public Action prepareForSpecimenOnChamberPos() {
        return goToPosWithCorrectSpeed(Math.toRadians(25));
    }

    public Action TransferPos() {
        return goToPosWithCorrectSpeed(0);
    }
    public Action outOfTheWayOfIntakePos(){
        return goToPosWithCorrectSpeed(Math.toRadians(55));
    }
    public Action zeroMotor(){return new SequentialAction(pivot.runWithPowerUntilStopped(-0.5,1), new InstantAction(()->pivot.getEncoder().setPos(0)));}
    public double getTargetDegrees(){
        return Math.toDegrees(pivot.getTargetPosition());
    }

}
