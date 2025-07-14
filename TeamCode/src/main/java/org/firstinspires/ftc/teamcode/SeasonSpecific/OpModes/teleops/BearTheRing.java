package org.firstinspires.ftc.teamcode.SeasonSpecific.OpModes.teleops;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.GamepadClasses.BetterControllerClass;
import org.firstinspires.ftc.teamcode.WeddingDrive;
import org.firstinspires.ftc.teamcode.inputmodifiers.risingEdgeDetector;
import org.firstinspires.ftc.teamcode.learningStuf.ArmsStuff;

@TeleOp(name = "Bear The Ring")
public class BearTheRing extends OpMode
{
    WeddingDrive weddingDrive;
    BetterControllerClass controller;
    ArmsStuff arm;
    TelemetryPacket p;

    risingEdgeDetector a, b;

    @Override
    public void init()
    {
        weddingDrive = new WeddingDrive(hardwareMap);
        controller = new BetterControllerClass(gamepad1);
        p = new TelemetryPacket();
        arm = new ArmsStuff(hardwareMap);
        arm.ringArms.setPosition(0);
        a = new risingEdgeDetector(controller::A);
        b = new risingEdgeDetector(controller::B);
    }

    @Override
    public void loop()
    {
        weddingDrive.run(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_y / 2);
        arm.vipers.setpower(gamepad1.left_trigger);
        telemetry.addData("left trigger", gamepad1.left_trigger);
        telemetry.addData("viper pos", arm.vipers.getPos());
        telemetry.addData("vipers in range", arm.vipers.inRange());
        telemetry.addData("gravityPower", arm.vipers.gravityPower);
        telemetry.addData("viper power", arm.vipers.getPower());
        telemetry.addData("arm position", arm.ringArms.getPosition());
        a.update();
        b.update();
        if (a.getState()) {
            //arm.vipers.gravityPower+=0.05;
        }
        if (b.getState()) {
            //arm.vipers.gravityPower-=0.05;
        }
        if (gamepad1.right_trigger == 1) {
            arm.ringArms.setPosition(45);
        }
        arm.ringArms.update();
    }
}
