package org.firstinspires.ftc.teamcode.Subsystems;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class OuttakePivotSub extends SubsystemBase {
    public final ServoSub left;
    public final ServoSub right;
    public OuttakePivotSub(HardwareMap hardwareMap, String name1, String name2) {
         left = new ServoSub(hardwareMap,name1,0,1);
         right = new ServoSub(hardwareMap,name2,1,0);
    }

    public void Up() {
        left.MoveToMax();
        right.MoveToMax();
    }

    public void Down() {
        left.MoveToMin();
        right.MoveToMin();
    }
}
//commit