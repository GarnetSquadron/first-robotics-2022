package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.ExtraMath;
import org.firstinspires.ftc.teamcode.enums.AngleUnit;

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
    private int PosError = 0;//the amount that its set position differs from the real position
    double tolerance = 10;
    double ticksInFullCircle = 1425.1;
    HardwareMap hardwareMap;
    public DcMotorSub(HardwareMap hardwareMap, String MotorName, int minPos, int maxPos, double posCoefficient,double tolerance){
        motor = new Motor(hardwareMap,MotorName);
        m = hardwareMap.get(DcMotorEx.class,MotorName);
        motor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        motor.resetEncoder();
        MaxPos = maxPos;
        MinPos = minPos;
        PosCoefficient = posCoefficient;
        this.tolerance = tolerance;
    }
    public void setTgPosTick(int pos,double tolerance){
        ExtraMath.Clamp(pos,MaxPos,MinPos);//prevent the motor from moving outside of its range, to avoid accidently breaking stuff
        // set the run mode
        motor.setRunMode(Motor.RunMode.PositionControl);

// set and get the position coefficient
        motor.setPositionCoefficient(PosCoefficient);


        tgtPos = pos;// an integer representing//um shouldnt this be pos+PosError? not gonna change it now cause it seems to work but
        // desired tick count
// set the target position
        motor.setTargetPosition(tgtPos+PosError);


        motor.set(0);

// set the tolerance
        motor.setPositionTolerance(tolerance);   // allowed maximum error
    }
    public void setTgPosTick(int pos){
        setTgPosTick(pos,tolerance);
    }
    public void setTgPosRatio(double posRatio,double tolerance){
        setTgPosTick(getPosFromRatio(MinPos, MaxPos,posRatio),tolerance);
    }
    public void setTgPosRatio(double posRatio){
        setTgPosRatio(posRatio,tolerance);
    }
    public void setTgPosAngle(double angle,AngleUnit unit,double tolerance){
        setTgPosTick((int)Math.round(ticksInFullCircle*ExtraMath.ConvertUnit(angle,unit,AngleUnit.REVOLUTIONS)),tolerance);
    }
    public void setTgPosAngle(double angle,AngleUnit unit){
        setTgPosAngle(angle,unit,tolerance);
    }
    public void runToTgPos(){
        if (!TargetReached()) {
            motor.set(1);
        }
        else {
            motor.stopMotor();// stop the motor
        }
    }
    public void runToTgPosAndHoldIt(double holdPower){
        if (!TargetReached()) {
            motor.set(1);
        }
        else {
            motor.set(holdPower);// keep the motor up
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
        return motor.getCurrentPosition()+PosError;
    }
    public double getRatioPos(){
        return ExtraMath.convertToRatio(getPos(),MinPos,MaxPos);
    }
    public double getPosInAngle(AngleUnit unit){
        return ExtraMath.ConvertUnit(getPos()/ticksInFullCircle,AngleUnit.REVOLUTIONS,unit);
    }
    public int getTargetPos(){
        return tgtPos;
    }
    public double getTargetAngle(AngleUnit unit){
        return ExtraMath.getFullCircle(unit)*tgtPos/ticksInFullCircle;
    }
    public double getCurrent(){
        return m.getCurrent(CurrentUnit.AMPS);
    }
    public void setPosition(int position){
        PosError = position-motor.getCurrentPosition();
    }
    public double getPower(){
        return motor.motor.getPower();
    }
    public double getSpeed(){
        return motor.getRate();
    }
    public void setEncoder(Motor encoder){
        motor.encoder = encoder.encoder;
    }
    public Motor getMotor(){
        return motor;
    }
    public void ReverseMotor(){
        motor.motor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public double getError(){
        return PosError;
    }
}

