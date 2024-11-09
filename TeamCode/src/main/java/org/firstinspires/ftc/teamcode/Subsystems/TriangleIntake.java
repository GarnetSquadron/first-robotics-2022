package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

//import com.acmerobotics.roadrunner.Action;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class TriangleIntake extends SubsystemBase {
    private final CRServo Ti;
    private final CRServo Fi;
    private final CRServo Bi;
    public TriangleIntake(HardwareMap hardwareMap, String name1,String name2,String name3){
        Ti = hardwareMap.get(CRServo.class,name1);
        Fi = hardwareMap.get(CRServo.class,name2);
        Bi = hardwareMap.get(CRServo.class,name3);
    }

    public void intake() {
        Ti.setPower(0);
        Fi.setPower(-1);
        Bi.setPower(+1);
        telemetry.addLine("intaking");
    }

    public void eject() {
        Ti.setPower(-1);
        Fi.setPower(+1);
        Bi.setPower(0);
        telemetry.addLine("ejecting");

    }

    public void hold() {
        Ti.setPower(0);
        Fi.setPower(0);
        Bi.setPower(0);
        telemetry.addLine("holding");

    }
        public void send() {
        Ti.setPower(-1);
        Fi.setPower(0);
        Bi.setPower(+1);
    }
}