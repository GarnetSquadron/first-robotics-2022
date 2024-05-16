package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "\uD83D\uDE08 \uD83D\uDE08 JAMANUEL TEST ME PLZ\uD83D\uDE08 \uD83D\uDE08")
public class VoidsAndThingsTest extends LinearOpMode {
    VoidsAndThings voidsAndThings;

    @Override
    public void runOpMode() {
        voidsAndThings = new VoidsAndThings(hardwareMap);
        voidsAndThings.initHardware();
        waitForStart();
        voidsAndThings.forward(0.25,10);

    }
}
