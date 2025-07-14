package org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff.outake;
//

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Dimensions.FieldDimensions;
import org.firstinspires.ftc.teamcode.Dimensions.RobotDimensions;
import org.firstinspires.ftc.teamcode.ExtraMath;
import org.firstinspires.ftc.teamcode.MiscActions.IfThenAction;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

//
public class Outtake
{
    public ViperSlidesSubSystem vipers;
    public OuttakeClaw claw;
    public boolean goingDown, basketPlacing;
    public DcMotorPrimaryOuttakePivot pivot1;
    public SecondaryOuttakePivot pivot2;
    boolean BasketDropping = false;

    double grabOffWallAngle = 211 - 180;

    public Outtake(HardwareMap hardwareMap, DoubleSupplier time)
    {
        claw = new OuttakeClaw(hardwareMap, time);
        pivot1 = new DcMotorPrimaryOuttakePivot(hardwareMap);
        pivot2 = new SecondaryOuttakePivot(hardwareMap, time);
        vipers = new ViperSlidesSubSystem(hardwareMap);
    }

    public Action safeVipersToInches(double inches)
    {
        double heightFromStart = FieldDimensions.highBasketHeight - RobotDimensions.outtakePivotMinimumHeight;
        double heightToAllowPivotToMove = heightFromStart - RobotDimensions.maxOuttakeLength;
        BooleanSupplier movingDown = () -> inches < vipers.getInches();
        BooleanSupplier movingUp = () -> inches > vipers.getInches();
        BooleanSupplier pivotInTheWay = () -> pivot1.pivot.getEncoder().getPos() > Math.toRadians(80);
        BooleanSupplier aboveBaskets = () -> heightFromStart < vipers.getInches() + pivot1.getExtraHeight();
        BooleanSupplier belowBaskets = () -> !aboveBaskets.getAsBoolean();
        BooleanSupplier needToGoDownBeforePivoting = () -> movingUp.getAsBoolean() && belowBaskets.getAsBoolean() && heightFromStart < vipers.getInches() + RobotDimensions.maxOuttakeLength;
        BooleanSupplier needToPivot = () -> (aboveBaskets.getAsBoolean() && movingDown.getAsBoolean()) || (belowBaskets.getAsBoolean() && movingUp.getAsBoolean());
        Action moveVipersDownToAllowPivotToMove = vipers.goToInches(heightToAllowPivotToMove);
        Action movePivotOutOfTheWay = pivot1.goToRad(70);
        return new SequentialAction(
//                new IfThenAction(
//                        pivotInTheWay,
//                        new IfThenAction(
//                                movingDown,
//                                new IfThenAction(
//                                        aboveBaskets,
//                                        movePivotOutOfTheWay
//                                )
//                        ).Else(
//                                new IfThenAction(
//                                        ()->!aboveBaskets.getAsBoolean(),
//                                        new SequentialAction(
//                                                new IfThenAction(
//                                                        needToGoDownBeforePivoting,
//                                                        moveVipersDownToAllowPivotToMove
//                                                ),
//                                                movePivotOutOfTheWay
//                                        )
//                                )
//                        )
//                ),
                new IfThenAction(
                        needToGoDownBeforePivoting,
                        moveVipersDownToAllowPivotToMove
                ),
                new IfThenAction(
                        needToPivot,
                        movePivotOutOfTheWay
                ),
                vipers.goToInches(inches)
        );
    }

    /**
     * this function is meant to be looped
     */
    public Action TransferPos()
    {
        return new SequentialAction(
                pivot1.outOfTheWayOfIntakePos(),
                pivot2.TransferPos(),
                pivot1.TransferPos(),
                vipers.Down()
        );
    }

    public Action grabSpecPos()
    {
        return new ParallelAction(
                //vipers.GoToInches(wallGrabHeight+Math.sin(Math.toRadians(grabOffWallAngle))-pivotHeight),
                new SequentialAction(
                        vipers.Down(),
                        vipers.goToInches(0.75)
                ),
                moveToAngleAndMakeTheClawStraight(grabOffWallAngle),
                claw.Open()
        );
    }

    public Action moveToAngleAndMakeTheClawStraight(double angle)
    {
        return new ParallelAction(
                pivot1.SpecimenOnWallPos(Math.toRadians(angle + 180)),
                pivot2.goToDegrees(angle)
        );
    }
//

    /**
     * doesnt work
     *
     * @param height
     * @return
     */
    public Action moveToHeightAndMakeTheClawStraight(double height)
    {

        double angle = getAngleFromHeight(height);
        return moveToAngleAndMakeTheClawStraight(angle);
    }

    public double getAngleFromHeight(double height)
    {
        return Math.toDegrees(Math.asin((RobotDimensions.outtakePivotMinimumHeight - height) / RobotDimensions.minOuttakeLength));
    }

    public boolean isGrabbingOffWall()
    {
        return ExtraMath.ApproximatelyEqualTo(Math.toDegrees(pivot1.pivot.getTargetPosition()), grabOffWallAngle + 180, 5);
    }

    public boolean readyForClip()
    {
        return ExtraMath.ApproximatelyEqualTo(Math.toDegrees(pivot1.pivot.getTargetPosition()), 25, 5);
    }

    public Action prepareToPlaceSpec()
    {
        return new SequentialAction(
                claw.Close(),
                new ParallelAction(
                        pivot1.prepareForSpecimenOnChamberPos(),
                        pivot2.SpecimenOnChamberPos()
                ),
                vipers.SpecimenPlaceV2()
        );
    }

    public Action placeSpec()
    {
        return new SequentialAction(
                vipers.goToInches(3.40),
                new SleepAction(0.05),
                new ParallelAction(
                        pivot1.goToRad(Math.toRadians(159)),
                        pivot2.goToDegrees(60)
                ));
    }

    public Action AutoplaceSpec()
    {
        return new SequentialAction(
                vipers.goToInches(3.40),
                new SleepAction(0.05),
                new ParallelAction(
                        pivot1.goToRad(Math.toRadians(150)),
                        pivot2.goToDegrees(60)
                ));
    }

    boolean pivotMoving()
    {
        return ExtraMath.ApproximatelyEqualTo(pivot1.pivot.getEncoder().getVelocity(), 0, 0.02);
    }

    public Action placeSpecV2()
    {
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

    //    public Action placeSpecV2(){
//        return new SequentialAction(pivot2.BucketPos(),pivot1.pivot.runToPosition(Math.PI/6));
//    }
    public Action placeSpecV3()
    {
        return new SequentialAction(pivot1.pivot.runToPosition(0), pivot1.clip(), claw.Open());
    }

    public Action prepareToGrabSpecOffWall()
    {
        return new SequentialAction(
                claw.Close(),
                vipers.RemoveSpecimenFromWall(),
                prepareToPlaceSpec()//TODO: maybe change to this.prepareToPlaceSpec
        );
    }

    public Action OutOfTheWayOfTheIntakePos()
    {
        return new ParallelAction(
                pivot1.outOfTheWayOfIntakePos(),
                pivot2.outOfTheWayOfIntakePos()
        );
    }

    public Action SafeVipersDown()
    {
        return new SequentialAction(

                new ParallelAction(
                        pivot1.outOfTheWayOfIntakePos(),
                        pivot2.outOfTheWayOfIntakePos()
                ),
                vipers.Down()


        );//
    }

    public Action clipAndThenPrepareToGrab()
    {
        return new SequentialAction(
                placeSpecV2(),
                prepareToGrabSpecOffWall()
        );
    }
//    public boolean atWallPos(){
//        return
//    }

}
