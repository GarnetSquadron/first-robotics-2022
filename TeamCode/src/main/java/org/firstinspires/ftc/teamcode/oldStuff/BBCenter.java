package org.firstinspires.ftc.teamcode.oldStuff;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;


@Autonomous(name = "BBCenter")
public class BBCenter extends LinearOpMode {

    private VoidsAndThings voidsAndThings;

    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera

    // TFOD_MODEL_ASSET points to a model file stored in the project Asset location,
    // this is only used for Android Studio when using models in Assets.
    private static final String TFOD_MODEL_ASSET = "ShinyBlueBox.tflite";
    private static final String[] LABELS = {
            "BlueCube",
    };
    /**
     * The variable to store our instance of the TensorFlow Object Detection processor.
     */
    private TfodProcessor tfod;
    /**
     * The variable to store our instance of the vision portal.
     */
    private VisionPortal visionPortal;




    //We are defining all motors here, as to manually control each motor rather than use terry hardware.

    private DcMotor lf;
    private DcMotor rf;
    private DcMotor lb;
    private DcMotor rb;
    private DcMotor arm;
    private DcMotor telearm;
    private Servo claw;
    private Servo funnel;
    private CRServo funnelWheel;
    private DistanceSensor sensor;

    //ColorSensor Fsensor;

    //ColorSensor Bsensor;
    //private DcMotor arm;
    //private Servo claw;
    //ColorSensor colorSensor;
    //Wheel encoders
    //This is the gobilda encoder value that is used
    final double wheelUnitTicks = 537.7;

    //this is the gear ratio (this is to make it looks consistent with the arm encoders)
    final double wheelGearRatios = 1;

    //multiplies wheelUnitTicks by wheelGearRatios
    final double wheelRotation = (wheelUnitTicks * wheelGearRatios);

    //This is the circumference value for thegobilda wheels we use in inches
    final double wheelCircumference = 12.5663706144;

    //this is the wheelUnitTicks divided by wheelCircumference to get the amount of ticks to move one inch, this
    //makes it so we can just call on this value instead of pasting the number each time in code, as well as
    //the value can be multiplied by the amount of inches desired to make it more simple.
    final double wheelOneInch = (wheelRotation / wheelCircumference);

