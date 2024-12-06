package org.firstinspires.ftc.teamcode.Subsystems.outake;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class Outtake {
    public ViperSlidesSubSystem vipers;
    public OuttakeClaw claw;
    public OuttakePivotSub pivot;
    public Outtake(HardwareMap hardwareMap){
        claw = new OuttakeClaw(hardwareMap);
        pivot = new OuttakePivotSub(hardwareMap);
        vipers = new ViperSlidesSubSystem(hardwareMap);
    }
    public void BasketDropping(){
        //this is meant to be looped
        if(vipers.targetReached()){
            pivot.Up();
            //TODO: probably add a sleep here, need to make a class for sleeping in a loop(everything is more complicated in a loop)
            claw.open();
            pivot.Down();
            vipers.SetTgPosToRetract();
        }
        else {
            vipers.SetTgPosToExtend();
        }
    }
}
