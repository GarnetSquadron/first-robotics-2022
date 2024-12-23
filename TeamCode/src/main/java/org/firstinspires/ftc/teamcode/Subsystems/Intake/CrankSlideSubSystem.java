package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import com.acmerobotics.roadrunner.Action;
import static org.firstinspires.ftc.teamcode.ExtraMath.Tau;
import static java.lang.Math.PI;
import static java.lang.Math.pow;

import com.acmerobotics.roadrunner.ParallelAction;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.ActionServo;
import org.firstinspires.ftc.teamcode.Subsystems.ServoSub;

import java.util.function.DoubleSupplier;

public class CrankSlideSubSystem extends SubsystemBase {
    public final ActionServo CrankL;
    public final ActionServo CrankR;
    private int SplineTeeth = 25;
    private double getToothSize(int teeth){
        return 4.0/(teeth*3.0);
    }
    private double LeftMin = 0.3333-getToothSize(SplineTeeth)/2.0;
    private double RightMin = 1;
    private double LeftMax = 1-getToothSize(SplineTeeth)/2.0;
    private double RightMax = 0.3333;
    double maxExtensionInInches = 12;// needs to be updated
    double minExtensionInInches = 0;
    double drivingLinkageLength = 4,secondaryLinkageLength = 8;

    public CrankSlideSubSystem(HardwareMap hardwareMap, DoubleSupplier time) {
        CrankL = new ActionServo(hardwareMap,"CrankLeft", LeftMin, LeftMax,time);
        CrankR = new ActionServo(hardwareMap, "CrankRight", RightMin, RightMax,time);
    }
    public Action goToPos(double ratio){
        return new ParallelAction
                (
                        CrankL.runToRatio(ratio),
                        CrankR.runToRatio(ratio)
                );
    }
    public double getAngleFromRatio(double ratio){
        return ratio*PI;
    }
    public double getRatioFromAngle(double angle){
        return angle/PI;
    }
    public Action undeploy() {
        return goToPos(0);
    }
    public Action deploy() {
        return goToPos(1);
    }

    public double getExtensionInInches() {
        return minExtensionInInches+CrankL.getPos()*(maxExtensionInInches-minExtensionInInches);
    }
    /**
     * in inches
     * @return
     */
    public Action goToLengthInInches(double length){
        double angle = Math.acos((pow(secondaryLinkageLength,2)-pow(length,2)-pow(drivingLinkageLength,2))/(2*length*drivingLinkageLength));
        return goToPos(getRatioFromAngle(angle));
    }
}
