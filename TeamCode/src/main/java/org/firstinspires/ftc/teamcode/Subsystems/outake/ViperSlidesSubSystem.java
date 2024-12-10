package org.firstinspires.ftc.teamcode.Subsystems.outake;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.Subsystems.DcMotorSubSystem;

public class ViperSlidesSubSystem extends SubsystemBase{
    public DcMotorSubSystem l;
    public DcMotorSubSystem r;
    private final int LMaxPos = -4000;
    private final int LMinPos = 0;
    private final int RMaxPos = 4000;
    private final int RMinPos = 0;
    private double posCoefficient = 0.05;
    public ViperSlidesSubSystem(HardwareMap hardwareMap){
         l = new DcMotorSubSystem(hardwareMap,"LeftViper",0,-4000,posCoefficient);
         r = new DcMotorSubSystem(hardwareMap,"RightViper",0,4000,posCoefficient);
    }
    public void SetTgPosToRetract(){
        l.setTgPos(1);
        r.setTgPos(1);
    }
    public void SetTgPosToExtend(){
        l.setTgPos(0);
        r.setTgPos(0);
    }
    public void runToTgPos(){
        l.runToTgPos();
        r.runToTgPos();
    }
    public boolean targetReached(){
        return l.TargetReached()&&r.TargetReached();
    }
}
