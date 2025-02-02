package org.firstinspires.ftc.teamcode.Subsystems.outake;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.ActionDcMotor;
import org.firstinspires.ftc.teamcode.Subsystems.DcMotorSub;
import org.firstinspires.ftc.teamcode.enums.AngleUnitV2;

import java.util.function.DoubleSupplier;

public class DcMotorPrimaryOuttakePivot{
    public ActionDcMotor pivot;
    double tolerance = 30;
    public DcMotorPrimaryOuttakePivot(HardwareMap hardwareMap) {
        pivot = new ActionDcMotor(hardwareMap,"Primary Pivot",0,950,0.015,10);//min and max need to be tuned
    }
    public Action BucketPos() {
        return pivot.GoToAngleAndHoldIt(135,tolerance,0.5,0.5, AngleUnitV2.DEGREES);
    }
    public Action SpecimenOnWallPos() {
        return pivot.GoToAngleAndHoldIt(225,tolerance,0.5,0.5, AngleUnitV2.DEGREES);
    }
    public Action SpecimenOnChamberPos() {
        return pivot.GoToAngleAndHoldIt(0,tolerance,0.5,0.5, AngleUnitV2.DEGREES);
    }
    public Action SpecimenOnChamberPosV2() {
        return new SequentialAction(pivot.GoToAngleAndHoldIt(0,tolerance,0.5,0.5, AngleUnitV2.DEGREES), pivot.GoToAngleAndHoldIt(70,0,0.5,0.5,AngleUnitV2.DEGREES));
    }
    public Action prepareForSpecimenOnChamberPos() {
        return pivot.GoToAngleAndHoldIt(45,tolerance,0.5,0.5,AngleUnitV2.DEGREES);
    }

    public Action TransferPos() {
        return pivot.GoToAngleAndHoldIt(25,tolerance,0.5,0.5,AngleUnitV2.DEGREES);
    }
    public Action outOfTheWayOfIntakePos(){
        return pivot.GoToAngleAndHoldIt(45,tolerance,0.5,0.5,AngleUnitV2.DEGREES);
    }
    public Action zeroMotor(){return pivot.goUntilStoppedAndAssumeTgtAngleHasBeenReached(0,-0.5,AngleUnitV2.DEGREES);}

}
