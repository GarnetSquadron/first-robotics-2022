package org.firstinspires.ftc.teamcode.SeasonSpecific.OpModes.autonomi;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.depricated.NonDriveHardware;
import org.firstinspires.ftc.teamcode.pathing.roadrunner.drives.MecanumDrive;

@TeleOp(name = "JAMANUELS CHALLENGE")
@Disabled
public class JamuelsChallenge extends LinearOpMode
{
    public void runOpMode()
    {
        Pose2d beginPose = new Pose2d(0, 0, 0);
        NonDriveHardware.Arm arm = new NonDriveHardware.Arm(hardwareMap, 630);
        NonDriveHardware.Launcher Launcher = new NonDriveHardware.Launcher(hardwareMap);
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        waitForStart();
//        VelConstraint baseVelConstraint = new MinVelConstraint(Arrays.asList(
//                new TranslationalVelConstraint(50.0)
////                new AngularVelConstraint(Math.PI / 2)
//        ));
        Action path1 = drive.actionBuilder(beginPose).splineToConstantHeading(new Vector2d(48, 24), 0).build();
        Action path2 = drive.actionBuilder(beginPose).splineToConstantHeading(new Vector2d(0, 24), 0).build();
        Action path3 = drive.actionBuilder(beginPose).splineToConstantHeading(new Vector2d(48, 0), 0).build();
        Action path4 = drive.actionBuilder(beginPose).splineToSplineHeading(new Pose2d(48, 0, 0), 0).build();
        Actions.runBlocking(

                new ParallelAction(
                        NonDriveHardware.DcMotorActions.handleMovement(),
                        new SequentialAction(
                                new ParallelAction(
                                        NonDriveHardware.DcMotorActions.runTo(300),
                                        path1
                                ),
                                new SleepAction(3),
                                path2,
                                new SleepAction(3),
                                new ParallelAction(
                                        NonDriveHardware.Launcher.LaunchPlane(),
                                        path3
                                ),
                                NonDriveHardware.DcMotorActions.Disable()
                        )

                )
        );
    }

}
