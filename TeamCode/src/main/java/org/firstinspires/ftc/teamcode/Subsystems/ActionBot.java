package org.firstinspires.ftc.teamcode.Subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.FieldDimensions;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.MiscActions.CancelableAction;
import org.firstinspires.ftc.teamcode.Pipelines.SamplePipeline;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.outake.Outtake;
import org.firstinspires.ftc.teamcode.Subsystems.outake.PrimaryOuttakePivot;
import org.firstinspires.ftc.teamcode.commands.HeadlessDriveCommand;
import org.opencv.core.Point;

import java.util.function.DoubleSupplier;

/**
 * I need to make some big changes to the current structure of the bot class, so this is a temporary class that Im going to store the new version in case I still want to use the old version
 */
public class ActionBot {
    public MecanumDrive drive;
    //public Vision vision;
    public Pose2d beginPose = new Pose2d(0,0,0);
    public HeadlessDriveCommand headlessDriveCommand;

    public Outtake outtake;

    public PrimaryOuttakePivot outtakePivot;

    public Intake intake;
    public boolean transfering = false;
    public final double robotWidth = 9;
    Pose2d intakePos = new Pose2d(0,0,0);
//    Action path = drive.actionBuilder(beginPose)
//            .splineToSplineHeading(intakePos,0)
//            .build();


    public ActionBot(HardwareMap hardwareMap, GamepadEx Gpad1, Telemetry telemetry, DoubleSupplier time){
        drive = new MecanumDrive(hardwareMap,beginPose);
        headlessDriveCommand = new HeadlessDriveCommand(drive,Gpad1::getLeftX,Gpad1::getLeftY,Gpad1::getRightX);
        outtake = new Outtake(hardwareMap,time);
        outtakePivot = new PrimaryOuttakePivot(hardwareMap,time);
        intake = new Intake(hardwareMap,time);
        //vision = new Vision(hardwareMap,telemetry);
    }


//    /**
//     * meant to be looped, like a lot of this stuff
//     */
//    public void VisionGrab(){
//        if(vision.streamOpened){
//            SamplePipeline.AnalyzedStone Sample =  vision.getNearestSample();
//        }
//    }

    //region actions
    /**
     * grabs at a sample givien its coords
     * @param p the coords of the sample relative to the midpoint of the barrier
     */

    public Action GrabInSub(Point p){
        Point q = new Point(p.x,FieldDimensions.subLength-p.y);
        double angle = 0;
        double length = 0;
        if(q.y>intake.littleClawArmThingyLength){

            double x2 = FieldDimensions.innerSubWidth -Math.abs(q.x);
            if(q.x<intake.leftClawLength-FieldDimensions.innerSubWidth){

                double h = Math.hypot(q.x, q.y);
                angle = Math.acos(intake.leftClawLength/h)+Math.atan(q.y/x2);
                angle=Math.PI-angle;
            }
            if(q.x>FieldDimensions.innerSubWidth -intake.rightClawLength){

                double h = Math.hypot(x2, q.y);
                angle = Math.acos(intake.rightClawLength/h)+Math.atan(q.y/x2);
                angle=Math.PI-angle;
            }
        }
        length = Math.pow(intake.littleClawArmThingyLength,2)-Math.pow(q.y,2)-2*q.y*intake.littleClawArmThingyLength*Math.cos(angle);//Law of cosines
        return new SequentialAction(PositionClaw(p,angle,length));

    }



    public Action PositionClaw(Point tgtp, double length, double angle){
        Pose2d botPos = new Pose2d(tgtp.x+length*Math.sin(angle),tgtp.y-length*Math.cos(angle), angle);

        return new CancelableAction( new ParallelAction( drive.StraightTo(botPos),new InstantAction(()->intake.crankSlide.goToLengthInInches(length))),drive.Stop());
    }
    public Action Transfer(){
        return new SequentialAction(
                new ParallelAction(
                        outtake.claw.Open(),
                        intake.claw.Close()
                ),
                intake.DefaultPos(),
                outtake.TransferPos(),
                outtake.claw.Close(),
                intake.claw.Open()

        );
    }

    //endregion

}
