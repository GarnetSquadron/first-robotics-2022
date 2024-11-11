package org.firstinspires.ftc.teamcode.Subsystems;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class ServoAlignmentSub extends SubsystemBase {
    public final CRServo left;
    public final CRServo right;
    public ServoAlignmentSub(HardwareMap hardwareMap, String name1, String name2) {
         left = hardwareMap.get(CRServo.class, name1);
         right = hardwareMap.get(CRServo.class, name2);
    }

    public void Up() {
        left.setPower(1);
        right.setPower(-1);
    }

    public void Down() {
        left.setPower(-1);
        right.setPower(1);
    }
}
//commit