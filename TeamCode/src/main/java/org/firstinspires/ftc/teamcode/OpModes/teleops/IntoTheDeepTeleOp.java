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
    ToggleButtonReader intakeDeployToggle, intakeClawToggle, outtakeClawToggle, viperToggle, outtakePivotToggle;
    GamepadButton transferButton;
    ToggleButtonReader transferToggle;
    TeleOpActionScheduler actionScheduler;
    @Override
    public void init() {
        Gpad1 = new GamepadEx(gamepad1);
        Gpad2 = new GamepadEx(gamepad2);
        bot = new ActionBot(hardwareMap,Gpad1,telemetry,this::getRuntime);

        intakeDeployToggle = new ToggleButtonReader(Gpad2, GamepadKeys.Button.X);
        viperToggle = new ToggleButtonReader(Gpad2, GamepadKeys.Button.RIGHT_BUMPER);
        outtakePivotToggle = new ToggleButtonReader(Gpad2, GamepadKeys.Button.LEFT_BUMPER);
        intakeClawToggle = new ToggleButtonReader(Gpad2,GamepadKeys.Button.A);
        outtakeClawToggle = new ToggleButtonReader(Gpad2, GamepadKeys.Button.B);


        actionScheduler = new TeleOpActionScheduler();
        actionScheduler.CancelOnAnyOtherAction(bot.Transfer(),bot.outtake.BasketDrop());
    }
    boolean firstiter = true;



    @Override
    public void loop() {
        outtakeClawToggle.readValue();
        intakeClawToggle.readValue();
        viperToggle.readValue();
        outtakePivotToggle.readValue();
        intakeDeployToggle.readValue();
        //actionScheduler.actionTogglePair(intakeDeployToggle,);
        if(intakeDeployToggle.getState()){
            actionScheduler.start(bot.intake.deploy(1));//cranks go forward
            actionScheduler.cancel(bot.intake.undeploy());
        }
        else {
            actionScheduler.start(bot.intake.undeploy());
            actionScheduler.cancel(bot.intake.deploy(1));
        }
        if(intakeClawToggle.getState()){
            actionScheduler.start(bot.intake.claw.Open());
            actionScheduler.cancel(bot.intake.claw.Close());
        }
        else {
            actionScheduler.start(bot.intake.claw.Close());
            actionScheduler.cancel(bot.intake.claw.Open());
        }
        if(outtakeClawToggle.getState()){
            actionScheduler.start(bot.outtake.claw.Close());
            actionScheduler.cancel(bot.outtake.claw.Open());
        }
        else {
            actionScheduler.start(bot.outtake.claw.Open());
            actionScheduler.cancel(bot.outtake.claw.Close());
        }
        bot.intake.wrist.wrist.changePosBy(Math.signum(gamepad2.left_stick_x)*0.01);

        if(gamepad2.dpad_left){
            actionScheduler.cancelAll();
            actionScheduler.start(bot.Transfer());
        }


        if(viperToggle.getState()){
            actionScheduler.start(bot.outtake.vipers.Up());
            actionScheduler.cancel(bot.outtake.vipers.Down());
        }
        else {
            actionScheduler.start(bot.outtake.vipers.Down());
            actionScheduler.cancel(bot.outtake.vipers.Up());
        }
        if(gamepad2.left_trigger>0.1) {
            actionScheduler.start(bot.outtake.BasketDrop());
        }


        actionScheduler.update();
        telemetry.addData("left trigger", gamepad2.left_trigger);
        telemetry.addData("current actions", actionScheduler.getActions());
        telemetry.update();
    }
}
