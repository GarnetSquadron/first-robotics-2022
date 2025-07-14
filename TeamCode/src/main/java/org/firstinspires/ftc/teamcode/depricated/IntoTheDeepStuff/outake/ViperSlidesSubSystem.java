package org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff.outake;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.NullAction;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Dimensions.FieldDimensions;
import org.firstinspires.ftc.teamcode.Dimensions.RobotDimensions;
import org.firstinspires.ftc.teamcode.ExtraMath;
import org.firstinspires.ftc.teamcode.controllers.Controller;
import org.firstinspires.ftc.teamcode.enums.AngleUnitV2;
import org.firstinspires.ftc.teamcode.hardwareClasses.motors.DistanceSensorMotor;
import org.firstinspires.ftc.teamcode.hardwareClasses.motors.RAWMOTOR;

public class ViperSlidesSubSystem
{
    public DistanceSensorMotor l;
    public RAWMOTOR r;

    public boolean disabled = false;
    private final int LMaxPos = -4000;
    private final int LMinPos = 0;
    private final int RMaxPos = 4000;
    private final int RMinPos = 0;
    DistanceSensor sensor;
    double totalRevs = 8.1, strokeLength = 38.425;//based on https://www.gobilda.com/4-stage-viper-slide-kit-belt-driven-336mm-slides/?srsltid=AfmBOop1ONQi_MCp5LMjMV55FO3ZtN6YcIHnEL4hXhXS2j3_KoAiYx0O
    double revPerInch = totalRevs / strokeLength;

    public ViperSlidesSubSystem(HardwareMap hardwareMap)
    {
        l = new DistanceSensorMotor(hardwareMap, "LeftViper", 0, 30);
        sensor = hardwareMap.get(Rev2mDistanceSensor.class, "viper distance sensor");
        l.setDistanceSensor(sensor);
        l.getEncoder().setCPR(Motor.GoBILDA.RPM_435);
        l.getEncoder().scaleToAngleUnit(AngleUnitV2.REVOLUTIONS);
        l.getEncoder().scaleScaleBy(1 / revPerInch);
        l.getEncoder().setTicks(0);
        l.setPID(0.5, 0, 0);
        l.setTolerance(1);
        //l.setMaxPower(0.5);
        l.setExtTorqueController(new Controller()
        {
            @Override
            public double calculate()
            {
                return -0.15;
            }
        });
        r = new RAWMOTOR(hardwareMap, "RightViper");
        l.reverseMotor();
        //r.reverseMotor();
    }

    public void updatePos()
    {
        l.updateDistance();
    }

    public Action goToInches(double inches)
    {
        return l.runToPosition(inches);
    }

    public boolean targetReached()
    {
        return l.targetReached();
    }

    //    public double DistanceToTarget(){
//        return l.getDistanceToTarget();
//    }
    public double getTgtPosition()
    {
        return l.getTargetPosition();
    }

    public double getInches()
    {
        return l.getEncoder().getPos();
    }

    public Action Up()
    {
        if (disabled) {
            return new NullAction();
        } else
            return l.runToPosition(30);
    }

    public Action LowBasket()
    {
        return l.runToPosition(10);
    }

    public Action prepareSpecimenPlace()
    {
        return l.runToPosition(1.0985);
    }

    public Action SpecimenPlace()
    {
        return l.runToPosition(5.492625);//what can I say I like ridiculous numbers of sig figs
    }

    public Action SpecimenPlaceV2()
    {
        return l.runToPosition(1.6477875);
    }

    public Action SpecimenPlaceV3()
    {
        return l.runToPosition(FieldDimensions.highChamberHeight - RobotDimensions.outtakePivotMinimumHeight + RobotDimensions.AdditionalClippingHeight);
    }

    public Action RemoveSpecimenFromWall()
    {
        return l.runToPosition(4);
    }

    public Action SpecimenHold()
    {
        return l.runToPosition(0.5);
    }

    public Action Down()
    {
        if (disabled) {
            return new NullAction();
        } else
            return new SequentialAction(l.runWithPowerUntilStopped(-1, 0.1), new InstantAction(() -> l.getEncoder().setPosition(0)));
        //return l.runToPosition(0);
    }

    public boolean isGoingDown()
    {
        return ExtraMath.ApproximatelyEqualTo(getTgtPosition(), 0, 1);
    }

    public boolean isDown()
    {
        return ExtraMath.ApproximatelyEqualTo(l.getEncoder().getPos(), 0, 1);
    }

    public class updateR implements Action
    {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket)
        {
            r.setPower(l.getPower());
            return true;
        }
    }

    public Action updatePower()
    {
        return new ParallelAction(new updateR(), l.new UpdatePower());
    }
//    public Action zeroMotor(){
//        return new ParallelAction( l.runWithPowerUntilStopped(1,1),r.GoToPosButIfStoppedAssumePosHasBeenReached(0));
//    }
}
