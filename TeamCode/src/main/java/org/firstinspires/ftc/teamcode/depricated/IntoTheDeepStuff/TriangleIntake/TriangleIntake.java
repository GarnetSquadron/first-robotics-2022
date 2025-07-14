package org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff.TriangleIntake;

//import com.acmerobotics.roadrunner.Action;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class TriangleIntake extends SubsystemBase
{
    private final CRServo Ti;
    private final CRServo Fi;
    private final CRServo Bi;

    public TriangleIntake(HardwareMap hardwareMap, String TiName, String FiName, String BiName, String pivotName)
    {
        Ti = hardwareMap.get(CRServo.class, TiName);
        Fi = hardwareMap.get(CRServo.class, FiName);
        Bi = hardwareMap.get(CRServo.class, BiName);
    }

    public enum State
    {
        INTAKING,
        EJECTING,
        HOLDING
    }

    public State state;

    public void intake()
    {
        Ti.setPower(0);
        Fi.setPower(+1);
        Bi.setPower(-1);
        state = State.INTAKING;
    }

    public void eject()
    {
        Ti.setPower(+1);
        Fi.setPower(+1);
        Bi.setPower(0);
        state = State.EJECTING;
    }

    public void hold()
    {
        Ti.setPower(0);
        Fi.setPower(0);
        Bi.setPower(0);
        state = State.HOLDING;
    }
//    public void send() {
//        Ti.setPower(+1);
//        Fi.setPower(0);
//        Bi.setPower(+1);
//    }
}