package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.arcrobotics.ftclib.command.SubsystemBase;

public class ViperSlidesSubSystem extends SubsystemBase{
    private final Motor l;
    //private final Motor r;
    private final int MaxPos = -4000;
    private final int MinPos = 0;
    private double posCoefficient = 0.05;
    public ViperSlidesSubSystem(HardwareMap hardwareMap, String name1, String name2){
        l = hardwareMap.get(Motor.class,name1);
        //r = hardwareMap.get(Motor.class,name2);

    }
    public void runMotorToPosition(int pos, double PC, Motor motor){
        // set the run mode
        motor.setRunMode(Motor.RunMode.PositionControl);

// set and get the position coefficient
        motor.setPositionCoefficient(PC);
        double kP = motor.getPositionCoefficient();

// set the target position
        motor.setTargetPosition(pos);      // an integer representing
        // desired tick count

        motor.set(0);

// set the tolerance
        motor.setPositionTolerance(13.6);   // allowed maximum error

// perform the control loop
        while (!motor.atTargetPosition()) {
            motor.set(0.75);
        }
        motor.stopMotor(); // stop the motor
    }

    public void runToPos(int pos){
        runMotorToPosition(pos,posCoefficient,l);
    }
    public void Extend() {
        runToPos(MaxPos);
    }
    public void Return() {
       runToPos(MinPos);
    }
}
