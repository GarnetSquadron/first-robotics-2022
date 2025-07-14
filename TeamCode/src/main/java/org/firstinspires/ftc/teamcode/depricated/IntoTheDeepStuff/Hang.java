package org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.depricated.failedMotorClasses.ActionDcMotor;
import org.firstinspires.ftc.teamcode.enums.AngleUnitV2;

public class Hang
{
    ActionDcMotor hangmotor;

    public Hang(HardwareMap hardwareMap)
    {
        hangmotor = new ActionDcMotor(hardwareMap, "hangmotor", 1, 10, 0.2, 100);

    }

    //    public Action HookUp(){
//        return hangmotor.;
//    }
    public Action HookDown()
    {
        return hangmotor.GoToAngle(0, AngleUnitV2.DEGREES);
    }
//    public Action HoldPosition(){
//        return
//    }
}
