package org.firstinspires.ftc.teamcode.Subsystems.outake;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Outtake {
    public ViperSlidesSubSystem vipers;
    public OuttakeClaw claw;
    public PrimaryOuttakePivot pivot1;
    public SecondaryOuttakePivot pivot2;
    boolean BasketDropping = false;
    public Outtake(HardwareMap hardwareMap){
        claw = new OuttakeClaw(hardwareMap);
        pivot1 = new PrimaryOuttakePivot(hardwareMap);
        pivot2 = new SecondaryOuttakePivot(hardwareMap);
        vipers = new ViperSlidesSubSystem(hardwareMap);
    }

    /**
     * this function is meant to be looped
     */
    public void BucketPos(){
        pivot1.BucketPos();
        pivot2.BucketPos();
    }
    public void TransferPos(){
        pivot1.TransferPos();
        pivot2.TransferPos();
    }
    public void BasketDrop(){

        BasketDropping = true;
    }
    public void goToDefaultPos(){
        vipers.SetTgPosToRetract();
        TransferPos();
    }
    public boolean targetReached(){
        return vipers.targetReached()&&claw.claw.targetReached();
    }
    public void runToTargetPos(){
        vipers.runToTgPos();
        if(BasketDropping){
            //vipers.SetTgPosToExtend();
            //this is meant to be looped
            if(vipers.targetReached()){
                BucketPos();
                //TODO: probably add a sleep here, need to make a class for sleeping in a loop(everything is more complicated in a loop)
                //^ 12/6 I just did this not sure if its fixed though
                if(claw.claw.targetReached()){
                    claw.open();
                    goToDefaultPos();
                    BasketDropping = false;
                }
            }
            else {

            }
        }
    }
    public Action OuttakeBucket() {
        BasketDrop();
        return null;
    }

    public Action ClawTransfer() {
        TransferPos();
        return null;
    }

    }
}
