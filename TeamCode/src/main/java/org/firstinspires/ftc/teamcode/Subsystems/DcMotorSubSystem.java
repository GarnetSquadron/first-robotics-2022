package org.firstinspires.ftc.teamcode.Subsystems;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_USING_ENCODER;
import static org.firstinspires.ftc.teamcode.Subsystems.DcMotorSubSystem.RUN_MODE.RELEASE_MOTOR;
import static org.firstinspires.ftc.teamcode.Subsystems.DcMotorSubSystem.RUN_MODE.RUN_TO_TARGET_POSITION;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * STILL WORKING ON THIS DO NOT USE
 * Class to keep all DcMotor actions that can be used for multiple different motors
 */
public class DcMotorSubSystem extends SubsystemBase {
    public static HardwareMap hardwareMap;
    public static DcMotorEx motor;
    private static String deviceName;
    private static double p, i, d, f;
    private static PIDController controller;
    //region Tick Stuff
    public static int StartingTick;
    private static int MaxTick;
    private static double ticks_in_degrees;
    private static double ticks_in_rad;
    private static int tgPos;
    //endregion
    private static boolean MotorBusy, MotorEnabled;
    public static void setTargetPos(int Pos){
        tgPos = Pos;
    }
    public enum RUN_MODE {
        RUN_TO_TARGET_POSITION,
        RELEASE_MOTOR
    }
    public static RUN_MODE runMode;

    //region constructors
    public DcMotorSubSystem(HardwareMap hardwareMap, String deviceName, int MaxTick, int StartingTick, double p, double i, double d, double f, double ticks_in_degrees){
        motor = hardwareMap.get(DcMotorEx.class, deviceName);
        motor.setMode(com.qualcomm.robotcore.hardware.DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DcMotorSubSystem.MaxTick = MaxTick;
        DcMotorSubSystem.deviceName = deviceName;
        DcMotorSubSystem.StartingTick = StartingTick;
        DcMotorSubSystem.hardwareMap = hardwareMap;
        DcMotorSubSystem.ticks_in_degrees = ticks_in_degrees;
        ticks_in_rad = Math.toRadians(ticks_in_degrees);
        //motor.setPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION, new PIDFCoefficients(0));
        DcMotorSubSystem.p = p;
        DcMotorSubSystem.i = i;
        DcMotorSubSystem.d = d;
        DcMotorSubSystem.f = f;
        tgPos = StartingTick;

        controller = new PIDController(p,i,d);
    }
    public DcMotorSubSystem(HardwareMap hardwareMap, String deviceName, int MaxTick, int StartingTick, double p, double i, double d, double f, double ticks_in_degrees, ForceFunction F){
        this(hardwareMap,deviceName,MaxTick,StartingTick,p,i,d,f,ticks_in_degrees);
        forceFunction = F;
    }
    //endregion
    public static interface ForceFunction{
        double run(int pos);
    }
    static ForceFunction forceFunction = null;
    public static int ConvertFromFracToTick(double frac){
        return (int) Math.round(frac*MaxTick);
    }
    public static double ConvertFromTickToFrac(int tick){
        return (double) tick/MaxTick;
    }
    public static double ConvertFromTickToRad(int tick){
        return (double) tick/ticks_in_rad;
    }
    public static double ConvertFromTickToDeg(int tick){
        return (double) tick/ticks_in_degrees;
    }
    public static double getRadPos(){
        return ConvertFromTickToRad(getTickPos());
    }
    public static double getDegPos(){
        return ConvertFromTickToDeg(getTickPos());
    }
    public static int getTickPos(){
        return motor.getCurrentPosition() + StartingTick;
    }
    public static double getFracPos(){
        return ConvertFromTickToFrac(getTickPos());
    }

    public static class handleMovement implements Action {//forward(1);forward(-1)

        private boolean initialized = false;
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            MotorEnabled = true;
            if(runMode == RUN_TO_TARGET_POSITION){
                motor.setMode(RUN_USING_ENCODER);
                controller.setPID(p, i, d);
                if (tgPos < 0 || tgPos > MaxTick) {
                    throw new RuntimeException(deviceName+" target position outside range");//keep the arm from trying to move outside its range
                }
                if (!initialized) {
                    initialized = true;
                }

                int pos = getTickPos();

                int deltaPos = tgPos - pos;
                //                double vel = motor.getVelocity();
                //                double time = Actions.now();
                double pid = controller.calculate(pos, tgPos);
                double ff;
                if (forceFunction != null) {
                    ff = forceFunction.run(tgPos) * f;
                } else
                    ff = 0;
                double power = pid + ff;
                motor.setPower(power);
                MotorBusy = Math.abs(deltaPos)==0;
                packet.put(deviceName+" tgPos",tgPos);
                packet.put(deviceName+" Pos", pos);
                packet.put(deviceName+" power", motor.getPower());
                packet.put(deviceName+" Busy", MotorBusy);
//                    packet.put(deviceName + " Position", pos);
//                    packet.put(deviceName + " input Power", power);
//                    packet.put(deviceName + " real Power", motor.getPower());
//                    packet.put(deviceName + " AngleInRad", ConvertFromTickToRad(getTickPos() - 100));
//                    packet.put(deviceName + " AngleInDeg", ConvertFromTickToDeg(getTickPos() - 100));
//                    packet.put(deviceName + " Running", Running);
//                    packet.put(deviceName + " P", p);
//                    packet.put(deviceName + " I", i);
//                    packet.put(deviceName + " D", d);

                if (!MotorEnabled) {
                    motor.setPower(0);
                }


            }
            else if(runMode == RELEASE_MOTOR){
                motor.setPower(0);
            }
            return MotorEnabled;
        }
    }
    /**
     * moves the motor a given fraction of its range of motion, will only move if 0<=h<=1
     */
    public static Action handleMovement(){
        return new handleMovement();
    }
    public static class release implements Action{

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket){
            runMode = RELEASE_MOTOR;
            return false;
        }
    }
    public static Action Release() {
        return new release();
    }
    public static class runTo implements Action{
        int pos;
        public runTo(int p){
            pos = p;
        }
        public boolean run(@NonNull TelemetryPacket telemetryPacket){

            if(MotorBusy){
                return true;
            }
            else{
                runMode = RUN_TO_TARGET_POSITION;
                tgPos = pos;
                MotorBusy = true;
                return false;
            }

        }
    }
    public static Action runTo(int pos){
        return new runTo(pos);
    }
    public static class Disable implements Action{
        public boolean run(@NonNull TelemetryPacket telemetryPacket){
            MotorEnabled = false;
            return false;
        }
    }
    public static Action Disable(){
        return new Disable();
    }
}
