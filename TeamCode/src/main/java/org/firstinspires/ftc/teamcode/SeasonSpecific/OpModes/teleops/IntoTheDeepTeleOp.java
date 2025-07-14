package org.firstinspires.ftc.teamcode.SeasonSpecific.OpModes.teleops;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.GamepadClasses.BetterControllerClass;
import org.firstinspires.ftc.teamcode.OpmodeActionSceduling.TeleOpActionScheduler;
import org.firstinspires.ftc.teamcode.StaticInfo;
import org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff.Bot;
import org.firstinspires.ftc.teamcode.enums.AngleUnitV2;
import org.firstinspires.ftc.teamcode.enums.Color;
import org.firstinspires.ftc.teamcode.inputmodifiers.InitialToggler;
import org.firstinspires.ftc.teamcode.inputmodifiers.risingEdgeDetector;
import org.firstinspires.ftc.teamcode.time.TIME;

@TeleOp(name = "AA \uD83E\uDD3F \uD83D\uDC1F ####INTOTHEDEEPTELEOP#### \uD83D\uDC1F \uD83E\uDD3F")
@Disabled
public class IntoTheDeepTeleOp extends OpMode
{
    Bot bot;
    GamepadEx Gpad1, Gpad2;
    BetterControllerClass Con1, Con2;
    Color AlianceColor = Color.RED;
    GamepadButton intakeDeployButton;
    InitialToggler intakeDeployToggle, intakeClawToggle, outtakeClawToggle, viperToggle, grabOffWallToggle;
    risingEdgeDetector transferDetector, wristGoLeft, wristGoRight, SpecimenGrabPosButton, SpecimenPlaceButton, viperUpdateButton, lowBasketDeploy, UltimatePivotResetButton;
    TeleOpActionScheduler actionScheduler;
    TelemetryPacket packet;
    double sensitivity = 1;

    @Override
    public void init()
    {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        packet = new TelemetryPacket();


        Gpad1 = new GamepadEx(gamepad1);
        Gpad2 = new GamepadEx(gamepad2);
        Con1 = new BetterControllerClass(gamepad1);
        Con2 = new BetterControllerClass(gamepad2);


        if (StaticInfo.LastOpModeWasAuto) {
            bot = new Bot(hardwareMap, telemetry, this::getRuntime);
        } else {
            bot = new Bot(hardwareMap, telemetry, this::getRuntime,
                    new Pose2d(0, 0, Math.toRadians(90))
            );
        }

        intakeDeployToggle = new InitialToggler(Con2::X);
        viperToggle = new InitialToggler(Con2::LeftTrigger);
        intakeClawToggle = new InitialToggler(Con2::Y);
        outtakeClawToggle = new InitialToggler(Con2::B);
        grabOffWallToggle = new InitialToggler(Con2::DpadUp);
        transferDetector = new risingEdgeDetector(Con2::A);
        SpecimenGrabPosButton = new risingEdgeDetector(Con2::DpadUp);
        SpecimenPlaceButton = new risingEdgeDetector(Con2::RightTrigger);
        wristGoLeft = new risingEdgeDetector(Con2::LeftBumper);
        wristGoRight = new risingEdgeDetector(Con2::RightBumper);
        UltimatePivotResetButton = new risingEdgeDetector(Con2::DpadLeft);

        viperUpdateButton = new risingEdgeDetector(Con2::DpadRight);

        lowBasketDeploy = new risingEdgeDetector(Con2::DpadDown);


        actionScheduler = new TeleOpActionScheduler(packet);
        actionScheduler.CancelOnAnyOtherAction("transfer", "basket drop");
        //actionScheduler.addCancelGroup("antiTransfer",);

    }

    boolean firstiter = true;


