package org.firstinspires.ftc.teamcode.Subsystems.outake;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.function.DoubleSupplier;

public class Outtake {
    public ViperSlidesSubSystem vipers;
    public OuttakeClaw claw;
    public PrimaryOuttakePivot pivot1;
    public SecondaryOuttakePivot pivot2;
    boolean BasketDropping = false;

    public Outtake(HardwareMap hardwareMap, DoubleSupplier time) {
        claw = new OuttakeClaw(hardwareMap, time);
        pivot1 = new PrimaryOuttakePivot(hardwareMap, time);
        pivot2 = new SecondaryOuttakePivot(hardwareMap, time);
        vipers = new ViperSlidesSubSystem(hardwareMap);
    }

    /**
     * this function is meant to be looped
     */
    public Action TransferPos() {
        return new ParallelAction(
                pivot1.TransferPos(),
                pivot2.TransferPos(),
                vipers.Down()
        );
    }
    public Action BasketDrop() {
        return new SequentialAction(
                claw.Close(),
                new ParallelAction(
                        pivot1.BucketPos(),
                        pivot2.BucketPos(),
                        vipers.Up()
                )
        );
    }
}
