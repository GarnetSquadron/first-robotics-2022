package org.firstinspires.ftc.teamcode.Subsystems.hardwareClasses.motors;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.EthernetOverUsbConfiguration;

import org.firstinspires.ftc.teamcode.InitialToggler;
import org.firstinspires.ftc.teamcode.MiscActions.ActionUntillOneIsDone;
import org.firstinspires.ftc.teamcode.MiscActions.ConditionalAction;
import org.firstinspires.ftc.teamcode.MiscActions.WaitForConditionAction;

import java.util.function.DoubleSupplier;

public class ACTIONMOTOR extends UpdatableMOTOR {
    public ACTIONMOTOR(HardwareMap hardwareMap, String name) {
        super(hardwareMap, name);
    }
    public Action runToPosition(double target){
        return new SequentialAction(
                new InstantAction(()->setTargetPosition(target)),
                new WaitForConditionAction(this::targetReached)
        );
    }
    public Action runWithPowerUntilStopped(double power,double timeTillMovement){
        return (
                new SequentialAction(
                        new InstantAction(()->setTargetPower(power)),
                        new SleepAction(timeTillMovement),
                        new WaitForConditionAction(encoder::isStopped),
                        new InstantAction(()->setTargetPower(0))
                )
        );
    }

}
