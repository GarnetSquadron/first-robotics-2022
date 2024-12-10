package org.firstinspires.ftc.teamcode.Subsystems.Intake;

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

    public CrankSlideSubSystem(HardwareMap hardwareMap) {
        CrankL = new ServoSub(hardwareMap,"CrankLeft", LeftMin, LeftMax);
        CrankR = new ServoSub(hardwareMap, "CrankRight", RightMin, RightMax);

    }
    public void goToPos(double pos){
        CrankL.goToRatio(pos);
        CrankR.goToRatio(pos);
    }
    public void undeploy() {
        goToPos(1);
    }
    public void deploy() {
        goToPos(0);
    }

    public double getExtensionInInches() {
        return minExtensionInInches+CrankL.getPos()*(maxExtensionInInches-minExtensionInInches);
    }
    public boolean targetReached(){
        return CrankL.targetReached()&& CrankR.targetReached();
    }
}