    private double MaxClawPos = 0.3;
    private double MinClawPos = 0.45;
    private double MaxArmPos = 509;
    private double MinArmPos = 0;
    private double minRed=90;//under 90
    private double minBlue=50;

//    private boolean GetColorBRed(){
//
//        return minRed<Bsensor.red();
//    }
//    private boolean GetColorBBlue(){
//
//        return minBlue<Bsensor.blue();//under
//    }
public void arm(double power,double height) {//forward(1);forward(-1)
    //resetEncoders();   <--we dont want this
    arm.setTargetPosition((int) (height));
//--------------------------------------------------------------------------------------------------
    arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//--------------------------------------------------------------------------------------------------
    arm.setPower(power);

    //--------------------------------Telemetry, gives data about position and makes sure it doesnt stop immediately.----------------------
    while (arm.isBusy()) {
        telemetry.addData("arm position", arm.getCurrentPosition());
        telemetry.addData("arm tg position", arm.getTargetPosition());
        telemetry.update();
    }
    //-------------------------End While--------------------------------------------------------
    arm.setPower(0); //stopping all motors
}
public void armDown(){
    sleep(200);
    arm(0.15, MaxArmPos);
//    while(arm.isBusy()) {
//        telemetry.addData("arm position", arm.getCurrentPosition());
//        telemetry.addData("arm tg position", arm.getTargetPosition());
//        telemetry.addData("arm power", arm.getPower());
//        telemetry.update();
//
//    }
    sleep(200);
    arm.setPower(0);
    sleep(200);

}
    public void armUp(){
        arm(-0.35, MinArmPos);
//        while(arm.isBusy()){
//            telemetry.addData("arm position",arm.getCurrentPosition());
//            telemetry.update();
//
//        }
        arm.setPower(0);
    }
    public void ClawOpenWIDE(){
        claw.setPosition(0);
    }
    public void ClawOpen(){
        claw.setPosition(0.45);
    }
    public void ClawClose(){
        claw.setPosition(0.9);
    }
    public void FunnelOpen(){
        funnelWheel.setPower(1);
        sleep(1600);
        funnelWheel.setPower(0);
    //funnel.setPosition(0);
    }
    public void runWithoutEncoders() {
        lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void move(double direction, double power) {
        lf.setPower(Math.sin(direction - Math.PI / 4) * power);
        rf.setPower(Math.sin(direction + Math.PI / 4) * power);
        lb.setPower(Math.sin(direction - Math.PI / 4) * power);
        rb.setPower(Math.sin(direction + Math.PI / 4) * power);
    }

    public void tele(double power,double height) {//forward(1);forward(-1)
        //resetEncoders();   <--we dont want this
        telearm.setTargetPosition((int) (height));
//--------------------------------------------------------------------------------------------------
        telearm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//--------------------------------------------------------------------------------------------------
        telearm.setPower(power);
        //--------------------------------Telemetry, gives data about position and makes sure it doesnt stop immediately.----------------------
        while (telearm.isBusy()) {
            telemetry.addData("lf encoder: ",arm.getCurrentPosition());
            telemetry.addData("power: ",arm.getPower());
            telemetry.update();
        }
        //-------------------------End While--------------------------------------------------------
        //stopping all motors
        telearm.setPower(0);
    }
//hello



    public void forward(double power) {//forward(1);forward(-1);
        lf.setPower(power);
        rf.setPower(power);
        lb.setPower(power);
        rb.setPower(power);

    }


    public void resetEncoders() {
        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void runToPosition()  {
        lf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    //----------------------------Forward starts----------------------------------------------------
    public void forward(double power,double distance) {//forward(1);forward(-1)
        resetEncoders();
        lf.setTargetPosition((int) (distance*wheelOneInch));
        rf.setTargetPosition((int) (distance*wheelOneInch));
        lb.setTargetPosition((int) (distance*wheelOneInch));
        rb.setTargetPosition((int) (distance*wheelOneInch));
//--------------------------------------------------------------------------------------------------
        runToPosition();
//--------------------------------------------------------------------------------------------------
        lf.setPower(power);
        rf.setPower(power);
        lb.setPower(power);
        rb.setPower(power);

        //--------------------------------Telematry, gives data about position----------------------
        while (lf.isBusy() && lb.isBusy() && rf.isBusy() && rb.isBusy()) {
            telemetry.addData("rb encoder: ",rb.getCurrentPosition());
            telemetry.addData("power: ",rb.getPower());
            telemetry.addData("lb encoder: ",lb.getCurrentPosition());
            telemetry.addData("power: ",lb.getPower());
            telemetry.addData("rf encoder: ",rf.getCurrentPosition());
            telemetry.addData("power: ",rf.getPower());
            telemetry.addData("lf encoder: ",lf.getCurrentPosition());
            telemetry.addData("power: ",lf.getPower());
            telemetry.addData("distance: ",sensor.getDistance(DistanceUnit.INCH));
//            telemetry.addData("Bsensor red: ",Bsensor.red());
//            telemetry.addData("Bsensor blue: ",Bsensor.blue());
            telemetry.update();
            telemetry.update();
        }
        //-------------------------End While--------------------------------------------------------
        stop(); //stopping all motors
    }
    public void forwardDistance(double power,double distance) {//forward(1);forward(-1)
        resetEncoders();
        lf.setTargetPosition((int) (distance*wheelOneInch));
        rf.setTargetPosition((int) (distance*wheelOneInch));
        lb.setTargetPosition((int) (distance*wheelOneInch));
        rb.setTargetPosition((int) (distance*wheelOneInch));
//--------------------------------------------------------------------------------------------------
        runToPosition();
//--------------------------------------------------------------------------------------------------
        lf.setPower(power);
        rf.setPower(power);
        lb.setPower(power);
        rb.setPower(power);

        //--------------------------------Telematry, gives data about position----------------------
        while (lf.isBusy() && lb.isBusy() && rf.isBusy() && rb.isBusy() && sensor.getDistance(DistanceUnit.INCH)> 4) {
            telemetry.addData("rb encoder: ",rb.getCurrentPosition());
            telemetry.addData("power: ",rb.getPower());
            telemetry.addData("lb encoder: ",lb.getCurrentPosition());
            telemetry.addData("power: ",lb.getPower());
            telemetry.addData("rf encoder: ",rf.getCurrentPosition());
            telemetry.addData("power: ",rf.getPower());
            telemetry.addData("lf encoder: ",lf.getCurrentPosition());
            telemetry.addData("power: ",lf.getPower());
            telemetry.addData("distance: ",sensor.getDistance(DistanceUnit.INCH));
//            telemetry.addData("Bsensor red: ",Bsensor.red());
//            telemetry.addData("Bsensor blue: ",Bsensor.blue());
            telemetry.update();
            telemetry.update();
        }
        //-------------------------End While--------------------------------------------------------
        stop(); //stopping all motors
    }

    public void turn(double power){
        lf.setPower(-power);
        rf.setPower(power);
        lb.setPower(-power);
        rb.setPower(power);
    }
    IMU imu;
    public void turn(double power, double degrees){
        runWithoutEncoders();
        //YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        //double initdirection = orientation.getYaw(AngleUnit.DEGREES);
        imu.resetYaw();
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        while (Math.abs (orientation.getYaw(AngleUnit.DEGREES)) <degrees) {


            turn(power);
            telemetry.addData("Yaw (Z)", "%.2f Deg. (Heading)", Math.abs(orientation.getYaw(AngleUnit.DEGREES)));
//            telemetry.addData("Pitch (X)", "%.2f Deg.", orientation.getPitch(AngleUnit.DEGREES));
//            telemetry.addData("Roll (Y)", "%.2f Deg.\n", orientation.getRoll(AngleUnit.DEGREES));
            telemetry.update();
            orientation = imu.getRobotYawPitchRollAngles();
        }
//        while (Math.abs (orientation.getYaw(AngleUnit.DEGREES)) >degrees) {
//
//
//            turn(-power/4);
//            telemetry.addData("Yaw (Z)", "%.2f Deg. (Heading)", Math.abs(orientation.getYaw(AngleUnit.DEGREES)));
////            telemetry.addData("Pitch (X)", "%.2f Deg.", orientation.getPitch(AngleUnit.DEGREES));
////            telemetry.addData("Roll (Y)", "%.2f Deg.\n", orientation.getRoll(AngleUnit.DEGREES));
//            telemetry.update();
//            orientation = imu.getRobotYawPitchRollAngles();
//        }
        turn(0);
    }
    //----------------------------------End of forward--------------------------------------------------
    //sRight = straife right
    //right = ++ left = --
//----------------------------sRight starts---------------------------------------------------------
    public void sRight(double power,double distance) {
        resetEncoders();
        lf.setTargetPosition((int) (-distance*wheelOneInch));
        rf.setTargetPosition((int) (distance*wheelOneInch));
        lb.setTargetPosition((int) (distance*wheelOneInch));
        rb.setTargetPosition((int) (-distance*wheelOneInch));
//--------------------------------------------------------------------------------------------------
        runToPosition();
//--------------------------------------------------------------------------------------------------
        lf.setPower(-power);
        rf.setPower(power);
        lb.setPower(power);
        rb.setPower(-power);

        //--------------------------------Telematry, gives data about position--------------------------
        while (lf.isBusy() && lb.isBusy() && rf.isBusy() && rb.isBusy()) {
            telemetry.addData("rb encoder: ",rb.getCurrentPosition());
            telemetry.addData("power: ",rb.getPower());
            telemetry.addData("lb encoder: ",lb.getCurrentPosition());
            telemetry.addData("power: ",lb.getPower());
            telemetry.addData("rf encoder: ",rf.getCurrentPosition());
            telemetry.addData("power: ",rf.getPower());
            telemetry.addData("lf encoder: ",lf.getCurrentPosition());
            telemetry.addData("power: ",lf.getPower());
//            telemetry.addData("Bsensor red: ",Bsensor.red());
//            telemetry.addData("Bsensor blue: ",Bsensor.blue());
            telemetry.update();

            telemetry.update();
        }
        //-------------------------End While------------------------------------------------------------
        stop(); //stopping all motors
    }
    //----------------------------------End of sRight----------------------------------------------

    public void Stop() {
        lf.setPower(0);
        rf.setPower(0);
        lb.setPower(0);
        rb.setPower(0);

    }

    public void striaferight(double power) {
        lf.setPower(power);
        rf.setPower(-power);
        lb.setPower(-power);
        rb.setPower(power);

    }

    public void striafeleft(double power) {
        lf.setPower(-power);
        rf.setPower(power);
        lb.setPower(power);
        rb.setPower(-power);

    }
//    public int SpikeCheck() {
//        int max1= 10;
//        int max2= 20;
//        int spike;
//        int i=0;
//        forward(0.25,1);
//        left(0.25,-2);
//        while(sensor.getDistance(DistanceUnit.INCH)>=30) {
//            right(0.25, 2);
//            i+=2;
//        }
//        if (i<max1)
//            spike=1;
//        else if (i<max2)
//            spike=2;
//        else
//            spike=3;
//        left(0.25, 2*i);
//
//        return spike;
//
//    }

    //This is a new auto with the color sensor and straife

//    public void autoScrimmageBF() {
//        claw.setPosition(MinClawPos);
//        sRight(.25, 44.5);
//        sleep(200);
//        forward(.25,4);
//        //boolean spike=GetColorB();
//        if(GetColorBRed()){ //Put yes statement here for detection of spike marker
//            forward(.25, 2);
//            forward(-.25,-7);
//            turn(-0.25,179);
//            sRight(.25,7);
//            forward(-.25,-2);
//            forward(.25, 52);
//            claw.setPosition(MaxClawPos);
//        } //end of yes statement
//        else{ //Put no statement here for detection of spike marker
//            forward(-.25, -4);
//            sRight(.25,6);;
//            turn(-0.25, 176);
//            sRight(.25,17);
//            forward(0.25,4);
//            sleep(200);
//            if(GetColorBRed()){ //Put yes statement here for detection of spike marker
//                forward(.25, 2);
//                //drop piece here
//                forward(.25,14);
//                claw.setPosition(MaxClawPos);
//
//            } //end of yes statement
//            else { //Put no statement here for detection of spike marker
//                forward(.25,2);
//                sRight(.25,6);
//                //drop thing
//                forward(.25,38);
//                claw.setPosition(MaxClawPos);
//            }
//
//        }
//
//
//    }
    //--------------------------------------------------------------------
    public void autoScrimmage2() {
        forward(-.25, -48);
        sleep(200);
        forward(-.25, -5);
        forward(.25, 5);
    }

    //-------------------------------------------------------------------

    public void autoScrimmage3() {
        forward(-.25, -38);
        forward(.25, 26);
        lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //right(.25);
        sleep(1600);
        forward(0);
        forward(.25,5);
        forward(-.25,-36);
    }

    //-----------------------------------------------------

    public void autoScrimmage5() {
        striaferight(-.25);
        sleep(1000);
        forward(0);
    }

    public void quarterTurn() {
        //right(1);
        sleep(200);
        forward(0);
    }

    public void autoScrimmagered() {
        lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        striaferight(.25);
        sleep(3400);
        forward( .25,-40);
        forward( .25,5);

    }

    public void autoScrimmageblue() {
        lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        striaferight(-.25);
        sleep(3400);
        forward( .25,-40);
        forward( .25,5);

    }




    private void initTfod() {

        // Create the TensorFlow processor by using a builder.
        tfod = new TfodProcessor.Builder()


                // With the following lines commented out, the default TfodProcessor Builder
                // will load the default model for the season. To define a custom model to load,
                // choose one of the following:
                //   Use setModelAssetName() if the custom TF Model is built in as an asset (AS only).
                //   Use setModelFileName() if you have downloaded a custom team model to the Robot Controller.
                .setModelAssetName(TFOD_MODEL_ASSET)
                //.setModelFileName(TFOD_MODEL_FILE)

                // The following default settings are available to un-comment and edit as needed to
                // set parameters for custom models.
                .setModelLabels(LABELS)
                //.setIsModelTensorFlow2(true)
                //.setIsModelQuantized(true)
                //.setModelInputSize(300)
                //.setModelAspectRatio(16.0 / 9.0)

                .build();
        //time for tfod 2!
        //tfod2 = new TfodProcessor.Builder().setModelAssetName(TFOD_MODEL_ASSET2).setModelLabels(LABELS2).build();

        // Create the vision portal by using a builder.
        VisionPortal.Builder builder = new VisionPortal.Builder();

        // Set the camera (webcam vs. built-in RC phone camera).
        if (USE_WEBCAM) {
            builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
        } else {
            builder.setCamera(BuiltinCameraDirection.BACK);
        }

        // Choose a camera resolution. Not all cameras support all resolutions.
        //builder.setCameraResolution(new Size(640, 480));

        // Enable the RC preview (LiveView).  Set "false" to omit camera monitoring.
        //builder.enableLiveView(true);

        // Set the stream format; MJPEG uses less bandwidth than default YUY2.
        //builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);

        // Choose whether or not LiveView stops if no processors are enabled.
        // If set "true", monitor shows solid orange screen if no processors enabled.
        // If set "false", monitor shows camera view without annotations.
        //builder.setAutoStopLiveView(false);

        // Set and enable the processor.
        builder.addProcessor(tfod);
        //builder.addProcessor(tfod2);

        // Build the Vision Portal, using the above settings.
        visionPortal = builder.build();

        // Set confidence threshold for TFOD recognitions, at any time.
        //tfod.setMinResultConfidence(0.75f);

        // Disable or re-enable the TFOD processor at any time.
        //visionPortal.setProcessorEnabled(tfod, true);

    }

    private int getSpikeMarkVision() {

        List<Recognition> currentRecognitions = tfod.getRecognitions();
        //telemetry.addData("# Objects Detected", currentRecognitions.size());

        double confidence=0;
        double x=100;
        double counter=0;
        while ((currentRecognitions.size()==0) && opModeIsActive() ) {
            currentRecognitions = tfod.getRecognitions();
            counter++;
            telemetry.addData("counter", counter);
            telemetry.update();
            if (counter>40){
                x=0;//to get spikemark 1
                break;
            }
            sleep(200);


        }
        // Step through the list of recognitions and display info for each one.
        for (Recognition recognition : currentRecognitions) {
            if (confidence < recognition.getConfidence()) {
                x = (recognition.getLeft() + recognition.getRight()) / 2;
                confidence = recognition.getConfidence();
            }

            //            telemetry.addData(""," ");
            //            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
            //            telemetry.addData("- Position", "%.0f / %.0f", x, y);
            //            telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());
            //telemetry.addData("- spikemark?","%.0f x %.0f", Math.round(x/200));
        }   // end for() loop
        telemetry.addData("position",x);
        telemetry.addData("spikemark",Math.round(x/200+1));
        telemetry.update();
        sleep(500);
        return (int) Math.round(x/200+1);
    }







    //This is a new auto with the color sensor and straife

    //    public void autoScrimmageRF() {
//        //claw.setPosition(MinClawPos);
//        sRight(-.25, -40);
//        sleep(200);
//        forward(.25,4);
//        sleep(200);
//        //boolean spike=GetColorB();
//        if(GetColorBRed()){ //Put yes statement here for detection of spike marker
//            forward(.25, 2);
//            forward(-.25,-7);
//            turn(0.25,179);
//            sRight(-.25,-7);
//            forward(-.25,-2);
//            forward(.25, 45);
//            sleep(1000);
//            //claw.setPosition(MaxClawPos);
//        } //end of yes statement
//        else{ //Put no statement here for detection of spike marker
//                forward(-.25, -4);
//                sRight(-.25,-6);;
//                turn(0.25, 176);
//                sRight(-.25,-17);
//                forward(0.25,4);
//                sleep(200);
//            if(GetColorBRed()){ //Put yes statement here for detection of spike marker
//                forward(.25, 2);
//                //drop piece here
//
//
//
//
//
//                forward(.25,14);
//                sleep(1000);
//                //claw.setPosition(MaxClawPos);
//
//           } //end of yes statement
//            else { //Put no statement here for detection of spike marker
//                forward(.25,2);
//                sRight(-.25,-6);
//                //drop thing
//                forward(.25,35);
//                sleep(1000);
//                //claw.setPosition(MaxClawPos);
//            }
//
//        }
//
//
//    }

    public void CameraAutoScrimmageRF() {
        //claw.setPosition(MinClawPos);
        int spikemark = getSpikeMarkVision();
        visionPortal.close();
        sleep(1000);
        ClawClose();
//        telearm.setPower(1);
//        sleep(2000);
//        telearm.setPower(0);

        telearm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        tele(1,1000);
        //boolean spike=GetColorB();
        if(spikemark==1){ //Put yes statement here for detection of spike marker

            forward(-0.4, -33);
            sleep(200);
            turn(-0.25,90);
            forward(-0.4,-25);

            armDown();
            sleep(1000);
            ClawOpen();//claw open
            sleep(100);
            armUp();
            sleep(1200);
            forward(-0.4,-5);
            forward(0.4,5);

            sRight(-0.25,-12);
            forward(-0.4,-18);
            ClawOpen();//so that it is not WIDE anymore

            sleep(500);
            //sRight(0.5,15);















//            sRight(.25,4);
//            sleep(200);
//            sRight(.25, 2);
//            sRight(-.25,-7);
//            turn(0.25,90);
//            forward(-.25,-7);
//            sRight(-.25,-2);
//            sRight(.25, 45);
//            sleep(1000);
            //claw.setPosition(MaxClawPos);
        } //end of yes statement
        else{ //Put no statement here for detection of spike marker



//            sRight(.25,4);
//            sleep(200);
//            //sRight(-.25, -4);
//            forward(-.25,-9);
//            turn(0.25, 90);
//            forward(-.25,-17);
//            sRight(0.25,4);
//            sleep(200);
            if(spikemark==2){ //Put yes statement here for detection of spike marker
                forward(-0.4, -53);

                sleep(200);

                armDown();
                sleep(1000);
                ClawOpen();//claw open
                sleep(100);
                armUp();
                sRight(-.25, -40);
                forward(0.4, 20);
                turn(-0.25,90);
                sRight(-0.25, -4);
                forward(-0.4, -11);
                ClawOpen();//so that it is not WIDE anymore

                sleep(100);


                //turn(0.25,178);
//                sRight(.25, 2);
//
//                sRight(.25,14);
//                sleep(1000);
//                //claw.setPosition(MaxClawPos);

            } //end of yes statement
            else { //Put no statement here for detection of spike marker
                //sRight(.25,2);

                forward(-0.4,-30);
                sRight(0.25,7);
                sRight(-0.25,-7);
                turn(-0.25,90);
                forward(-0.4, -5);

                armDown();
                sleep(1000);
                forward(0.25, 5);
                ClawOpen();//drop thing
                sleep(100);
                armUp();
                forward(-0.25,-45);
                sRight(0.25,10);







//                sRight(.25,35);
//                sleep(1000);
//                //claw.setPosition(MaxClawPos);
            }

        }

        ClawOpen();
        FunnelOpen();
        //sleep(700);
        tele(-1,0);
        armUp();//VERY IMPORTANT: line keeps the teleop from losing control of arm/stalling arm
        sRight(0.25,28);



    }
    public void TestingCameraAutoScrimmageRFWithOOP() {

        //claw.setPosition(MinClawPos);
        int spikemark = voidsAndThings.getSpikeMarkVision();
        visionPortal.close();
        sleep(1000);
        ClawClose();
//        telearm.setPower(1);
//        sleep(2000);
//        telearm.setPower(0);

        telearm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        tele(1,1000);
        //boolean spike=GetColorB();
        if(spikemark==1){ //Put yes statement here for detection of spike marker

            forward(-0.4, -33);
            sleep(200);
            turn(-0.25,90);
            forward(-0.4,-25);

            armDown();
            sleep(1000);
            ClawOpen();//claw open
            sleep(100);
            armUp();
            sleep(1200);
            forward(-0.4,-5);
            forward(0.4,5);

            sRight(-0.25,-12);
            forward(-0.4,-18);
            ClawOpen();//so that it is not WIDE anymore

            sleep(500);
            //sRight(0.5,15);















//            sRight(.25,4);
//            sleep(200);
//            sRight(.25, 2);
//            sRight(-.25,-7);
//            turn(0.25,90);
//            forward(-.25,-7);
//            sRight(-.25,-2);
//            sRight(.25, 45);
//            sleep(1000);
            //claw.setPosition(MaxClawPos);
        } //end of yes statement
        else{ //Put no statement here for detection of spike marker



//            sRight(.25,4);
//            sleep(200);
//            //sRight(-.25, -4);
//            forward(-.25,-9);
//            turn(0.25, 90);
//            forward(-.25,-17);
//            sRight(0.25,4);
//            sleep(200);
            if(spikemark==2){ //Put yes statement here for detection of spike marker
                forward(-0.4, -53);

                sleep(200);

                armDown();
                sleep(1000);
                ClawOpen();//claw open
                sleep(100);
                armUp();
                sRight(-.25, -40);
                forward(0.4, 20);
                turn(-0.25,90);
                sRight(-0.25, -4);
                forward(-0.4, -11);
                ClawOpen();//so that it is not WIDE anymore

                sleep(100);


                //turn(0.25,178);
//                sRight(.25, 2);
//
//                sRight(.25,14);
//                sleep(1000);
//                //claw.setPosition(MaxClawPos);

            } //end of yes statement
            else { //Put no statement here for detection of spike marker
                //sRight(.25,2);

                forward(-0.4,-30);
                sRight(0.25,7);
                sRight(-0.25,-7);
                turn(-0.25,90);
                forward(-0.4, -5);

                armDown();
                sleep(1000);
                forward(0.25, 5);
                ClawOpen();//drop thing
                sleep(100);
                armUp();
                forward(-0.25,-45);
                sRight(0.25,10);







//                sRight(.25,35);
//                sleep(1000);
//                //claw.setPosition(MaxClawPos);
            }

        }

        ClawOpen();
        FunnelOpen();
        //sleep(700);
        tele(-1,0);
        armUp();//VERY IMPORTANT: line keeps the teleop from losing control of arm/stalling arm
        sRight(0.25,28);



    }
    @Override
    public void runOpMode() throws InterruptedException {
        voidsAndThings = new VoidsAndThings(hardwareMap);
        voidsAndThings.initHardware();
        lf = hardwareMap.get(DcMotor.class, "lf");
        rf = hardwareMap.get(DcMotor.class, "rf");
        lb = hardwareMap.get(DcMotor.class, "lb");
        rb = hardwareMap.get(DcMotor.class, "rb");
        rf.setDirection(DcMotorSimple.Direction.REVERSE);
        rb.setDirection(DcMotorSimple.Direction.REVERSE);
        telearm = hardwareMap.get(DcMotor.class, "teleArm");
        sensor=hardwareMap.get(DistanceSensor.class, "distance");
        //Fsensor=hardwareMap.get(ColorSensor.class, "Fsensor");
     //   Bsensor=hardwareMap.get(ColorSensor.class, "Bsensor");
        imu = hardwareMap.get(IMU.class, "imu");
        claw = hardwareMap.get(Servo.class, "claw");
        arm = hardwareMap.get(DcMotor.class, "arm");
        //funnel = hardwareMap.get(Servo.class, "funnel");
        funnelWheel = hardwareMap.get(CRServo.class, "funnel");
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.UP;
        RevHubOrientationOnRobot.UsbFacingDirection  usbDirection  = RevHubOrientationOnRobot.UsbFacingDirection.FORWARD;

        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);
        imu.initialize(new IMU.Parameters(orientationOnRobot));

        telearm.setDirection(DcMotorSimple.Direction.REVERSE);

        //arm = hardwareMap.get(DcMotor.class, "arm");
        //claw = hardwareMap.get(Servo.class, "claw");
        //colorSensor = hardwareMap.colorSensor.get("color");
        //List<Recognition> currentRecognitions = tfod.getRecognitions();
        //telemetry.addData("# Objects Detected", currentRecognitions.size());

//        double confidence=0;
//        double x=100;
        initTfod();
//        int counter=0;
//        double x=-100;
//        double confidence=0;
//        int SpikeMark=0;
//        while ((currentRecognitions.size()==0) && !(isStarted()) ) {
//            currentRecognitions = tfod.getRecognitions();
//            counter++;
//            telemetry.addData("counter", counter);
//            telemetry.update();
//            //sleep(200);
//            if(currentRecognitions.size()>0){
//                for (Recognition recognition : currentRecognitions) {
//                    if (confidence < recognition.getConfidence()) {
//                        x = (recognition.getLeft() + recognition.getRight()) / 2;
//                        confidence = recognition.getConfidence();
//                    }
//
//                    //            telemetry.addData(""," ");
//                    //            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
//                    //            telemetry.addData("- Position", "%.0f / %.0f", x, y);
//                    //            telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());
//                    //telemetry.addData("- spikemark?","%.0f x %.0f", Math.round(x/200));
//                }   // end for() loop
//                SpikeMark=(int) Math.round(x/200+1);
//                if (SpikeMark==1){
//                    SpikeMark=2;
//                }
//            }
//        }
//        telemetry.addData("position",x);
//        telemetry.addData("spikemark",SpikeMark);
//        telemetry.update();
//        //sleep(500);
        waitForStart();
        //voidsAndThings.turn(0.25,90);


        //autoScrimmageBF();
        CameraAutoScrimmageRF();

//        turn(0.25,91);
//        forward(0.25,11);




    }


}


