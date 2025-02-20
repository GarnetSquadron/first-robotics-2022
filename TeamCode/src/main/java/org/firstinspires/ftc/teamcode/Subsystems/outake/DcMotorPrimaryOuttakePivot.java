package org.firstinspires.ftc.teamcode.Subsystems.outake;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.ActionDcMotor;
import org.firstinspires.ftc.teamcode.enums.AngleUnitV2;
@Config
public class DcMotorPrimaryOuttakePivot{
    public ActionDcMotor pivot;
    double tolerance = 60;
    double powerCoefficient, minHeight;
    double ExtForceCoefficient = 0.1;
    public DcMotorPrimaryOuttakePivot(HardwareMap hardwareMap) {
        pivot = new ActionDcMotor(hardwareMap,"Primary Pivot",0,950,0.004,0,tolerance);//min and max need to be tuned
        pivot.setExtTorqueFunction(theta-> -ExtForceCoefficient *Math.cos(theta));
        //pivot.setNewCoefficient(0.002);
    }
    public Action goToPosWithCorrectSpeed(double angle, AngleUnitV2 unit){
        //return pivot.GoToAngleAndHoldIt(angle,tolerance,1,unit);
        return pivot.GoToAngleAndHoldItAccountingForExternalForces(angle,tolerance,unit);
    }
    public Action BucketPos() {
        return goToPosWithCorrectSpeed(135,AngleUnitV2.DEGREES);
    }
    public Action SpecimenOnWallPos(double angle) {
        return goToPosWithCorrectSpeed(angle,AngleUnitV2.DEGREES);
    }
    public Action SpecimenOnChamberPos() {
        return goToPosWithCorrectSpeed(0,AngleUnitV2.DEGREES);
    }
    public Action SpecimenOnChamberPosV2() {
        return new SequentialAction(goToPosWithCorrectSpeed(0,AngleUnitV2.DEGREES), pivot.goUntilStoppedAndThenRampPowerUntilItsStoppedAgain(0.25,0.001)/*goToPosWithCorrectSpeed(120,AngleUnitV2.DEGREES)*/);
    }
    public Action prepareForSpecimenOnChamberPos() {
        return goToPosWithCorrectSpeed(45,AngleUnitV2.DEGREES);
    }

    public Action TransferPos() {
        return goToPosWithCorrectSpeed(0,AngleUnitV2.DEGREES);
    }
    public Action outOfTheWayOfIntakePos(){
        return goToPosWithCorrectSpeed(55,AngleUnitV2.DEGREES);}
    public Action zeroMotor(){return pivot.goUntilStoppedAndAssumeTgtAngleHasBeenReached(0,-0.5,AngleUnitV2.DEGREES);}
    public double getTargetDegrees(){
        return pivot.getTargetAngle(AngleUnitV2.DEGREES);
    }

}
