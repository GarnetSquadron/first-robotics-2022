package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.ExtraMath;
import org.firstinspires.ftc.teamcode.enums.AngleUnitV2;

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
    private double power;
    /**
     * actual encoder position-position that we want to call it
     */
    private int PosError = 0;//the amount that its set position differs from the real position
    double tolerance = 10;
    double ticksInFullCircle = 1425.1;
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


        tgtPos = pos;// an integer representing
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
    public void setTgPosAngle(double angle,AngleUnitV2 unit,double tolerance){
        setTgPosTick((int)Math.round(ticksInFullCircle*ExtraMath.ConvertUnit(angle,unit,AngleUnitV2.REVOLUTIONS)),tolerance);
    }
    public void setTgPosAngle(double angle,AngleUnitV2 unit){
        setTgPosAngle(angle,unit,tolerance);
    }
    private void setPower(double power){
        this.power = power;
        motor.set(power);
    }
    public void updatePower(){
        motor.set(power);
    }
    public void runToTgPos(){
        if (!TargetReached()) {
            setPower(1);
        }
        else {
            stop();// stop the motor
        }
    }

    /**
     * runs at max vel because speeeeeeeeeeeeeeed
     * @param holdPower
     */
    public void runToTgPosAndHoldIt(double holdPower){
        runToTgPosAndHoldIt(holdPower,1);
    }
    /**
     * runs at whatever power you want because mechanical doesn't like fun things.
     * @param holdPower
     */
    public void runToTgPosAndHoldIt(double holdPower,double runningPower){
        if (!TargetReached()) {
            setPower(runningPower);
        }
        else {
            setPower(holdPower);// keep the motor up
        }
    }
    public void JustKeepRunning(double power){
        motor.setRunMode(Motor.RunMode.RawPower);
        setPower(power);
    }
    public void stop(){
        power = 0;
        motor.stopMotor();
    }
    int getPosFromRatio(int min, int max, double pos){
        return min+(int)Math.round(pos*(max-min));
    }
    public boolean TargetReached(){
        return motor.atTargetPosition();
    }
    public int getPos(){
        return motor.getCurrentPosition()-PosError;
    }
    public double getRatioPos(){
        return ExtraMath.convertToRatio(getPos(),MinPos,MaxPos);
    }
    public double getPosInAngle(AngleUnitV2 unit){
        return ExtraMath.ConvertUnit(getPos()/ticksInFullCircle,AngleUnitV2.REVOLUTIONS,unit);
    }
    public int getTargetPos(){
        return tgtPos;
    }
    public double getTargetAngle(AngleUnitV2 unit){
        return ExtraMath.getFullCircle(unit)*tgtPos/ticksInFullCircle;
    }
    public double getCurrent(){
        return m.getCurrent(CurrentUnit.AMPS);
    }
    public void setPosition(int position){
        PosError = motor.getCurrentPosition()-position;
    }
    public void setAngle(double angle, AngleUnitV2 unit){
        setPosition((int)Math.round(ticksInFullCircle*ExtraMath.ConvertUnit(angle,unit,AngleUnitV2.REVOLUTIONS)));
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

