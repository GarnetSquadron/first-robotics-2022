package org.firstinspires.ftc.teamcode.oldStuff;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "\uD83D\uDE08 \uD83D\uDE08 JAMANUEL PLZ\uD83D\uDE08 \uD83D\uDE08")
@Disabled
public class VoidsAndThingsTest extends LinearOpMode {
    VoidsAndThings voidsAndThings;

    @Override
    public void runOpMode() throws InterruptedException {
        voidsAndThings = new VoidsAndThings(hardwareMap);
        voidsAndThings.initHardware();
        double x=0;
        double y=10;
//        Thread Telem = new Thread(()->{
//            waitForStart();
//            while(opModeIsActive()) {
//                telemetry.addData("X", voidsAndThings.OPH.getArcsX());
//                telemetry.addData("Y", voidsAndThings.OPH.getArcsY());
//                telemetry.addData("DeltaX", voidsAndThings.OPH.DeltaRealX);
//                telemetry.addData("DeltaY", voidsAndThings.OPH.DeltaRealY);
//                telemetry.addData("DeadWheelX", voidsAndThings.OPH.getDeadWheelX());
//                telemetry.addData("DeadwheelY", voidsAndThings.OPH.getDeadWheelY());
//                telemetry.addData("\u03B8", voidsAndThings.OPH.getTheta());
//                telemetry.addData("\u03C9", voidsAndThings.OPH.getAngularVel());
//                telemetry.addData("x left to move, assuming arcs",x-voidsAndThings.OPH.getArcsX());
//                telemetry.addData("y left to move, assuming arcs",y-voidsAndThings.OPH.getArcsY());
//                telemetry.addData("x left to move, assuming lines",x-voidsAndThings.OPH.getLinesX());
//                telemetry.addData("y left to move, assuming lines",y-voidsAndThings.OPH.getLinesY());
//                telemetry.addData("lPower", voidsAndThings.lPower);
//                telemetry.addData("rPower", voidsAndThings.rPower);
//                telemetry.addData("disx", voidsAndThings.disx);
//                telemetry.addData("disy", voidsAndThings.disy);
//                telemetry.addData("Power", voidsAndThings.Power);
//                telemetry.addData("Moving", voidsAndThings.moving);
//
//                telemetry.update();
//            }
//        });
////        Thread moving = new Thread(()->{
////            waitForStart();
////            voidsAndThings.moveSquareToWithOdometry(0.2,0,30);
////        });
//        Thread updateLinesPos = new Thread(()->{
//            waitForStart();
//            while (opModeIsActive()){
//                voidsAndThings.OPH.updatePosAssumeLines();
//                //sleep(2);
//            }
//        });
//        Thread updateArcsPos = new Thread(()->{
//            waitForStart();
//            while (opModeIsActive()){
//                voidsAndThings.OPH.updatePosAssumeArcs();
//                //sleep(2);
//            }
//        });
        waitForStart();

//        Telem.start();
//        updateArcsPos.start();
//        updateLinesPos.start();
//        //voidsAndThings.moveSquareToWithOdometry(0.25,x,y);
//
//        Telem.join();
//        updateArcsPos.join();
        while(opModeIsActive()) {
            telemetry.addData("X", voidsAndThings.OPH.getArcsX());
            telemetry.addData("Y", voidsAndThings.OPH.getArcsY());
            telemetry.addData("DeltaX", voidsAndThings.OPH.DeltaRealX);
            telemetry.addData("DeltaY", voidsAndThings.OPH.DeltaRealY);
            telemetry.addData("DeadWheelX", voidsAndThings.OPH.getDeadWheelX());
            telemetry.addData("DeadwheelY", voidsAndThings.OPH.getDeadWheelY());
            telemetry.addData("\u03B8", voidsAndThings.OPH.getTheta());
            telemetry.addData("\u03C9", voidsAndThings.OPH.getAngularVel());
            telemetry.addData("x left to move, assuming arcs",x-voidsAndThings.OPH.getArcsX());
            telemetry.addData("y left to move, assuming arcs",y-voidsAndThings.OPH.getArcsY());
            telemetry.addData("x left to move, assuming lines",x-voidsAndThings.OPH.getLinesX());
            telemetry.addData("y left to move, assuming lines",y-voidsAndThings.OPH.getLinesY());
            telemetry.addData("lPower", voidsAndThings.lPower);
            telemetry.addData("rPower", voidsAndThings.rPower);
            telemetry.addData("disx", voidsAndThings.disx);
            telemetry.addData("disy", voidsAndThings.disy);
            telemetry.addData("Power", voidsAndThings.Power);
            telemetry.addData("Moving", voidsAndThings.moving);

            telemetry.update();
        }




    }
}
