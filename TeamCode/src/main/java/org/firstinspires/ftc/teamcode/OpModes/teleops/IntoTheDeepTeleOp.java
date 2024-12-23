package org.firstinspires.ftc.teamcode.OpModes.teleops;

import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.ToggleButtonReader;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.OpmodeActionSceduling.TeleOpActionScheduler;
import org.firstinspires.ftc.teamcode.Subsystems.ActionBot;
import org.firstinspires.ftc.teamcode.enums.Color;

@TeleOp(name = "INTOTHEDEEP TELEOP ", group = "AAA TELEOPS")
public class IntoTheDeepTeleOp extends OpMode {
    ActionBot bot;
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
    TeleOpActionScheduler actionScheduler;
    @Override
    public void init() {
        Gpad1 = new GamepadEx(gamepad1);
        Gpad2 = new GamepadEx(gamepad2);
        bot = new ActionBot(hardwareMap,Gpad1,telemetry,this::getRuntime);
        intakeDeployButton = new GamepadButton(Gpad2, GamepadKeys.Button.X);
        intakeDeployToggle = new ToggleButtonReader(intakeDeployButton::get);
        actionScheduler = new TeleOpActionScheduler();
        actionScheduler.CancelOnAnyOtherAction(bot.Transfer(),bot.outtake.BasketDrop());
    }
    boolean firstiter = true;



    @Override
    public void loop() {
        if(gamepad2.x){
            actionScheduler.start(bot.intake.deploy(1));//cranks go forward
            actionScheduler.cancel(bot.intake.undeploy());
        }
        if(gamepad2.y) {
            actionScheduler.start(bot.intake.undeploy());
            actionScheduler.cancel(bot.intake.deploy(1));
        }
        if(gamepad2.a){
            actionScheduler.start(bot.intake.claw.Open());
            actionScheduler.cancel(bot.intake.claw.Close());
        }
        if(gamepad2.b) {
            actionScheduler.start(bot.intake.claw.Close());
            actionScheduler.cancel(bot.intake.claw.Open());
        }
        bot.intake.wrist.wrist.changePosBy(Math.signum(gamepad2.left_stick_x)*0.01);

        if(gamepad2.dpad_left){
            actionScheduler.cancelAll();
            actionScheduler.start(bot.Transfer());
        }


        if(gamepad2.right_bumper){
            actionScheduler.start(bot.outtake.vipers.Down());
            actionScheduler.cancel(bot.outtake.vipers.Up());
        }
        if(gamepad2.left_bumper){
            actionScheduler.start(bot.outtake.vipers.Up());
            actionScheduler.cancel(bot.outtake.vipers.Down());
        }
        if(gamepad2.left_trigger>0.1) {
            actionScheduler.start(bot.outtake.BasketDrop());
        }
        if(gamepad2.right_trigger>0.1){

        }

        actionScheduler.update();
        telemetry.addData("left trigger", gamepad2.left_trigger);
        telemetry.addData("current actions", actionScheduler.getActions());
        telemetry.update();
    }
}
