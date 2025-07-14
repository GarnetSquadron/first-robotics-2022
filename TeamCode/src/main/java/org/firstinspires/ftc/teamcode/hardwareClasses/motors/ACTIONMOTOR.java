package org.firstinspires.ftc.teamcode.hardwareClasses.motors;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.MiscActions.WaitForConditionAction;

public class ACTIONMOTOR extends UpdatableMOTOR
{
    public ACTIONMOTOR(HardwareMap hardwareMap, String name)
    {
        super(hardwareMap, name);
    }

    public Action runToPosition(double target)
    {
        return new SequentialAction(
                new InstantAction(() -> setTargetPosition(target)),
                new WaitForConditionAction(this::targetReached)
        );
    }

    public Action runWithPowerUntilStopped(double power, double timeTillMovement)
    {
        return (
                new SequentialAction(
                        new InstantAction(() -> setTargetPower(power)),
                        new SleepAction(timeTillMovement),
                        new WaitForConditionAction(encoder::isStopped),
                        new InstantAction(() -> setTargetPower(0))
                )
        );
    }

}
