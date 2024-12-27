package org.firstinspires.ftc.teamcode.Subsystems.outake;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.Subsystems.ActionDcMotor;
import org.firstinspires.ftc.teamcode.Subsystems.ActionServo;
import org.firstinspires.ftc.teamcode.Subsystems.DcMotorSub;

public class ViperSlidesSubSystem{
    public ActionDcMotor l;
    public ActionDcMotor r;
    private final int LMaxPos = -4000;
    private final int LMinPos = 0;
    private final int RMaxPos = 4000;
    private final int RMinPos = 0;
    private double posCoefficient = 0.05;
    public ViperSlidesSubSystem(HardwareMap hardwareMap){
         l = new ActionDcMotor(hardwareMap,"LeftViper",0,-3900,posCoefficient);
         r = new ActionDcMotor(hardwareMap,"RightViper",0,3900,posCoefficient);
    }
    public boolean targetReached(){
        return l.targetReached()&&r.targetReached();
    }
    public Action Up() {
        return new ParallelAction(l.GoToPos(1),r.GoToPos(1));
    }
    public Action Down() {
        return new ParallelAction(l.GoToPos(0),r.GoToPos(0));
    }
}
