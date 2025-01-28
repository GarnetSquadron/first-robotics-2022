package org.firstinspires.ftc.teamcode.Subsystems.outake;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.ActionServo;

import java.util.function.DoubleSupplier;


public class PrimaryOuttakePivot{
    public ActionServo pivot;
    public PrimaryOuttakePivot(HardwareMap hardwareMap, DoubleSupplier time) {
         pivot = new ActionServo(hardwareMap,"primary pivot",0.9,0,2,time);
    }
    public Action BucketPos() {
        return pivot.runToDegrees(135);
    }
    public Action SpecimenOnWallPos() {
        return pivot.runToDegrees(225);
    }
    public Action SpecimenOnChamberPos() {
        return pivot.runToDegrees(0);
    }
    public Action SpecimenOnChamberPosV2() {
        return new SequentialAction(pivot.runToDegrees(0), pivot.runToDegrees(70));
    }
    public Action prepareForSpecimenOnChamberPos() {
        return pivot.runToDegrees(45);
    }

    public Action TransferPos() {
        return pivot.runToRatio(0.03);
    }
    public Action outOfTheWayOfIntakePos(){
        return pivot.runToRatio(0.2);
    }
}