package org.firstinspires.ftc.teamcode.OpModes.teleops;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.GamepadClasses.BetterControllerClass;
import org.firstinspires.ftc.teamcode.WeddingDrive;
import org.firstinspires.ftc.teamcode.depricated.NonDriveHardware;
import org.firstinspires.ftc.teamcode.learningStuf.ArmsStuff;

@TeleOp(name = "Bear The Ring")
public class BearTheRing extends OpMode {
    WeddingDrive weddingDrive;
    BetterControllerClass controller;
    ArmsStuff arm;
    TelemetryPacket p;
    @Override
    public void init() {
        weddingDrive = new WeddingDrive(hardwareMap);
        controller = new BetterControllerClass(gamepad1);
        p = new TelemetryPacket();
        arm = new ArmsStuff(hardwareMap);
    }

    @Override
    public void loop() {
        weddingDrive.run(gamepad1.left_stick_y,gamepad1.left_stick_x,gamepad1.right_stick_y);
        arm.funToUp().run(p);
    }
}
