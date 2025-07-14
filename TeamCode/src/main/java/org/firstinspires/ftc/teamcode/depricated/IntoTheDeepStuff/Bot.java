package org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Dimensions.FieldDimensions;
import org.firstinspires.ftc.teamcode.MiscActions.ActionUntillOneIsDone;
import org.firstinspires.ftc.teamcode.MiscActions.CancelableAction;
import org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff.Intake.Intake;
import org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff.outake.Outtake;
import org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff.outake.PrimaryOuttakePivot;
import org.firstinspires.ftc.teamcode.pathing.roadrunner.HeadlessDriveCommand;
import org.firstinspires.ftc.teamcode.pathing.roadrunner.RegularDrive;
import org.firstinspires.ftc.teamcode.pathing.roadrunner.drives.MecanumDrive;
import org.opencv.core.Point;

import java.util.function.DoubleSupplier;

/**
 * the class that holds everything
 */
public class Bot
{
    public MecanumDrive drive;
    public Pose2d beginPose;
    public HeadlessDriveCommand headlessDriveCommand;
    public RegularDrive regularDrive;//depricatedish
    public Outtake outtake;
    public PrimaryOuttakePivot outtakePivot;
    public Intake intake;
    //public Hang hang;
    public boolean transfering = false;
    public final double robotWidth = 9;

    //    Action path = drive.actionBuilder(beginPose)
//            .splineToSplineHeading(intakePos,0)
//            .build();
    public Bot(HardwareMap hardwareMap, Telemetry telemetry, DoubleSupplier time, Pose2d beginPose)
    {
        this.beginPose = beginPose;
        drive = new MecanumDrive(hardwareMap, beginPose);
        headlessDriveCommand = new HeadlessDriveCommand(drive);
        regularDrive = new RegularDrive(drive);
        outtake = new Outtake(hardwareMap, time);
        outtakePivot = new PrimaryOuttakePivot(hardwareMap, time);
        intake = new Intake(hardwareMap, time);
        //vision = new Vision(hardwareMap,telemetry);
    }

    public Bot(HardwareMap hardwareMap, Telemetry telemetry, DoubleSupplier time)
    {
        this.beginPose = MecanumDrive.pose;
        drive = new MecanumDrive(hardwareMap, beginPose);
        headlessDriveCommand = new HeadlessDriveCommand(drive);
        regularDrive = new RegularDrive(drive);
        outtake = new Outtake(hardwareMap, time);
        outtakePivot = new PrimaryOuttakePivot(hardwareMap, time);
        intake = new Intake(hardwareMap, time);
        //vision = new Vision(hardwareMap,telemetry);
        //hang = new Hang(hardwareMap);
    }

    private class addTelemetry implements Action
    {
        String description;
        Object value;

        private addTelemetry(String description, Object value)
        {
            this.description = description;
            this.value = value;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket)
        {
            telemetryPacket.put(description, value);
            return true;
        }
    }

    public Action addTelemetry(String description, Object value)
    {
        return new addTelemetry(description, value);
    }

//    /**
//     * meant to be looped, like a lot of this stuff
//     */
//    public void VisionGrab(){
//        if(vision.streamOpened){
//            SamplePipeline.AnalyzedStone Sample =  vision.getNearestSample();
//        }
//    }
    //region actions

    /**
     * grabs at a sample givien its coords
     *
     * @param p the coords of the sample relative to the midpoint of the barrier
     */
    public Action GrabInSub(Point p)
    {
        Point q = new Point(p.x, FieldDimensions.subLength - p.y);
        double angle = 0;
        double length = 0;
        if (q.y > intake.littleClawArmThingyLength) {

            double x2 = FieldDimensions.innerSubWidth - Math.abs(q.x);
            if (q.x < intake.leftClawLength - FieldDimensions.innerSubWidth) {

                double h = Math.hypot(q.x, q.y);
                angle = Math.acos(intake.leftClawLength / h) + Math.atan(q.y / x2);
                angle = Math.PI - angle;
            }
            if (q.x > FieldDimensions.innerSubWidth - intake.rightClawLength) {

                double h = Math.hypot(x2, q.y);
                angle = Math.acos(intake.rightClawLength / h) + Math.atan(q.y / x2);
                angle = Math.PI - angle;
            }
        }
        length = Math.pow(intake.littleClawArmThingyLength, 2) - Math.pow(q.y, 2) - 2 * q.y * intake.littleClawArmThingyLength * Math.cos(angle);//Law of cosines
        return new SequentialAction(PositionClaw(p, angle, length));

    }

