package org.firstinspires.ftc.teamcode.Subsystems;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class ServoAlignmentSub extends SubsystemBase {
    public final Servo left;
    public final Servo right;
    public ServoAlignmentSub(HardwareMap hardwareMap, String name1, String name2) {
         left = hardwareMap.get(Servo.class, name1);
         right = hardwareMap.get(Servo.class, name2);
    }

    public void Up() {
        left.setPosition(1);
        right.setPosition(0);
    }

    public void Down() {
        left.setPosition(0);
        right.setPosition(1);
    }
}
//commit