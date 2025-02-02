package org.firstinspires.ftc.teamcode.Subsystems.outake;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.MiscActions.ConditionalAction;

import java.util.function.DoubleSupplier;

public class Outtake {
    public ViperSlidesSubSystem vipers;
    public OuttakeClaw claw;
    public DcMotorPrimaryOuttakePivot pivot1;
    public SecondaryOuttakePivot pivot2;
    boolean BasketDropping = false;

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
                pivot1.SpecimenOnWallPos(),
                pivot2.SpecimenOnWallPos(),
                claw.Open()
        );
    }
    public Action prepareToPlaceSpec(){
        return new ParallelAction(
                claw.Close(),
                pivot1.SpecimenOnChamberPos(),
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
    public Action placeSpecPosV2(){
        return new SequentialAction(
                pivot1.SpecimenOnChamberPos(),
                new ParallelAction(
                        vipers.SpecimenPlaceV2(),
                        pivot1.SpecimenOnChamberPosV2(),
                        pivot2.SpecimenOnChamberPos()
                ),
                new SleepAction(0.5),
                claw.Open()
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
}
