package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

/**
 * Class to keep all DcMotor actions that can be used for multiple different motors
 */
public class DcMotorSub extends SubsystemBase {
    private final Motor motor;
    private final DcMotorEx m;
    private final int MaxPos;
    private final int MinPos;
    private double PosCoefficient;
    private int tgtPos;
    public DcMotorSub(HardwareMap hardwareMap, String MotorName, int minPos, int maxPos, double posCoefficient){
        motor = new Motor(hardwareMap,MotorName);
        m = hardwareMap.get(DcMotorEx.class,MotorName);
        motor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        motor.resetEncoder();
        MaxPos = minPos;
        MinPos = maxPos;
        PosCoefficient = posCoefficient;
    }
    public void setTgPos(double posRatio){
        // set the run mode
        motor.setRunMode(Motor.RunMode.PositionControl);

// set and get the position coefficient
        motor.setPositionCoefficient(PosCoefficient);


        tgtPos = getPosFromRatio(MinPos, MaxPos,posRatio);// an integer representing
        // desired tick count
// set the target position
        motor.setTargetPosition(tgtPos);


        motor.set(0);

// set the tolerance
        motor.setPositionTolerance(13.6);   // allowed maximum error

// perform the control loop

    }
    public void runToTgPos(){
        if (!motor.atTargetPosition()) {
            motor.set(1);
        }
        else {
            motor.stopMotor();// stop the motor
        }
    }
    public void stop(){
        motor.stopMotor();
    }
    int getPosFromRatio(int min, int max, double pos){
        return min+(int)Math.round(pos*(max-min));
    }
    public boolean TargetReached(){
        return motor.atTargetPosition();
    }
    public int getPos(){
        return motor.getCurrentPosition();
    }
    public int getTargetPos(){
        return tgtPos;
    }
    public double getCurrent(){
        return m.getCurrent(CurrentUnit.AMPS);
    }

}

