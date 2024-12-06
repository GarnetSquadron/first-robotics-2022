package org.firstinspires.ftc.teamcode.Subsystems.outake;

import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.Timer;

public class Outtake {
    public ViperSlidesSubSystem vipers;
    public OuttakeClaw claw;
    public OuttakePivotSub pivot;
    public Outtake(HardwareMap hardwareMap){
        claw = new OuttakeClaw(hardwareMap);
        pivot = new OuttakePivotSub(hardwareMap);
        vipers = new ViperSlidesSubSystem(hardwareMap);
    }

    /**
     * this function is meant to be looped
     */
    public void BasketDropping(){
        //this is meant to be looped
        if(vipers.targetReached()){
            pivot.Up();
            //TODO: probably add a sleep here, need to make a class for sleeping in a loop(everything is more complicated in a loop)
            //^ 12/6 I just did this not sure if its fixed though
            if(claw.claw.targetReached()){
                claw.open();
                pivot.Down();
                vipers.SetTgPosToRetract();
            }
        }
        else {
            vipers.SetTgPosToExtend();
        }
    }
}
