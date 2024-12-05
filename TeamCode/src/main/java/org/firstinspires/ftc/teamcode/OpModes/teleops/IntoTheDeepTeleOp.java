package org.firstinspires.ftc.teamcode.OpModes.teleops;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Bot;
import org.firstinspires.ftc.teamcode.enums.Color;

@TeleOp(name = "INTOTHEDEEP TELEOP", group = "AAA TELEOPS")
public class IntoTheDeepTeleOp extends OpMode {
    Bot bot;
    GamepadEx Gpad1, Gpad2;
    Color AlianceColor = Color.RED;
//    public static void RunHeadlessDrive(MecanumDrive drive, Gamepad gamepad){
//        drive.updatePoseEstimate();
//        double direction = drive.pose.heading.toDouble();
//        drive.setDrivePowers(new PoseVelocity2d(
//                new Vector2d(
//                        -Math.sin(drive.pose.heading.toDouble())*gamepad.left_stick_x-Math.cos(drive.pose.heading.toDouble())*gamepad.left_stick_y,
//                        -Math.cos(drive.pose.heading.toDouble())*gamepad.left_stick_x+Math.sin(drive.pose.heading.toDouble())*gamepad.left_stick_y
//                ),
//                -gamepad.right_stick_x
//        ));
//    }
    @Override
    public void init() {
        Gpad1 = new GamepadEx(gamepad1);
        Gpad2 = new GamepadEx(gamepad2);
        bot = new Bot(hardwareMap,Gpad1);

    }
    boolean firstiter = true;
    @Override
    public void loop() {
        if(gamepad1.x){
            bot.intake.deploy(1);
        }
        if(gamepad1.y) {
            bot.intake.undeploy();
        }
        if(gamepad1.a){
            bot.intake.claw.open();
        }
        if(gamepad1.b) {
            bot.intake.claw.close();
        }
        bot.intake.wrist.runToRatio(gamepad1.left_stick_x);

    }
}
