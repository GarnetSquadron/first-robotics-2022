package org.firstinspires.ftc.teamcode.Subsystems.outake;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.NullAction;
import com.acmerobotics.roadrunner.ParallelAction;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.ExtraMath;
import org.firstinspires.ftc.teamcode.Subsystems.ActionDcMotor;
import org.firstinspires.ftc.teamcode.Subsystems.ActionServo;
import org.firstinspires.ftc.teamcode.Subsystems.DcMotorSub;

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
    public ViperSlidesSubSystem(HardwareMap hardwareMap){
         l = new ActionDcMotor(hardwareMap,"LeftViper",0,-3500,posCoefficient);
         r = new ActionDcMotor(hardwareMap,"RightViper",0,3500,posCoefficient);
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
    public Action Up() {
        if(disabled){
            return new NullAction();
        }
        else
            return new ParallelAction(l.GoToPos(0),r.GoToPos(0));
    }
    public Action TgtPosUp(){
        return new ParallelAction(l.new SetTgPos(0),r.new SetTgPos(0));
    }
    public Action Down() {
        if(disabled){
            return new NullAction();
        }
        else
            return new ParallelAction(l.GoToPosButIfStoppedAssumePosHasBeenReached(1,downTolerance,downWaitTime),r.GoToPosButIfStoppedAssumePosHasBeenReached(1,downTolerance,downWaitTime));
    }
    public Action TgtPosDown(){
        return new ParallelAction(l.new SetTgPos(1),r.new SetTgPos(1));
    }
    public boolean isDown(){
        return ExtraMath.ApproximatelyEqualTo(GetTgtPos(),0,50);
    }
}
