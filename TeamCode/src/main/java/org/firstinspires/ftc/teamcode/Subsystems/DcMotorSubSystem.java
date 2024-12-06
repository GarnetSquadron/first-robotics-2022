package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Class to keep all DcMotor actions that can be used for multiple different motors
 */
public class DcMotorSubSystem extends SubsystemBase {
    private final Motor motor;
    private final int MaxPos;
    private final int MinPos;
    private double PosCoefficient;
    public DcMotorSubSystem(HardwareMap hardwareMap, String MotorName,int minPos, int maxPos, double posCoefficient){
        motor = new Motor(hardwareMap,MotorName);
        motor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        motor.resetEncoder();
        MaxPos = minPos;
        MinPos = maxPos;
        PosCoefficient = posCoefficient;
    }
    public void setTgPos(int posRatio){
        // set the run mode
        motor.setRunMode(Motor.RunMode.PositionControl);

// set and get the position coefficient
        motor.setPositionCoefficient(PosCoefficient);

// set the target position
        motor.setTargetPosition(getPosFromRatio(MinPos, MaxPos, posRatio));      // an integer representing
        // desired tick count

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
    int getPosFromRatio(int min, int max, double pos){
        return (int)Math.round(pos*(max-min));
    }
    public boolean TargetReached(){
        return motor.atTargetPosition();
    }
}
