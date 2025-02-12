package org.firstinspires.ftc.teamcode.Subsystems.outake;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.NullAction;
import com.acmerobotics.roadrunner.ParallelAction;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.ExtraMath;
import org.firstinspires.ftc.teamcode.Subsystems.ActionDcMotor;
import org.firstinspires.ftc.teamcode.enums.AngleUnitV2;

public class ViperSlidesSubSystem{
    public ActionDcMotor l;
    public ActionDcMotor r;
    public TouchSensor LimitSwitch;

    public boolean disabled = false;
    private final int LMaxPos = -4000;
    private final int LMinPos = 0;
    private final int RMaxPos = 4000;
    private final int RMinPos = 0;
    private double posCoefficient = 0.03;//0.05<-original, worked decently
    private double downTolerance = 10, downWaitTime = 1;
    double revPerInch = 8.1/38.425;//based on gobuilda site
    public ViperSlidesSubSystem(HardwareMap hardwareMap){
         l = new ActionDcMotor(hardwareMap,"LeftViper",0,-3300,posCoefficient,100);
         r = new ActionDcMotor(hardwareMap,"RightViper",0,-3300,posCoefficient,100);
         r.reverseMotor();
         r.setEncoder(l.getMotor());
    }
    public boolean targetReached(){
        return l.targetReached()&&r.targetReached();
    }
    public double DistanceToTarget(){
        return l.getDistanceToTarget();
    }
    public double GetTgtPos(){
        return l.getTargetPos();
    }
    public Action GoToPos(double pos){
        return new ParallelAction(l.GoToPos(pos),r.GoToPos(pos));
    }
    public Action GoToInches(double inches){
        double rev = inches*revPerInch;
        return new ParallelAction(l.GoToAngle(rev, AngleUnitV2.REVOLUTIONS),r.GoToAngle(rev,AngleUnitV2.REVOLUTIONS));
    }
    public Action GoToPosAndHoldIt(double pos,double holdPower){
        return new ParallelAction(l.GoToPosAndHoldIt(pos,holdPower),r.GoToPosAndHoldIt(pos,holdPower));
    }
    public Action Up() {
        if(disabled){
            return new NullAction();
        }
        else
            return GoToPos(1);
    }
    public Action HoldUp() {
        if(disabled){
            return new NullAction();
        }
        else
            return GoToPosAndHoldIt(1,0.5);
    }
    public Action prepareSpecimenPlace(){
        return GoToPos(0.1);
    }
    public Action SpecimenPlace(){
        return GoToPos(0.5);
    }
    public Action SpecimenPlaceV2(){
        return GoToPos(0.175);
    }
    public Action RemoveSpecimenFromWall(){
        return GoToInches(6);
    }
    public Action SpecimenHold(){
        return GoToPosAndHoldIt(0.5,0.5);
    }

    public Action Down() {
        if(disabled){
            return new NullAction();
        }
        else
            return new ParallelAction(
                    l.GoToPosButIfStoppedAssumePosHasBeenReached(0,downTolerance),
                    r.GoToPosButIfStoppedAssumePosHasBeenReached(0,downTolerance)
            );
    }

    public boolean isDown(){
        return ExtraMath.ApproximatelyEqualTo(GetTgtPos(),0,50);
    }

    public Action updatePower(){
        return new ParallelAction(l.updatePower(),r.updatePower());
    }

    //region unused
    public Action TgtPosUp(){
        return new ParallelAction(l.new SetTgtPosRatio(1,0),r.new SetTgtPosRatio(1,0));
    }
    public Action TgtPosDown(){
        return new ParallelAction(l.new SetTgtPosRatio(0,0),r.new SetTgtPosRatio(0,0));
    }
    //endregion
}
