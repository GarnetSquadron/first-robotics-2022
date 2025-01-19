package org.firstinspires.ftc.teamcode.OpModes.teleops;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.BetterControllerClass;
import org.firstinspires.ftc.teamcode.InitialToggler;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.OpmodeActionSceduling.TeleOpActionScheduler;
import org.firstinspires.ftc.teamcode.Subsystems.Bot;
import org.firstinspires.ftc.teamcode.Subsystems.StaticInfo;
import org.firstinspires.ftc.teamcode.enums.Color;
import org.firstinspires.ftc.teamcode.risingEdgeDetector;

@TeleOp(name = "AA \uD83E\uDD3F \uD83D\uDC1F ####INTOTHEDEEPTELEOP#### \uD83D\uDC1F \uD83E\uDD3F", group = "AAA TELEOPS")
public class IntoTheDeepTeleOp extends OpMode {
    Bot bot;
    GamepadEx Gpad1, Gpad2;
    BetterControllerClass Con1,Con2;
    Color AlianceColor = Color.RED;
    GamepadButton intakeDeployButton;
    InitialToggler intakeDeployToggle, intakeClawToggle, outtakeClawToggle, viperToggle, outtakePivotToggle;
    risingEdgeDetector transferDetector,wristGoLeft, wristGoRight, IntakeTransferPosDet,OuttakeTransferPosDet;
    TeleOpActionScheduler actionScheduler;
    TelemetryPacket packet;
    double sensitivity = 1;
    @Override
    public void init() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        packet = new TelemetryPacket();


        Gpad1 = new GamepadEx(gamepad1);
        Gpad2 = new GamepadEx(gamepad2);
        Con1 = new BetterControllerClass(gamepad1);
        Con2 = new BetterControllerClass(gamepad2);


//        if(StaticInfo.LastOpModeWasAuto){
//            bot = new Bot(hardwareMap, telemetry, this::getRuntime);
//        }
//        else{
            bot = new Bot(hardwareMap, telemetry, this::getRuntime,
                    new Pose2d(0, 0, Math.toRadians(90)));
        //}
        StaticInfo.LastOpModeWasAuto = false;

        intakeDeployToggle = new InitialToggler(Con2::X);
        viperToggle = new InitialToggler(Con2::LeftTrigger);
        outtakePivotToggle = new InitialToggler(Con2::RightTrigger);
        intakeClawToggle = new InitialToggler(Con2::Y);
        outtakeClawToggle = new InitialToggler(Con2::B);
        transferDetector = new risingEdgeDetector(Con2::A);
        IntakeTransferPosDet = new risingEdgeDetector(Con2::DpadUp);
        OuttakeTransferPosDet = new risingEdgeDetector(Con2::DpadDown);
        wristGoLeft = new risingEdgeDetector(Con2::LeftBumper);
        wristGoRight = new risingEdgeDetector(Con2::RightBumper);


        actionScheduler = new TeleOpActionScheduler(packet);
        actionScheduler.CancelOnAnyOtherAction("transfer","basket drop");

    }
    boolean firstiter = true;



    @Override
    public void loop() {
        //update the value of each rising edge button detector so we don't miss a button press
        outtakeClawToggle.updateValue();
        intakeClawToggle.updateValue();
        viperToggle.updateValue();
        outtakePivotToggle.updateValue();
        intakeDeployToggle.updateValue();

        transferDetector.update();
        wristGoLeft.update();
        wristGoRight.update();
        IntakeTransferPosDet.update();
        OuttakeTransferPosDet.update();

        //These are the controls for several mechanisms that have two states.
        //These take the input of a rising edge detector and toggles between those states.
        //this effectively allows a button to toggle that state.
        actionScheduler.actionBooleanPair(
                intakeDeployToggle.JustChanged(),
                !bot.intake.crankSlide.IsExtended(),
                bot.SafeDeployIntake(1), "deploy intake",
                bot.SafeUndeployIntake(), "undeploy intake"
        );
        actionScheduler.actionBooleanPair(
                intakeClawToggle.JustChanged(),
                bot.intake.claw.isOpen(),
                bot.intake.claw.Open(),"open intake claw",
                bot.intake.claw.Close(), "close intake claw"
        );
        actionScheduler.actionBooleanPair(
                outtakeClawToggle.JustChanged(), bot.outtake.claw.isOpen(),
                bot.outtake.claw.Close(), "close outtake claw",
                bot.outtake.claw.Open(),"open outtake claw"
        );
        actionScheduler.actionBooleanPair(
                viperToggle.JustChanged(),bot.outtake.vipers.isDown(),
                bot.BasketDrop(),"vipers up",
                bot.outtake.SafeVipersDown(),"vipers down"
        );

        //misc controls
        if(wristGoLeft.getState()){
            actionScheduler.start(bot.intake.wrist.wrist.changePosBy(0.2),"wrist turning");
        }
        if (wristGoRight.getState()){
            actionScheduler.start(bot.intake.wrist.wrist.changePosBy(-0.2),"wrist turning");
        }

        if(transferDetector.getState()){
            actionScheduler.cancelAll();
            actionScheduler.start(bot.Transfer(),"transfer");
        }
        if(IntakeTransferPosDet.getState()){
            actionScheduler.cancelAll();
            actionScheduler.start(bot.intake.DefaultPos(),"Intaketransfer");
        }
        if(OuttakeTransferPosDet.getState()){
            actionScheduler.cancelAll();
            actionScheduler.start(bot.outtake.TransferPos(),"Outtaketransfer");
        }
        if(gamepad2.dpad_left) {
            actionScheduler.cancelAll();
            actionScheduler.start(bot.BasketDrop(),"basket drop");
        }
        bot.headlessDriveCommand.execute(
                Gpad1::getLeftX,Gpad1::getLeftY,
                Gpad1::getRightX,sensitivity
        );
        if(gamepad1.y){
            bot.drive.SetPosTo(new Pose2d(0,0,Math.PI/2));
        }
        sensitivity = 0.5;
        if(gamepad1.left_bumper){
            sensitivity = 0.2;
        }
        if(gamepad1.right_bumper){
            sensitivity = 1;
        }



//        telemetry.addData("l viper ticks", bot.outtake.vipers.l.getPos());
//        telemetry.addData("r viper ticks", bot.outtake.vipers.r.getPos());
//        telemetry.addData("l viper power", bot.outtake.vipers.l.getPower());
//        telemetry.addData("r viper power", bot.outtake.vipers.r.getPower());

        telemetry.addData("outtake claw open",bot.outtake.claw.isOpen());

        telemetry.addData("direction", MecanumDrive.pose.heading.toDouble());

        telemetry.addData("CURRENT ACTIONS", actionScheduler.getActionIDs());
        telemetry.update();
        FtcDashboard.getInstance().sendTelemetryPacket(packet);
        actionScheduler.update();
    }

}
