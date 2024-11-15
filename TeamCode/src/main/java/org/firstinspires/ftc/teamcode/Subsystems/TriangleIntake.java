package org.firstinspires.ftc.teamcode.Subsystems;

//import com.acmerobotics.roadrunner.Action;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class TriangleIntake extends SubsystemBase {
    private final CRServo Ti;
    private final CRServo Fi;
    private final CRServo Bi;
    private final Servo Pivot;
    public TriangleIntake(HardwareMap hardwareMap, String name1,String name2,String name3, String pivotName){
        Ti = hardwareMap.get(CRServo.class,name1);
        Fi = hardwareMap.get(CRServo.class,name2);
        Bi = hardwareMap.get(CRServo.class,name3);
        Pivot = hardwareMap.get(Servo.class,pivotName);
    }
    public enum State{
        INTAKING,
        EJECTING,
        HOLDING
    }
    public void intake() {
        Ti.setPower(0);
        Fi.setPower(-1);
        Bi.setPower(+1);
    }

    public void eject() {
        Ti.setPower(+1);
        Fi.setPower(-1);
        Bi.setPower(0);
    }

    public void hold() {
        Ti.setPower(0);
        Fi.setPower(0);
        Bi.setPower(0);
    }
    public void send() {
        Ti.setPower(+1);
        Fi.setPower(0);
        Bi.setPower(+1);
    }
}