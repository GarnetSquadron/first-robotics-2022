package org.firstinspires.ftc.teamcode.OpModes.autonomi;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Trajectory;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.Subsystems.ColorSensorSubSystem;
import org.firstinspires.ftc.teamcode.Subsystems.TriangleIntake;
import org.firstinspires.ftc.teamcode.commands.TriangleIntakeCommand;
import org.firstinspires.ftc.teamcode.Subsystems.ViperSlidesSubSystem;
import org.firstinspires.ftc.teamcode.Subsystems.TriangleIntake;
import org.firstinspires.ftc.teamcode.oldStuff.VoidsAndThings;

public class AnthonyAuto extends LinearOpMode {
    TriangleIntake triangleIntake;
    ViperSlidesSubSystem viperSlidesSubsystem;
    TriangleIntake triangleIntake = new TriangleIntake(hardwareMap,"IntakeServo1", "IntakeServo2", "IntakeServo3","pivot");
    DcMotor lf;
    DcMotor rf;
    DcMotor lb;
    DcMotor rb;
    public void RedAuto() {
        Pose2d StartPose = new Pose2d(54, -36, Math.toRadians(180));
        //Raise Viperslides and score sample in highest bucket
        //Lower Viperslides
        //spline to sample
        MecanumDrive Drive = new MecanumDrive(hardwareMap,StartPose);
        Action action1 = Drive.actionBuilder(StartPose)
                .setTangent(0)
                .splineToConstantHeading(new Vector2d(36,54),0)
                .build();
        Action action2 = Drive.actionBuilder(new Pose2d(36,54,0))
                .setTangent(0)
                .splineToConstantHeading(new Vector2d(54,-36),0)
                .build();


        Actions.runBlocking(action1);
        Actions.runBlocking(action2);

        //pick up sample
        //transfer sample to bucket
        //spline back to bucket
        //repeat
    }
    @Override
    //public void Red;
    //@Override
    public void runOpMode() throws InterruptedException {
        RedAuto();

    }
}


    }

//commit 12