    @Override
    public void loop()
    {
        double time1 = TIME.getTime();
        if (firstiter) {
            actionScheduler.start(bot.outtake.pivot1.zeroMotor(), "zero outtake pivot");
            //actionScheduler.start(bot.outtake.vipers.Down(),"zero vipers");
            actionScheduler.start(bot.UpdateMotorPowers(), "updating motor powers");
            firstiter = false;
        }
        //bot.outtake.vipers.l.updateDistance();
        StaticInfo.LastOpModeWasAuto = false;
        //update the value of each rising edge button detector so we don't miss a button press
        outtakeClawToggle.updateValue();
        intakeClawToggle.updateValue();
        viperToggle.updateValue();
        intakeDeployToggle.updateValue();
        grabOffWallToggle.updateValue();

        transferDetector.update();
        wristGoLeft.update();
        wristGoRight.update();
        //SpecimenGrabPosButton.update();
        SpecimenPlaceButton.update();

        viperUpdateButton.update();

        lowBasketDeploy.update();

        //These are the controls for several mechanisms that have two states.
        //These take the input of a rising edge detector and toggles between those states.
        //this effectively allows a button to toggle that state.
        actionScheduler.actionBooleanPair(
                intakeDeployToggle.JustChanged(),
                !bot.intake.crankSlide.IsExtended(),
                bot.SafeDeployIntake(1), "deploy intake",
                bot.SafeUndeployIntake(), "undeploy intake"
        );
        if (bot.intake.crankSlide.IsExtended()) {
            actionScheduler.actionBooleanPair(
                    intakeClawToggle.JustChanged(),
                    bot.intake.claw.isOpen(),
                    bot.IntakeDropSample(), "open intake claw",
                    bot.IntakeGrab(), "close intake claw"
            );
        }
        actionScheduler.actionBooleanPair(
                outtakeClawToggle.JustChanged(), bot.outtake.claw.isOpen(),
                bot.outtake.claw.Close(), "close outtake claw",
                bot.outtake.claw.Open(), "open outtake claw"
        );
        actionScheduler.actionBooleanPair(
                viperToggle.JustChanged(), bot.outtake.vipers.isDown(),
                bot.TeleBasketDrop(), "vipers up",
                bot.outtake.SafeVipersDown(), "vipers down"
        );
        actionScheduler.actionBooleanPair(
                grabOffWallToggle.JustChanged(), bot.outtake.isGrabbingOffWall(),
                bot.outtake.prepareToGrabSpecOffWall(), "preparing to grab spec",
                bot.outtake.grabSpecPos(), "grabbing spec"
        );
        actionScheduler.actionBooleanPair(
                SpecimenPlaceButton.getState(), bot.outtake.readyForClip(),
                bot.outtake.placeSpec(), "Place Specimen",
                bot.outtake.prepareToPlaceSpec(), "preparing to grab spec"
        );
        if (UltimatePivotResetButton.getState()) {
            actionScheduler.start(bot.outtake.pivot1.zeroMotor(), "zeroingPivot");
        }

        //misc controls
        if (wristGoLeft.getState()) {
            actionScheduler.start(bot.intake.wrist.wrist.changeAngleByRad(1), "wrist turning");
        }
        if (wristGoRight.getState()) {
            actionScheduler.start(bot.intake.wrist.wrist.changeAngleByRad(-1), "wrist turning");
        }

        if (transferDetector.getState()) {
            //actionScheduler.cancelAll();
            actionScheduler.start(bot.Transfer(), "transfer");
        }
//        if(SpecimenGrabPosButton.getState()){
//            //actionScheduler.cancelAll();
//            actionScheduler.start(bot.outtake.grabSpecPos(),"Grab Specimen");
//        }
        if (SpecimenPlaceButton.getState()) {
            //actionScheduler.cancelAll();

        }
//        if(!(
//                actionScheduler.TeleOpActionRunning("transfer")||
//                        actionScheduler.TeleOpActionRunning("deploy intake")||
//                        actionScheduler.TeleOpActionRunning("undeploy intake")
//        )||gamepad2.left_stick_y>0){
//            actionScheduler.start(bot.intake.crankSlide.goToLengthInInches(18-11*gamepad2.left_stick_y),"adjusting length");
//        }
        //^^^ coming soon!!!

        if (viperUpdateButton.getState()) {
            bot.outtake.vipers.updatePos();//avoid doing this when something is already moving, because this will put a 0.03
        }
        if (lowBasketDeploy.getState()) {
            actionScheduler.start(bot.LowBasketDrop(), "Low Basket");
        }


        //wheels driver
        bot.headlessDriveCommand.execute(
                Gpad1::getLeftX, Gpad1::getLeftY,
                Gpad1::getRightX, sensitivity
        );
        if (gamepad1.y) {
            bot.drive.SetDirectionTo(Math.PI / 2, AngleUnitV2.RADIANS);
        }
        sensitivity = 0.5;
        if (gamepad1.left_bumper) {
            sensitivity = 0.2;
        }
        if (gamepad1.right_bumper) {
            sensitivity = 1;
        }


        //telemetry.addData("left stick y", gamepad2.left_stick_y);

        telemetry.addData("outtake pivot power", bot.outtake.pivot1.pivot.getPower());
        telemetry.addData("outtake pivot degrees", Math.toDegrees(bot.outtake.pivot1.pivot.getEncoder().getPos()));
        telemetry.addData("outtake pivot tgt degrees", Math.toDegrees(bot.outtake.pivot1.pivot.getTargetPosition()));
        telemetry.addData("outtake pivot target reached", bot.outtake.pivot1.pivot.targetReached());
        telemetry.addData("outtake velocity", bot.outtake.pivot1.pivot.getEncoder().getVelocity());
        telemetry.addData("outtake distanceToTarget", bot.outtake.pivot1.pivot.getTargetPosition() - bot.outtake.pivot1.pivot.getEncoder().getPos());

        telemetry.addLine("");
        telemetry.addLine("----------------------------------------------------------------------");
        telemetry.addLine("");

        telemetry.addData("vipers down", bot.outtake.vipers.isDown());
        telemetry.addData("left viper power", bot.outtake.vipers.l.getPower());
        telemetry.addData("viper inches", bot.outtake.vipers.l.getEncoder().getPos());
        telemetry.addData("viper tgt inches", bot.outtake.vipers.l.getTargetPosition());
        telemetry.addData("viper tgt reached", bot.outtake.vipers.l.targetReached());
        telemetry.addData("right viper power", bot.outtake.vipers.r.getPower());
        telemetry.addData("viper position controlling", bot.outtake.vipers.l.inPositionControl());
        telemetry.addData("vipers stopped", bot.outtake.vipers.l.getEncoder().isStopped());
        telemetry.addData("viper speed", bot.outtake.vipers.l.getEncoder().getVelocity());
        telemetry.addData("time diff", time1 - TIME.getTime());


//        telemetry.addData("grabbing ",bot.outtake.isGrabbingOffWall());
//        //telemetry.addData("pivot target ",bot.outtake.pivot1.pivot.getTargetPos());
//
//
//        telemetry.addData("direction", MecanumDrive.pose.heading.toDouble());
//        telemetry.addData("x", MecanumDrive.pose.position.x);
//        telemetry.addData("y", MecanumDrive.pose.position.y);
        telemetry.addData("CURRENT ACTIONS", actionScheduler.getActionIDs());
        telemetry.update();
        FtcDashboard.getInstance().sendTelemetryPacket(packet);
        actionScheduler.update();
    }

}
