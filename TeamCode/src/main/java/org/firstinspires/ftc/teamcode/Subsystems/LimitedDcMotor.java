package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;

/**
 * UNUSED
 */
public class LimitedDcMotor extends SubsystemBase
{
//    private final DcMotorSub motor;
//    private final int MaxPos;
//    private final int MinPos;
//    private int tgtPos;
//    public LimitedDcMotor(HardwareMap hardwareMap, String MotorName, int minPos, int maxPos, double posCoefficient){
//        motor = new DcMotorSub(hardwareMap,MotorName,minPos,maxPos,posCoefficient);
//    }
//    public void setTgPos(double posRatio){
//        // set the run mode
//        motor.setRunMode(Motor.RunMode.PositionControl);
//
//// set and get the position coefficient
//        motor.setPositionCoefficient(PosCoefficient);
//
//
//        tgtPos = getPosFromRatio(MinPos, MaxPos,posRatio);// an integer representing
//        // desired tick count
//// set the target position
//        motor.setTargetPosition(tgtPos);
//
//
//        motor.set(0);
//
//// set the tolerance
//        motor.setPositionTolerance(13.6);   // allowed maximum error
//
//// perform the control loop
//
//    }
//    public void runToTgPos(){
//        if (!motor.atTargetPosition()) {
//            motor.set(1);
//        }
//        else {
//            motor.stopMotor();// stop the motor
//        }
//    }
//    int getPosFromRatio(int min, int max, double pos){
//        return min+(int)Math.round(pos*(max-min));
//    }
//    public boolean TargetReached(){
//        return motor.atTargetPosition();
//    }
//    public int getPos(){
//        return motor.getCurrentPosition();
//    }
//    public int getTargetPos(){
//        return tgtPos;
//    }

}