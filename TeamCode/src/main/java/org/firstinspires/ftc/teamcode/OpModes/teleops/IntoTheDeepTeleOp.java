package org.firstinspires.ftc.teamcode.OpModes.teleops;

import android.widget.ToggleButton;

import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.ToggleButtonReader;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.BetterControllerClass;
import org.firstinspires.ftc.teamcode.BooleanToggler;
import org.firstinspires.ftc.teamcode.OpmodeActionSceduling.TeleOpActionScheduler;
import org.firstinspires.ftc.teamcode.Subsystems.ActionBot;
import org.firstinspires.ftc.teamcode.enums.Color;

@TeleOp(name = "INTOTHEDEEP TELEOP ", group = "AAA TELEOPS")
public class IntoTheDeepTeleOp extends OpMode {
    ActionBot bot;
    GamepadEx Gpad1, Gpad2;
    BetterControllerClass Con1,Con2;
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
    BooleanToggler intakeDeployToggle, intakeClawToggle, outtakeClawToggle, viperToggle, outtakePivotToggle;
    GamepadButton transferButton;
    ToggleButtonReader transferToggle;
    TeleOpActionScheduler actionScheduler;
    @Override
    public void init() {
        Gpad1 = new GamepadEx(gamepad1);
        Gpad2 = new GamepadEx(gamepad2);
        Con1 = new BetterControllerClass(gamepad1);
        Con2 = new BetterControllerClass(gamepad2);


        bot = new ActionBot(hardwareMap,Gpad1,telemetry,this::getRuntime);

        intakeDeployToggle = new BooleanToggler(Con2::X);
        viperToggle = new BooleanToggler(Con2::RightBumper);
        outtakePivotToggle = new BooleanToggler(Con2::LeftBumper);
        intakeClawToggle = new BooleanToggler(Con2::Y);
        outtakeClawToggle = new BooleanToggler(Con2::B);


        actionScheduler = new TeleOpActionScheduler();
        actionScheduler.CancelOnAnyOtherAction(bot.Transfer(),bot.outtake.BasketDrop());
    }
    boolean firstiter = true;



    @Override
    public void loop() {
        outtakeClawToggle.updateValue();
        intakeClawToggle.updateValue();
        viperToggle.updateValue();

        outtakePivotToggle.updateValue();
        intakeDeployToggle.updateValue();

        actionScheduler.actionTogglePair(intakeDeployToggle.getState(),bot.intake.deploy(1),bot.intake.undeploy());
        actionScheduler.actionTogglePair(intakeClawToggle.getState(),bot.intake.claw.Open(),bot.intake.claw.Close());
        actionScheduler.actionTogglePair(outtakeClawToggle.getState(),bot.outtake.claw.Open(),bot.outtake.claw.Close());
        actionScheduler.actionTogglePair(viperToggle.getState(),bot.outtake.vipers.Up(),bot.outtake.vipers.Down());

        bot.intake.wrist.wrist.changePosBy(Math.signum(gamepad2.left_stick_x)*0.01);

        if(gamepad2.dpad_left){
            actionScheduler.cancelAll();
            actionScheduler.start(bot.Transfer());
        }
        if(gamepad2.left_trigger>0.1) {
            actionScheduler.cancelAll();
            actionScheduler.start(bot.outtake.BasketDrop());
        }
        bot.headlessDriveCommand.execute();

        telemetry.addData("current actions", actionScheduler.getActionIDs());
        telemetry.addData("viper toggle", diyViperToggle.getState());
        telemetry.update();
        actionScheduler.update();
    }
}