    /**
     * positions the claw at a sample given its coords, the desired angle, and the desired length of
     * the extension
     *
     * @param tgtp   target claw position
     * @param length desired extension length
     * @param angle  the desired angle of the bot
     */
    public Action PositionClaw(Point tgtp, double length, double angle)
    {
        Pose2d botPos = new Pose2d(tgtp.x + length * Math.sin(angle), tgtp.y - length * Math.cos(angle), angle);

        return new CancelableAction(new ParallelAction(drive.StraightTo(botPos), intake.crankSlide.goToLengthInInches(length)), drive.Stop());
    }

    /**
     * transfers the sample from the intake to the outtake
     */
    public Action Transfer()
    {
        return new SequentialAction(
                new ActionUntillOneIsDone(
                        new SequentialAction(
                                new ParallelAction(
                                        outtake.claw.Open(),
                                        IntakeGrab()
                                ),
                                outtake.OutOfTheWayOfTheIntakePos(),
                                intake.DefaultPos(),
                                outtake.TransferPos()
                        ),
                        new SleepAction(1.5)
                ),
                outtake.claw.Close(),
                intake.claw.Open()

        );
    }

    /**
     * Undeploys the intake after moving the outtake out of the way
     */
    public Action SafeUndeployIntake()
    {
        return new SequentialAction(
                outtake.OutOfTheWayOfTheIntakePos(),
                intake.DefaultPos()
        );
    }

    /**
     * Deploys the intake after moving the outtake out of the way
     */
    public Action SafeDeployIntake(double distance)
    {
        return new SequentialAction(
                outtake.OutOfTheWayOfTheIntakePos(),
                intake.PoiseToGrab(distance),
                intake.claw.Open()
        );
    }

    public Action IntakeGrab()
    {
        return new SequentialAction(
                outtake.OutOfTheWayOfTheIntakePos(),
                intake.pivot.deploy(),
                intake.claw.Close()
        );
    }

    public Action IntakeDropSample()
    {
        return new SequentialAction(
                outtake.OutOfTheWayOfTheIntakePos(),
                intake.pivot.poiseForTheGrab(),
                intake.claw.Open()
        );
    }

    /**
     * Deploys the outtake in a position to drop samples in the basket
     */
    public Action AutoBasketDrop()
    {
        return new SequentialAction(
                outtake.claw.Close(),
                outtake.vipers.Up(),
                new ParallelAction(
                        outtake.pivot1.BucketPos(),
                        outtake.pivot2.AutoBucketPos()
                )
        );
    }

    public Action TeleBasketDrop()
    {
        return new SequentialAction(
                outtake.claw.Close(),
                outtake.vipers.Up(),
                new ParallelAction(
                        outtake.pivot1.BucketPos(),
                        outtake.pivot2.TeleBucketPos()
                )
        );
    }

    public Action LowBasketDrop()
    {
        return new SequentialAction(
                outtake.claw.Close(),
                outtake.vipers.LowBasket(),
                new ParallelAction(
                        outtake.pivot1.BucketPos(),
                        outtake.pivot2.TeleBucketPos()
                )
        );
    }

    Pose2d ChamberPos = new Pose2d(0, -20, Math.PI / 2);


    public Action placeSpecPos()
    {
        return new SequentialAction(
                outtake.prepareToPlaceSpec(),
                //drive.StraightTo(ChamberPos),
                outtake.placeSpec()
        );
    }

    public Action UpdateMotorPowers()
    {
        return new CancelableAction(new ParallelAction(outtake.vipers.updatePower(), outtake.pivot1.pivot.new UpdatePower()));
    }

    //endregion
    public void turnOffTheServos()
    {
        intake.pivot.pivot.servo.turnOffController();
    }

}
