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
import org.firstinspires.ftc.teamcode.InitialToggler;
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
    InitialToggler intakeDeployToggle, intakeClawToggle, outtakeClawToggle, viperToggle, outtakePivotToggle;
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

        intakeDeployToggle = new InitialToggler(Con2::X);
        viperToggle = new InitialToggler(Con2::RightBumper);
        outtakePivotToggle = new InitialToggler(Con2::LeftBumper);
        intakeClawToggle = new InitialToggler(Con2::Y);
        outtakeClawToggle = new InitialToggler(Con2::B);


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

        actionScheduler.actionTogglePair(intakeDeployToggle,bot.intake.deploy(1),"deploy intake",bot.intake.undeploy(),"undeploy intake");
        actionScheduler.actionTogglePair(intakeClawToggle,bot.intake.claw.Open(),"open intake claw",bot.intake.claw.Close(),"close intake claw");
        actionScheduler.actionTogglePair(outtakeClawToggle,bot.outtake.claw.Open(),"open outtake claw",bot.outtake.claw.Close(),"close intake claw");
        actionScheduler.actionTogglePair(viperToggle,bot.outtake.vipers.Up(),"vipers up",bot.outtake.vipers.Down(),"vipers up");
        bot.intake.wrist.wrist.changePosBy(Math.signum(gamepad2.left_stick_x)*0.01);

        if(gamepad2.dpad_left){
            actionScheduler.cancelAll();
            actionScheduler.start(bot.Transfer(),"transfer");
        }
        if(gamepad2.left_trigger>0.1) {
            actionScheduler.cancelAll();
            actionScheduler.start(bot.outtake.BasketDrop(),"basket drop");
        }
        bot.headlessDriveCommand.execute();

        telemetry.addData("outtake claw time left", bot.outtake.claw.claw.servo.timer.timeLeft());


        telemetry.addData("CURRENT ACTIONS", actionScheduler.getActionIDs());
        telemetry.update();
        actionScheduler.update();
    }
}
