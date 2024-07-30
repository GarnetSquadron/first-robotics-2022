package org.firstinspires.ftc.teamcode.oldStuff;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;


@Autonomous(name = "RAUDBackdrop")
@Disabled
public class AutoAudiRFBackdrop extends LinearOpMode {
    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera

    private static final String TFOD_MODEL_ASSET = "model_20240104_203329.tflite";
    private static final String[] LABELS = {
            "RedCube",
    };
    /**
     * The variable to store our instance of the TensorFlow Object Detection processor.
     */
    private TfodProcessor tfod;
    /**
     * The variable to store our instance of the vision portal.
     */
    private VisionPortal visionPortal;




    private DcMotor lf;
    private DcMotor rf;
    private DcMotor lb;
    private DcMotor rb;
    private DcMotor arm;
    private DcMotor telearm;
    private Servo claw;
    private CRServo funnelWheel;
    ColorSensor Fsensor;

    private VoidsAndThings voidsAndThings;
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

    private double MaxClawPos = 0.65;
    private double MinClawPos = 0.9;
    private double MaxArmPos = 509;
    private double MinArmPos = 0;
    private double minRed=90;//under 90
    private double minBlue=50;

    public void runWithoutEncoders() {
        lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void arm(double power,double height) {//forward(1);forward(-1)
        //resetEncoders();   <--we dont want this
        arm.setTargetPosition((int) (height));
//--------------------------------------------------------------------------------------------------
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//--------------------------------------------------------------------------------------------------
        arm.setPower(power);

        stop(); //stopping all motors
    }
    public void armDown(){
        sleep(200);
        arm(0.15, MaxArmPos);
        while(arm.isBusy()) {
            telemetry.addData("arm position", arm.getCurrentPosition());
            telemetry.update();

        }
        sleep(200);
        arm.setPower(0);
        sleep(200);

    }
    public void armUp(){
        arm(-0.35, MinArmPos);
        while(arm.isBusy()){
            telemetry.addData("arm position",arm.getCurrentPosition());
            telemetry.update();

        }
        arm.setPower(0);
    }
    public void ClawOpenWIDE(){
        claw.setPosition(0);
    }
    public void ClawOpen(){
        claw.setPosition(0.65);
    }
    public void ClawClose(){
        claw.setPosition(0.9);
    }
    public void FunnelOpen(){
        funnelWheel.setPower(1);
        sleep(1600);
        funnelWheel.setPower(0);
    }

    public void move(double direction, double power) {
        lf.setPower(Math.sin(direction - Math.PI / 4) * power);
        rf.setPower(Math.sin(direction + Math.PI / 4) * power);
        lb.setPower(Math.sin(direction - Math.PI / 4) * power);
        rb.setPower(Math.sin(direction + Math.PI / 4) * power);
    }




    public void forward(double power) {//forward(1);forward(-1);
        lf.setPower(power);
        rf.setPower(power);
        lb.setPower(power);
        rb.setPower(power);

    }
    public void tele(double power,double height) {//forward(1);forward(-1)
        telearm.setTargetPosition((int) (height));
//--------------------------------------------------------------------------------------------------
        telearm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//--------------------------------------------------------------------------------------------------
        telearm.setPower(power);
        //--------------------------------Telemetry, gives data about position and makes sure it doesnt stop immediately.----------------------
        while (telearm.isBusy()) {
            telemetry.addData("telearm position", telearm.getCurrentPosition());
            telemetry.update();
        }
        //-------------------------End While--------------------------------------------------------
        //stopping all motors
        telearm.setPower(0);
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
    public void forward(double power,double distance) { //forward(1);forward(-1)
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
        imu.resetYaw();
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        while (Math.abs (orientation.getYaw(AngleUnit.DEGREES)) <=degrees) {


            turn(power);
            telemetry.addData("Yaw (Z)", "%.2f Deg. (Heading)", Math.abs(orientation.getYaw(AngleUnit.DEGREES)));
//          telemetry.addData("Yaw (Z)", "%.2f Deg. (Heading)", Math.abs(orientation.getYaw(AngleUnit.DEGREES)));
            telemetry.addData("Yaw (Z)", "%.2f Deg. (Heading)", Math.abs(orientation.getYaw(AngleUnit.DEGREES)));
            telemetry.addData("Yaw (Z)", "%.2f Deg. (Heading)", Math.abs(orientation.getYaw(AngleUnit.DEGREES)));
            telemetry.update();
            orientation = imu.getRobotYawPitchRollAngles();
        }
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
            telemetry.addData("claw: ",claw.getPosition());
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


    private void initTfod() {

        // Create the TensorFlow processor by using a builder.
        tfod = new TfodProcessor.Builder()



                .setModelAssetName(TFOD_MODEL_ASSET)
                .setModelLabels(LABELS)

                .build();

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

    }   // end method initTfod()
    private int getSpikeMarkVision() {

        List<Recognition> currentRecognitions = tfod.getRecognitions();

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

        }   // end for() loop
        telemetry.addData("position",x);
        telemetry.addData("spikemark",Math.round(x/200+1));
        telemetry.update();
        sleep(500);
        return (int) Math.round(x/200+1);
    }


    public void CameraAutoScrimmageRFBackdrop() {
        //claw.setPosition(MinClawPos);
        int spikemark = getSpikeMarkVision();
        //int spikemark = 1;
        visionPortal.close();
        sleep(1000);
        ClawClose();
        telearm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //boolean spike=GetColorB();
        if(spikemark==1){ //Put yes statement here for detection of spike marker
            forward(-.40,-33);
            turn(-0.25,89);
            forward(-0.40, -25);

            armDown();
            sleep(1000);
            //forward(0.25, 5);
            ClawOpen();//drop thing
            armUp();
            forward(.40,24);
            sRight(-0.40,-36);
            armDown();
            forward(.40,76);
            turn(-0.25,89);
            turn(-0.25,89);
            sRight(-.40,-15);
            forward(-.40,-20);
            tele(.2,1000);
            //.setPower();
            FunnelOpen();
           // forward(.40,6);
            //sRight(-.40,20);
            //sRight(-0.25,-1);



//
        } //end of yes statement
        else{ //Put no statement here for detection of spike marker



//
            if(spikemark==2){ //Put yes statement here for detection of spike marker
                forward(-.50, -53);

                sleep(200);

                armDown();
                sleep(1000);
                ClawOpen();//claw open
                armUp();
                forward(.50,45);
                turn(0.25,89);
                armDown();
                forward(-.60,-80);
                sRight(-.40,-30);
                tele(1,-1500);
                forward(-.3,-12);
                //.setPower();
                FunnelOpen();
                forward(1,7);
                tele(1,0);
                //forward(-.25,-4);
                //turn(0.25,89);
                //forward(-.25, -94);



            } //end of yes statement
            else { //Put no statement here for detection of spike marker
                //sRight(.25,2);
                forward(-.40, -33);
                sleep(200);
                turn(-0.25,91);
                forward(-.25,-4);

                armDown();
                forward(0.40,2);
                sleep(1000);
                ClawOpen();//claw open
                armUp();
                sleep(1200);
                sRight(-.40,-37);
                armDown();
                forward(.40,75);
                turn(-0.25,89);
                turn(-0.25,89);
                sRight(-.40,-24);
                forward(-.40,-15);
                tele(1,1000);
                //.setPower();
                FunnelOpen();

            }

        }
        //FunnelOpen();
        //sleep(700);
        armUp();//VERY IMPORTANT: line keeps the teleop from losing control of arm/stalling arm

    }
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
    @Override
    public void runOpMode() throws InterruptedException {
        lf = hardwareMap.get(DcMotor.class, "lf");
        rf = hardwareMap.get(DcMotor.class, "rf");
        lb = hardwareMap.get(DcMotor.class, "lb");
        rb = hardwareMap.get(DcMotor.class, "rb");
        rf.setDirection(DcMotorSimple.Direction.REVERSE);
        rb.setDirection(DcMotorSimple.Direction.REVERSE);
        Fsensor=hardwareMap.get(ColorSensor.class, "Fsensor");
        //Bsensor=hardwareMap.get(ColorSensor.class, "Bsensor");
        imu = hardwareMap.get(IMU.class, "imu");
        claw = hardwareMap.get(Servo.class, "claw");
        arm = hardwareMap.get(DcMotor.class, "arm");
        //funnel = hardwareMap.get(Servo.class, "funnel");
        funnelWheel = hardwareMap.get(CRServo.class, "funnel");
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        telearm = hardwareMap.get(DcMotor.class, "teleArm");


        voidsAndThings = new VoidsAndThings(hardwareMap);
        voidsAndThings.initHardware();

        RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.UP;
        RevHubOrientationOnRobot.UsbFacingDirection  usbDirection  = RevHubOrientationOnRobot.UsbFacingDirection.FORWARD;



        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);
        imu.initialize(new IMU.Parameters(orientationOnRobot));

        initTfod();
        waitForStart();


        CameraAutoScrimmageRFBackdrop();
    }


}
