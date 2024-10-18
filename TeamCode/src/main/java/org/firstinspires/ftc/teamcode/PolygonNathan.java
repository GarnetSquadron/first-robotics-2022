package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ftc.Actions;// <--NOTE: FOR SOME REASON ACTIONS WONT IMPORT IF YOU DONT PUT .ftc BEFORE .Actions IDK WHY

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Config
@Autonomous(name="PolygonNathan", group = "RoadRunnerStuff")
public class PolygonNathan extends LinearOpMode {
    Pose2d beginPose = new Pose2d(30, 20, 0);
    NonDriveHardware.Arm arm = new NonDriveHardware.Arm(hardwareMap, 630);
    MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
    public static int pos = 300;
    Action path(double angle, double length){
        double x = drive.pose.position.x;
        double y = drive.pose.position.y;
        double theta = drive.pose.heading.toDouble();
//        return drive.actionBuilder(beginPose).splineToSplineHeading(new Pose2d(x+length*Math.cos(angle),y+length*Math.sin(angle),theta+angle),angle).build();
        return drive.actionBuilder(beginPose).splineToSplineHeading(new Pose2d(x,y+Math.tan(theta)*5, angle),1).build();
    }
//    void RightTriangle(double theta){
//                .splineToSplineHeading(new x+5,y, theta)
//                .splineToSplineHeading(new Pose2d()
//    }

    void Polygon(double n,double sideLength){
        for(int i=0; i<n; i++) {
            Actions.runBlocking(path((n - 2) * Math.PI / n, sideLength));
        }
    }
    @Override
    public void runOpMode() throws InterruptedException {

//       Action path1 = drive.actionBuilder(beginPose)
//               .splineToSplineHeading(new Pose2d())
//               .build();

        waitForStart();
        Polygon(5,3);
    }
}