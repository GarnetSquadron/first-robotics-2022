package org.firstinspires.ftc.teamcode.Subsystems.outake;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.ActionDcMotor;
import org.firstinspires.ftc.teamcode.Subsystems.DcMotorSub;

import java.util.function.DoubleSupplier;

public class DcMotorPrimaryOuttakePivot{
    ActionDcMotor pivot;
    public DcMotorPrimaryOuttakePivot(HardwareMap hardwareMap) {
        pivot = new ActionDcMotor(hardwareMap,"primary pivot",0,300,0.015);//min and max need to be tuned
    }
    public Action BucketPos() {
        return pivot.GoToPos(135,0);
    }
    public Action SpecimenOnWallPos() {
        return pivot.GoToPos(225,0);
    }
    public Action SpecimenOnChamberPos() {
        return pivot.GoToPos(0,0);
    }
    public Action SpecimenOnChamberPosV2() {
        return new SequentialAction(pivot.GoToPos(0,0), pivot.GoToPos(70,0));
    }
    public Action prepareForSpecimenOnChamberPos() {
        return pivot.GoToPos(45,0);
    }

    public Action TransferPos() {
        return pivot.GoToPos(0.03,0);
    }
    public Action outOfTheWayOfIntakePos(){
        return pivot.GoToPos(0.2,0);
    }

}
