package org.firstinspires.ftc.teamcode.Subsystems.outake;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.ExtraMath;
import org.firstinspires.ftc.teamcode.enums.AngleUnitV2;

import java.util.function.DoubleSupplier;

public class Outtake {
    public ViperSlidesSubSystem vipers;
    public OuttakeClaw claw;
    public DcMotorPrimaryOuttakePivot pivot1;
    public SecondaryOuttakePivot pivot2;
    boolean BasketDropping = false;

    double grabOffWallAngle = 211-180;
    double pivotHeight = 15;
    double outtakeLength = 8.375;

    public Outtake(HardwareMap hardwareMap, DoubleSupplier time) {
        claw = new OuttakeClaw(hardwareMap, time);
        pivot1 = new DcMotorPrimaryOuttakePivot(hardwareMap);
        pivot2 = new SecondaryOuttakePivot(hardwareMap, time);
        vipers = new ViperSlidesSubSystem(hardwareMap);
    }

    /**
     * this function is meant to be looped
     */
    public Action TransferPos() {
        return new SequentialAction(
                pivot1.outOfTheWayOfIntakePos(),
                pivot2.TransferPos(),
                pivot1.TransferPos(),
                vipers.Down()
        );
    }
    public Action grabSpecPos(){
        return new ParallelAction(
                vipers.Down(),
                moveToAngleAndMakeTheClawStraight(grabOffWallAngle),
                claw.Open()
        );
    }
    public Action moveToAngleAndMakeTheClawStraight(double angle){
        return new ParallelAction(
                pivot1.SpecimenOnWallPos(angle+180),
                pivot2.goToDegrees(103-angle)
        );
    }

    /**
     * doesnt work
     * @param height
     * @return
     */
    public Action moveToHeightAndMakeTheClawStraight(double height){

        double angle = getAngleFromHeight(height);
        return moveToAngleAndMakeTheClawStraight(angle);
    }
    public double getAngleFromHeight(double height){
        return Math.asin((pivotHeight-height)/outtakeLength);
    }
    public boolean isGrabbingOffWall(){
        return ExtraMath.ApproximatelyEqualTo(pivot1.pivot.getTargetAngle(AngleUnitV2.DEGREES),grabOffWallAngle,1);
    }
    public Action prepareToPlaceSpec(){
        return new ParallelAction(
                claw.Close(),
                pivot1.prepareForSpecimenOnChamberPos(),
                pivot2.SpecimenOnChamberPos(),
                vipers.Down()
        );
    }
    public Action placeSpecPos(){
        return new SequentialAction(
                vipers.SpecimenHold(),
                claw.Open()
        );
    }
    boolean pivotMoving(){
        return ExtraMath.ApproximatelyEqualTo( pivot1.pivot.getSpeed(),0,0.02);
    }
    public Action placeSpecPosV2(){
        return new SequentialAction(
                pivot1.prepareForSpecimenOnChamberPos(),
                vipers.SpecimenPlaceV2(),
                new ParallelAction(
                        //new CancelableAction(pivot1.SpecimenOnChamberPosV2(),pivot1.);
                        pivot1.SpecimenOnChamberPosV2(),
                        pivot2.SpecimenOnChamberPos(),
                        new SequentialAction(
                                new SleepAction(0.4),
                                claw.Open()
                        )
                )
        );
    }
    public Action grabSpecOfWall(){
        return new SequentialAction(
                claw.Close(),
                vipers.RemoveSpecimenFromWall(),
                pivot1.SpecimenOnChamberPos()
        );
    }
    public Action OutOfTheWayOfTheIntakePos(){
        return new ParallelAction(
                pivot1.outOfTheWayOfIntakePos(),
                pivot2.outOfTheWayOfIntakePos()
        );
    }
    public Action SafeVipersDown(){
        return new SequentialAction(

                new ParallelAction(
                        pivot1.outOfTheWayOfIntakePos(),
                        pivot2.outOfTheWayOfIntakePos()
                ),
                vipers.Down()


        );
    }
//    public boolean atWallPos(){
//        return
//    }
}
