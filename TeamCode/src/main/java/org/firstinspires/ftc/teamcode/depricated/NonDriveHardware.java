package org.firstinspires.ftc.teamcode.depricated;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ftc.LynxFirmware;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public final class NonDriveHardware
{
    public static DcMotor lift, arm, telearm;
    public static Servo claw;
    public static int teleArmStartingTick, liftStartingTick, armStartingTick;


    public static class DcMotorActions
    {
        public static DcMotorEx motor;
        private static int MaxTick;
        private static String deviceName;
        public static int StartingTick;
        public static HardwareMap hardwareMap;
        private static double p, i, d, f;
        private static PIDController controller;
        private static double ticks_in_degrees;
        private static double ticks_in_rad;
        private static int tgPos;
        private static boolean MotorBusy, MotorEnabled;

        public static void setTargetPos(int Pos)
        {
            tgPos = Pos;
        }

        public static String runMode;

        public static void setRunMode(String runMode)
        {
            if (runMode == "RunToTargetPosition" || runMode == "ReleaseMotor") {
                DcMotorActions.runMode = runMode;
            } else
                throw new RuntimeException("invalid runMode");
        }

        /**
         * Class to keep all DcMotor actions that can be used for multiple different motors
         *
         * @param hardwareMap
         * @param deviceName
         * @param MaxTick
         */
        public DcMotorActions(HardwareMap hardwareMap, String deviceName, int MaxTick, int StartingTick, double p, double i, double d, double f, double ticks_in_degrees)
        {
            motor = hardwareMap.get(DcMotorEx.class, deviceName);
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            DcMotorActions.MaxTick = MaxTick;
            DcMotorActions.deviceName = deviceName;
            DcMotorActions.StartingTick = StartingTick;
            DcMotorActions.hardwareMap = hardwareMap;
            DcMotorActions.ticks_in_degrees = ticks_in_degrees;
            ticks_in_rad = Math.toRadians(ticks_in_degrees);
            //motor.setPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION, new PIDFCoefficients(0));
            DcMotorActions.p = p;
            DcMotorActions.i = i;
            DcMotorActions.d = d;
            DcMotorActions.f = f;
            tgPos = StartingTick;

            controller = new PIDController(p, i, d);
        }

        public DcMotorActions(HardwareMap hardwareMap, String deviceName, int MaxTick, int StartingTick, double p, double i, double d, double f, double ticks_in_degrees, ForceFunction F)
        {
            forceFunction = F;
            motor = hardwareMap.get(DcMotorEx.class, deviceName);
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            DcMotorActions.MaxTick = MaxTick;
            DcMotorActions.deviceName = deviceName;
            DcMotorActions.StartingTick = StartingTick;
            DcMotorActions.hardwareMap = hardwareMap;
            DcMotorActions.ticks_in_degrees = ticks_in_degrees;
            ticks_in_rad = ticks_in_degrees * 180 / Math.PI;
            DcMotorActions.p = p;
            DcMotorActions.i = i;
            DcMotorActions.d = d;
            DcMotorActions.f = f;

            controller = new PIDController(p, i, d);
        }

        public interface ForceFunction
        {
            double run(int pos);
        }

        static ForceFunction forceFunction = null;

        public static int ConvertFromFracToTick(double frac)
        {
            return (int) Math.round(frac * MaxTick);
        }

        public static double ConvertFromTickToFrac(int tick)
        {
            return (double) tick / MaxTick;
        }

        public static double ConvertFromTickToRad(int tick)
        {
            return (double) tick / ticks_in_rad;
        }

        public static double ConvertFromTickToDeg(int tick)
        {
            return (double) tick / ticks_in_degrees;
        }

        public static double getRadPos()
        {
            return ConvertFromTickToRad(getTickPos());
        }

        public static double getDegPos()
        {
            return ConvertFromTickToDeg(getTickPos());
        }

        public static int getTickPos()
        {
            return motor.getCurrentPosition() + StartingTick;
        }

        public static double getFracPos()
        {
            return ConvertFromTickToFrac(getTickPos());
        }

        public static class handleMovement implements Action
        {//forward(1);forward(-1)

            private boolean initialized = false;

            public handleMovement()
            {
            }

            @Override
            public boolean run(@NonNull TelemetryPacket packet)
            {
                MotorEnabled = true;
                if (runMode == "RunToTargetPosition") {
                    motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    controller.setPID(p, i, d);
                    if (tgPos < 0 || tgPos > MaxTick) {
                        throw new RuntimeException(deviceName + " target position outside range");//keep the arm from trying to move outside its range
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
                    MotorBusy = Math.abs(deltaPos) == 0;
                    packet.put(deviceName + " tgPos", tgPos);
                    packet.put(deviceName + " Pos", pos);
                    packet.put(deviceName + " power", motor.getPower());
                    packet.put(deviceName + " Busy", MotorBusy);
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


                } else if (runMode == "ReleaseMotor") {
                    motor.setPower(0);
                }
                return MotorEnabled;
            }
        }

        /**
         * moves the teleArm a given fraction of its range of motion, will only move if 0<=h<=1
         */
        public static Action handleMovement()
        {
            return new handleMovement();
        }

        public static class release implements Action
        {

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket)
            {
                setRunMode("ReleaseMotor");
                return false;
            }
        }

        public static Action Release()
        {
            return new DcMotorActions.release();
        }

        public static class runTo implements Action
        {
            int pos;

            public runTo(int p)
            {
                pos = p;
            }

            public boolean run(@NonNull TelemetryPacket telemetryPacket)
            {

                if (MotorBusy) {
                    return true;
                } else {
                    setRunMode("RunToTargetPosition");
                    tgPos = pos;
                    MotorBusy = true;
                    return false;
                }

            }
        }

        public static Action runTo(int pos)
        {
            return new runTo(pos);
        }

        public static class Disable implements Action
        {
            public boolean run(@NonNull TelemetryPacket telemetryPacket)
            {
                MotorEnabled = false;
                return false;
            }
        }

        public static Action Disable()
        {
            return new Disable();
        }
    }

    public static class ContinuousServo
    {
        public static CRServo servo;
        public static int reverse;

        public ContinuousServo(HardwareMap hardwareMap, String deviceName, boolean reverse)
        {
            servo = hardwareMap.get(CRServo.class, deviceName);
            if (reverse) {
                ContinuousServo.reverse = -1;
            } else {
                ContinuousServo.reverse = 1;
            }
        }

        public static class Spin implements Action
        {
            double power;

            Spin(double power)
            {
                this.power = power;
            }

            public boolean run(@NonNull TelemetryPacket telemetryPacket)
            {
                servo.setPower(reverse * power);
                return false;
            }
        }

        public static Action spin(double power)
        {
            return new Spin(power);
        }
    }

    public static class TeleArmParams
    {

    }

    public static TeleArmParams teleArmParams = new TeleArmParams();

    @Config
    public static class TeleArm extends DcMotorActions
    {
        public static int MaxTick = -1000;
        public static double p = 0;
        public static double i = 0;
        public static double d = 0;
        public static double f = 0;

        public TeleArm(HardwareMap hardwareMap, int StartingTick)
        {
            super(hardwareMap, "teleArm", MaxTick, StartingTick, p, i, d, f, 700 / 180);
        }

    }

    public static class LiftParams
    {
        public static double Kp = 0.5;
        public static double Ki = 0.5;
        public static double Kd = 0.5;
    }

    @Config
    public static class Lift extends DcMotorActions
    {
        public static int MaxTick = 100;
        public static double p = 0;
        public static double i = 0;
        public static double d = 0;
        public static double f = 0;

        public Lift(HardwareMap hardwareMap, int StartingTick)
        {
            super(hardwareMap, "perp/lift", MaxTick, StartingTick, p, i, d, f, 700 / 180);
            //motor = dcMotorActions.motor;
        }

    }

    @Config
    public static class Arm extends DcMotorActions
    {
        static int MaxTick = 630;
        public static double p = 0.001;
        public static double i = 0;
        public static double d = 0;
        public static double f = 0.0005;

        static ForceFunction forceFunction = (pos) -> Math.cos(ConvertFromTickToRad(pos - 500));
//        public static double P = motor.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION).p;
//        public static double I = motor.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION).i;
//        public static double D = motor.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION).d;
//        public static double F = motor.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION).f;

        public Arm(HardwareMap hardwareMap, int StartingTick)
        {
            super(hardwareMap, "arm", MaxTick, StartingTick, p, i, d, f, 700 / 180, forceFunction);
        }
    }

    @Config
    public static class Launcher
    {
        public static double endPos = 0.9;
        public static Servo launcher;

        public Launcher(HardwareMap hardwareMap)
        {
            launcher = hardwareMap.get(Servo.class, "launcher");
        }

        public static class LaunchPlane implements Action
        {
            public boolean run(@NonNull TelemetryPacket telemetryPacket)
            {
                launcher.setPosition(endPos);
                return false;
            }
        }

        public static Action LaunchPlane()
        {
            return new LaunchPlane();
        }
    }

    public static class IntakeServo1 extends ContinuousServo
    {
        public IntakeServo1(HardwareMap hardwareMap)
        {
            super(hardwareMap, "IntakeServo1", true);
        }
    }

    public static class IntakeServo2 extends ContinuousServo
    {
        public IntakeServo2(HardwareMap hardwareMap)
        {
            super(hardwareMap, "IntakeServo2", false);
        }
    }

    public NonDriveHardware(HardwareMap hardwareMap, int teleArmStartingTick, int liftStartingTick, int ArmStartingTick)
    {
        LynxFirmware.throwIfModulesAreOutdated(hardwareMap);

        lift = hardwareMap.get(DcMotor.class, "perp/lift");
        arm = hardwareMap.get(DcMotor.class, "arm");
        telearm = hardwareMap.get(DcMotor.class, "teleArm");
        claw = hardwareMap.get(Servo.class, "claw");

        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        telearm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        NonDriveHardware.teleArmStartingTick = teleArmStartingTick;
        NonDriveHardware.liftStartingTick = liftStartingTick;
        NonDriveHardware.armStartingTick = armStartingTick;

        //FlightRecorder.write("NonDriveHardware_PARAMS", PARAMS);
    }
}
