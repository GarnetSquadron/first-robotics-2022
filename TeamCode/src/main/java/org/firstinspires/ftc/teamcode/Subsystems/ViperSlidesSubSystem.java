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
         l = new DcMotorSubSystem(hardwareMap,"LeftViper",LMinPos,LMaxPos,posCoefficient);
         r = new DcMotorSubSystem(hardwareMap,"RightViper",RMinPos,RMaxPos,posCoefficient);
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
//    private final Motor l;
//    private final Motor r;
//    private final int LMaxPos = -4000;
//    private final int LMinPos = 0;
//    private final int RMaxPos = 4000;
//    private final int RMinPos = 0;
//    private double posCoefficient = 0.05;
//    public ViperSlidesSubSystem(HardwareMap hardwareMap, String name1, String name2){
//        l = new Motor(hardwareMap,name1);
//        r = new Motor(hardwareMap,name2);
//        l.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
//        r.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
//        l.resetEncoder();
//        r.resetEncoder();
//    }
//    public void setMotorToPosition(int pos, double PC, Motor motor){
//        // set the run mode
//        motor.setRunMode(Motor.RunMode.PositionControl);
//
//// set and get the position coefficient
//        motor.setPositionCoefficient(PC);
//
//// set the target position
//        motor.setTargetPosition(pos);      // an integer representing
//        // desired tick count
//
//        motor.set(0);
//
//// set the tolerance
//        motor.setPositionTolerance(13.6);   // allowed maximum error
//
//// perform the control loop
//
//    }
//    public void runMotorToPosition(Motor motor){
//        if (!motor.atTargetPosition()) {
//            motor.set(1);
//        }
//        else {
//            motor.stopMotor();// stop the motor
//        }
//    }
//    int getPos(int min, int max, double pos){
//        return min+(int)Math.round(pos*(max-min));
//    }
//    public void SetTgPos(double pos){
//        setMotorToPosition(getPos(LMinPos,LMaxPos,pos),posCoefficient,l);
//        setMotorToPosition(getPos(RMinPos,RMaxPos,pos),posCoefficient,r);
//    }
//    public void SetPosExtend() {
//        SetTgPos(1);
//    }
//    public void SetPosReturn() {
//       SetTgPos(0);
//    }
//    public void runToTgPos(){
//        runMotorToPosition(l);
//        runMotorToPosition(r);
//    }
}
