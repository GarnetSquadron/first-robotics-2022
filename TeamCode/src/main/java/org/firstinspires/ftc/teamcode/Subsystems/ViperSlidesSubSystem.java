package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.arcrobotics.ftclib.command.SubsystemBase;

public class ViperSlidesSubSystem extends SubsystemBase{
    DcMotorSubSystem l;
    DcMotorSubSystem r;
    private final int LMaxPos = -4000;
    private final int LMinPos = 0;
    private final int RMaxPos = 4000;
    private final int RMinPos = 0;
    private double posCoefficient = 0.05;
    public ViperSlidesSubSystem(HardwareMap hardwareMap){
         l = new DcMotorSubSystem(hardwareMap,"LeftViper",LMinPos,LMaxPos,LMinPos,posCoefficient);
         r = new DcMotorSubSystem(hardwareMap,"RightViper",RMinPos,RMaxPos,RMinPos,posCoefficient);
    }
    public void SetTgPosToExtend(){
        l.setTgPos(1);
        r.setTgPos(1);
    }
    public void SetTgPosToRetract(){
        l.setTgPos(0);
        r.setTgPos(0);
    }
    public void runToTgPos(){
        l.runToTgPos();
        r.runToTgPos();
    }
}
