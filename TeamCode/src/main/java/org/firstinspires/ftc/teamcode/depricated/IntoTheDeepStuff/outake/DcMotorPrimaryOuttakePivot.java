package org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff.outake;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Dimensions.RobotDimensions;
import org.firstinspires.ftc.teamcode.controllers.ArmOnAPivotController;
import org.firstinspires.ftc.teamcode.enums.AngleUnitV2;
import org.firstinspires.ftc.teamcode.hardwareClasses.motors.LimitedMotor;

@Config
public class DcMotorPrimaryOuttakePivot
{
    public LimitedMotor pivot;
    double tolerance = 0.0421;
    double powerCoefficient, minHeight;

    public DcMotorPrimaryOuttakePivot(HardwareMap hardwareMap)
    {
        pivot = new LimitedMotor(hardwareMap, "Primary Pivot", 0, 4.188);//min and max need to be tuned
        pivot.getEncoder().setCPR(Motor.GoBILDA.RPM_117);
        pivot.getEncoder().scaleToAngleUnit(AngleUnitV2.RADIANS);
        pivot.setPID(3, 0.1, 0);
        //pivot.setPositionController(new MaxSpeedController(4320,0.1));
        pivot.setTolerance(tolerance);
        pivot.setExtTorqueController(new ArmOnAPivotController(Math.PI / 2, 0.25));
        pivot.setMaxPower(0.5);
    }

    /**
     * for some reason this function wasnt working when the angle was 125 degrees =
     *
     * @param angle
     * @return
     */
    public Action goToRad(double angle)
    {
        return pivot.runToPosition(angle);
    }

    public Action BucketPos()
    {
        return pivot.runToPosition(Math.toRadians(125));
    }

    public Action SpecimenOnWallPos(double angle)
    {
        return goToRad(angle);
    }

    public Action SpecimenOnChamberPos()
    {
        return goToRad(0);
    }

    public Action SpecimenOnChamberPosV2()
    {
        return new SequentialAction(prepareForSpecimenOnChamberPos(), new InstantAction(() -> pivot.setTargetPower(0.8)), new SleepAction(0.4), new InstantAction(() -> pivot.setTargetPower(0)));//TODO: try increasing duration, and then decreasing power to keep the robot together.
        /*   new SleepAction(0.1), pivot.goUntilStoppedAndThenRampPowerUntilItsStoppedAgain(1,0.001)*//*goToPosWithCorrectSpeed(120,AngleUnitV2.DEGREES)*/
    }

    public Action SpecimenOnChamberPosV3()
    {
        return pivot.runToPosition(Math.toRadians(20));
    }

    public Action prepareForSpecimenOnChamberPos()
    {
        return goToRad(Math.toRadians(25));
    }

    public Action TransferPos()
    {
        return goToRad(0);
    }

    public Action outOfTheWayOfIntakePos()
    {
        return goToRad(Math.toRadians(55));
    }

    public Action zeroMotor()
    {
        return new SequentialAction(pivot.runWithPowerUntilStopped(-0.5, 0.01), new InstantAction(() -> pivot.getEncoder().setTicks(0)));
    }

    public double getTargetDegrees()
    {
        return Math.toDegrees(pivot.getTargetPosition());
    }

    public double getExtraHeight()
    {
        return (RobotDimensions.maxOuttakeLength) * Math.asin(pivot.getEncoder().getPos());
    }

    public Action clip()
    {
        return new SequentialAction(new InstantAction(() -> pivot.setTargetPower(0.8)), new SleepAction(0.4));//pivot.runWithPowerUntilStopped(0.5,1);
    }
}
