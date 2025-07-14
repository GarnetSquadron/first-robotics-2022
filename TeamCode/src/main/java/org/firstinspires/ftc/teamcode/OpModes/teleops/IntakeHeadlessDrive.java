package org.firstinspires.ftc.teamcode.OpModes.teleops;

import static com.arcrobotics.ftclib.gamepad.GamepadKeys.Trigger.LEFT_TRIGGER;

import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff.Intake.CrankSlideSubSystem;
import org.firstinspires.ftc.teamcode.commands.IntakeCenteredHeadlessDrive;
import org.firstinspires.ftc.teamcode.pathing.roadrunner.drives.MecanumDrive;

public class IntakeHeadlessDrive extends OpMode
{
    IntakeCenteredHeadlessDrive driveCommand;
    MecanumDrive drive;
    Pose2d beginPose = new Pose2d(0, 0, 0);
    DcMotorEx lf, rf, lb, rb;
    static GamepadEx Gpad1, Gpad2;
    CrankSlideSubSystem crank;

    public static double getLeftTrigger()
    {
        return Gpad1.getTrigger(LEFT_TRIGGER);
    }

    @Override
    public void init()
    {
        Gpad1 = new GamepadEx(gamepad1);
        Gpad2 = new GamepadEx(gamepad2);
        crank = new CrankSlideSubSystem(hardwareMap, this::getRuntime);

        lb = hardwareMap.get(DcMotorEx.class, "lb");
        rb = hardwareMap.get(DcMotorEx.class, "rb");
        lf = hardwareMap.get(DcMotorEx.class, "lf");
        rf = hardwareMap.get(DcMotorEx.class, "rf");

        drive = new MecanumDrive(hardwareMap, beginPose);
        driveCommand = new IntakeCenteredHeadlessDrive(drive, Gpad1::getLeftX, Gpad1::getLeftY, Gpad1::getRightX, IntakeHeadlessDrive::getLeftTrigger);
    }

    @Override
    public void loop()
    {
        driveCommand.execute();
        telemetry.addData("lb current", lb.getCurrent(CurrentUnit.AMPS));
        telemetry.addData("rb current", rb.getCurrent(CurrentUnit.AMPS));
        telemetry.addData("lf current", lf.getCurrent(CurrentUnit.AMPS));
        telemetry.addData("rf current", rf.getCurrent(CurrentUnit.AMPS));
        telemetry.update();
    }
}