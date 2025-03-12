package org.firstinspires.ftc.teamcode.Subsystems.outake;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Dimensions.RobotDimensions;
import org.firstinspires.ftc.teamcode.ExtraMath;

import java.util.function.DoubleSupplier;

public class Outtake {
    public ViperSlidesSubSystem vipers;
    public OuttakeClaw claw;
    public DcMotorPrimaryOuttakePivot pivot1;
    public SecondaryOuttakePivot pivot2;
    boolean BasketDropping = false;

    double grabOffWallAngle = 211-180;

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
                //vipers.GoToInches(wallGrabHeight+Math.sin(Math.toRadians(grabOffWallAngle))-pivotHeight),
                vipers.GoToInches(0.75),
                moveToAngleAndMakeTheClawStraight(grabOffWallAngle),
                claw.Open()
        );
    }
    public Action moveToAngleAndMakeTheClawStraight(double angle){
        return new ParallelAction(
                pivot1.SpecimenOnWallPos(Math.toRadians(angle+180)),
                pivot2.goToDegrees(angle)
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
        return Math.toDegrees(Math.asin((RobotDimensions.outtakePivotMinimumHeight -height)/ RobotDimensions.outtakeLength));
    }
    public boolean isGrabbingOffWall(){
        return ExtraMath.ApproximatelyEqualTo(Math.toDegrees(pivot1.pivot.getTargetPosition()),grabOffWallAngle+180,5);
    }
    public Action prepareToPlaceSpec(){
        return new ParallelAction(
                claw.Close(),
                pivot1.prepareForSpecimenOnChamberPos(),
                pivot2.SpecimenOnChamberPos(),
                vipers.SpecimenPlaceV2()
        );
    }
    public Action placeSpecPos(){
        return new SequentialAction(
                vipers.SpecimenHold(),
                claw.Open()
        );
    }
    boolean pivotMoving(){
        return ExtraMath.ApproximatelyEqualTo( pivot1.pivot.getEncoder().getVelocity(),0,0.02);
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
                                new SleepAction(0.6),
                                claw.Open()
                        )
                )
        );
    }
    public Action placeSpecPosV3(){
        return new ParallelAction(pivot1.SpecimenOnChamberPosV3(), pivot2.goToDegrees(90), vipers.SpecimenPlaceV3());
    }
    public Action grabSpecOfWall(){
        return new SequentialAction(
                claw.Close(),
                vipers.RemoveSpecimenFromWall(),
                prepareToPlaceSpec()//TODO: maybe change to this.prepareToPlaceSpec
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
