package org.firstinspires.ftc.teamcode.OpModes.teleops;

import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.BetterControllerClass;
import org.firstinspires.ftc.teamcode.InitialToggler;
import org.firstinspires.ftc.teamcode.OpmodeActionSceduling.TeleOpActionScheduler;
import org.firstinspires.ftc.teamcode.Subsystems.ActionBot;
import org.firstinspires.ftc.teamcode.enums.Color;
import org.firstinspires.ftc.teamcode.risingEdgeDetector;

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
    risingEdgeDetector transferDetector,wristGoLeft, wristGoRight;
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
        transferDetector = new risingEdgeDetector(Con2::A);
        wristGoLeft = new risingEdgeDetector(Con2::LeftTrigger);
        wristGoRight = new risingEdgeDetector(Con2::RightTrigger);


        actionScheduler = new TeleOpActionScheduler();
        actionScheduler.CancelOnAnyOtherAction("transfer","basket drop");


    }
    boolean firstiter = true;



    @Override
    public void loop() {
        outtakeClawToggle.updateValue();
        intakeClawToggle.updateValue();
        viperToggle.updateValue();
        outtakePivotToggle.updateValue();
        intakeDeployToggle.updateValue();

        transferDetector.update();
        wristGoLeft.update();
        wristGoRight.update();


        actionScheduler.actionTogglePair(intakeDeployToggle,bot.SafeUndeploy(),"undeploy intake",bot.SafeDeploy(1),"deploy intake");
        actionScheduler.actionTogglePair(intakeClawToggle,bot.intake.claw.Open(),"open intake claw",bot.intake.claw.Close(),"close intake claw");
        actionScheduler.actionTogglePair(outtakeClawToggle,bot.outtake.claw.Open(),"open outtake claw",bot.outtake.claw.Close(),"close outtake claw");
        actionScheduler.actionTogglePair(viperToggle,bot.outtake.vipers.Up(),"vipers up",bot.outtake.vipers.Down(),"vipers down");


        if(wristGoLeft.getState()){
            actionScheduler.start(bot.intake.wrist.wrist.changePosBy(0.05),"wrist turning");
        }
        if (wristGoRight.getState()){
            actionScheduler.start(bot.intake.wrist.wrist.changePosBy(-0.05),"wrist turning");
        }

        if(transferDetector.getState()){
            actionScheduler.cancelAll();
            actionScheduler.start(bot.Transfer(),"transfer");
        }
        if(gamepad2.dpad_left) {
            actionScheduler.cancelAll();
            actionScheduler.start(bot.outtake.BasketDrop(),"basket drop");
        }
        bot.headlessDriveCommand.execute();

        //telemetry.addData("viper tgt pos", bot.outtake.vipers.GetTgtPos());
        //telemetry.addData("viper distance to target", bot.outtake.vipers.DistanceToTarget());


        telemetry.addData("",bot.outtake.pivot1.pivot.servo.timer.timeLeft());

        telemetry.addData("CURRENT ACTIONS", actionScheduler.getActionIDs());
        telemetry.update();
        actionScheduler.update();
    }
}
