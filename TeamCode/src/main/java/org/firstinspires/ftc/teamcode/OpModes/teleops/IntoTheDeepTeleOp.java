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

//        actionScheduler.actionTogglePair(intakeDeployToggle,bot.intake.deploy(1),bot.intake.undeploy());
//        actionScheduler.actionTogglePair(intakeClawToggle,bot.intake.claw.Open(),bot.intake.claw.Close());
//        actionScheduler.actionTogglePair(outtakeClawToggle,bot.outtake.claw.Open(),bot.outtake.claw.Close());
        actionScheduler.actionTogglePair(viperToggle,bot.outtake.vipers.Up(),bot.outtake.vipers.Down());

//        bot.intake.wrist.wrist.changePosBy(Math.signum(gamepad2.left_stick_x)*0.01);
//
//        if(gamepad2.dpad_left){
//            actionScheduler.cancelAll();
//            actionScheduler.start(bot.Transfer());
//        }
//        if(gamepad2.left_trigger>0.1) {
//            actionScheduler.cancelAll();
//            actionScheduler.start(bot.outtake.BasketDrop());
//        }
//        bot.headlessDriveCommand.execute();


        actionScheduler.update();
        telemetry.addData("viper toggle", viperToggle.getState());
        telemetry.addData("current actions", actionScheduler.getActions());
        telemetry.update();
    }
}
