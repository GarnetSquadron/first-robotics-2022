package org.firstinspires.ftc.teamcode.OpModes.teleops;

import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.ToggleButtonReader;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Bot;
import org.firstinspires.ftc.teamcode.enums.Color;

@TeleOp(name = "INTOTHEDEEP TELEOP ", group = "AAA TELEOPS")
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
    GamepadButton intakeDeployButton;
    ToggleButtonReader intakeDeployToggle;
    GamepadButton transferButton;
    ToggleButtonReader transferToggle;
    @Override
    public void init() {
        Gpad1 = new GamepadEx(gamepad1);
        Gpad2 = new GamepadEx(gamepad2);
        bot = new Bot(hardwareMap,Gpad1);
        intakeDeployButton = new GamepadButton(Gpad2, GamepadKeys.Button.X);
        intakeDeployToggle = new ToggleButtonReader(intakeDeployButton::get);
    }
    boolean firstiter = true;



    @Override
    public void loop() {
        if(gamepad2.x){
            bot.intake.deploy(1);
        }
        if(gamepad2.y) {
            bot.intake.undeploy();
        }
        if(gamepad2.a){
            bot.intake.claw.open();
        }
        if(gamepad2.b) {
            bot.intake.claw.close();
        }
        bot.intake.wrist.wrist.changePosBy(Math.signum(gamepad2.left_stick_x)*0.01);

        if(gamepad2.dpad_left){
            bot.transfer();
        }


        if(gamepad2.right_bumper){
            bot.outtake.vipers.SetTgPosToRetract();
        }
        if(gamepad2.left_bumper){
            bot.outtake.vipers.SetTgPosToExtend();
        }
        bot.outtake.vipers.runToTgPos();
        if(gamepad2.left_trigger>0){
            bot.outtake.BasketDrop();
        }
        if(gamepad2.right_trigger>0){

        }

        bot.runToTargetPos();

        telemetry.addData("transfering",bot.transfering);
        telemetry.update();
    }
}
