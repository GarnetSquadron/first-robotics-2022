package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import static org.firstinspires.ftc.teamcode.ExtraMath.Tau;
import static java.lang.Math.PI;
import static java.lang.Math.pow;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.ServoSub;

public class CrankSlideSubSystem extends SubsystemBase {
    public final ServoSub CrankL;
    public final ServoSub CrankR;
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

    public CrankSlideSubSystem(HardwareMap hardwareMap) {
        CrankL = new ServoSub(hardwareMap,"CrankLeft", LeftMin, LeftMax,10000);
        CrankR = new ServoSub(hardwareMap, "CrankRight", RightMin, RightMax,10000);

    }
    public void goToPos(double pos){
        CrankL.goToRatio(pos);
        CrankR.goToRatio(pos);
    }
    public double getAngleFromRatio(double ratio){
        return ratio*PI;
    }
    public double getRatioFromAngle(double angle){
        return angle/PI;
    }
    public void undeploy() {
        goToPos(0);
    }
    public void deploy() {
        goToPos(1);
    }

    public double getExtensionInInches() {
        return getAngleFromRatio(CrankL.getPos());
    }
    public boolean targetReached(){
        return CrankL.targetReached()&& CrankR.targetReached();
    }
    /**
     * in inches
     * @return
     */
    public void goToLengthInInches(double length){
        double angle = Math.acos((pow(secondaryLinkageLength,2)-pow(length,2)-pow(drivingLinkageLength,2))/(2*length*drivingLinkageLength));
        goToPos(getRatioFromAngle(angle));
    }
}
