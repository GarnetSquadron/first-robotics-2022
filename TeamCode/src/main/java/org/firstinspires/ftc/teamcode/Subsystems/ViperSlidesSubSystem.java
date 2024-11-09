package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.arcrobotics.ftclib.command.SubsystemBase;

public class ViperSlidesSubSystem extends SubsystemBase{
    //private final Motor l;
    private final Motor r;
    private final int LMaxPos = -4000;
    private final int LMinPos = 0;
    private final int RMaxPos = 4000;
    private final int RMinPos = 0;
    private double posCoefficient = 0.05;
    public ViperSlidesSubSystem(HardwareMap hardwareMap, String name1, String name2){
        //l = new Motor(hardwareMap,name1);
        r = new Motor(hardwareMap,name2);
        //l.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        r.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

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
            motor.set(1);
        }
        motor.stopMotor(); // stop the motor
    }
    int getPos(int min, int max, double pos){
        return min+(int)Math.round(pos*(max-min));
    }
    public void runToPos(double pos){
        //runMotorToPosition(getPos(LMinPos,LMaxPos,pos),posCoefficient,l);
        runMotorToPosition(getPos(RMinPos,RMaxPos,pos),posCoefficient,r);
    }
    public void Extend() {
        runToPos(1);
    }
    public void Return() {
       runToPos(0);
    }
}
