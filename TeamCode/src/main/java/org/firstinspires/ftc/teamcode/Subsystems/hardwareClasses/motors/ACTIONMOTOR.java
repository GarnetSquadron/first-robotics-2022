package org.firstinspires.ftc.teamcode.Subsystems.hardwareClasses.motors;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.qualcomm.robotcore.hardware.HardwareMap;

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
}
