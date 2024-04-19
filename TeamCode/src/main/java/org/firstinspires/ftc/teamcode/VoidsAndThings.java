package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcontroller.external.samples.RobotHardware;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;
//import com.google.blocks.ftcrobotcontroller.runtime.ColorRangeSensorAccess;

/**
* hi
 * this is cool
 * so um yeah use this thing for stuff.
*
*
*
*
*
* */
public class VoidsAndThings {
    private HardwareMap hardwareMap;

    // Define a constructor that allows the OpMode to pass a reference to itself.
//    public VoidsAndThings(HardwareMap voidsAndThings) {
//        hardwareMap = voidsAndThings;
//    }
    /* Declare OpMode members. */


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
    private DcMotor telearm;
    private DistanceSensor sensor;
    private DcMotor lift;
    private Servo claw;
    private Servo Funnel;
    //ColorSensor Fsensor;
    //ColorSensor Bsensor;


    private DcMotor arm;

    //ColorSensor colorSensor;
    IMU imu;
    //Wheel encoders
    //This is the gobilda encoder value that is used
    final double wheelUnitTicks = 537.7;

    //this is the gear ratio (this is to make it looks consistent with the arm encoders)
    final double wheelGearRatios = 1;

    //multiplies wheelUnitTicks by wheelGearRatios
    final double wheelRotation = (wheelUnitTicks * wheelGearRatios);

    //This is the circumference value for the gobilda wheels we use in inches
    final double wheelCircumference = 12.5663706144;

    //this is the wheelUnitTicks divided by wheelCircumference to get the amount of ticks to move one inch, this
    //makes it so we can just call on this value instead of pasting the number each time in code, as well as
    //the value can be multiplied by the amount of inches desired to make it more simple.
    final double wheelOneInch = (wheelRotation / wheelCircumference);
    private HardwareMap robotHardware; // gain access to methods in the calling OpMode.

    // Define a constructor that allows the OpMode to pass a reference to itself.
    public VoidsAndThings(HardwareMap voidsAndThings) {
        robotHardware = voidsAndThings;
    }

    public void move(double direction, double power) {
        lf.setPower(Math.sin(direction + Math.PI / 4) * power);
        rf.setPower(Math.cos(direction - Math.PI / 4) * power);
        lb.setPower(Math.sin(direction + Math.PI / 4) * power);
        rb.setPower(Math.cos(direction - Math.PI / 4) * power);
    }
//hello






    public void resetEncoders() {
        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
//    public void armDown(){
//        sleep(200);
//        arm(0.15, MaxArmPos);
//        while(arm.isBusy()) {
//            telemetry.addData("arm position", arm.getCurrentPosition());
//            telemetry.update();
//
//        }
//        sleep(200);
//        arm.setPower(0);
//        sleep(200);
//
//    }



    public void tele(double power,double height) {//forward(1);forward(-1)
        //resetEncoders();   <--we dont want this
        telearm.setTargetPosition((int) (height));
//--------------------------------------------------------------------------------------------------
        telearm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//--------------------------------------------------------------------------------------------------
        telearm.setPower(1);
        //--------------------------------Telemetry, gives data about position and makes sure it doesnt stop immediately.----------------------
        while (telearm.isBusy()) {
//            telemetry.addData("lf encoder: ",arm.getCurrentPosition());
//            telemetry.addData("power: ",arm.getPower());
//            telemetry.update();
        }
        //-------------------------End While--------------------------------------------------------
        //stopping all motors
        telearm.setPower(0);
    }
    public void runWithoutEncoders() {
        lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void runToPosition()  {
        lf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void setTgPos(double distance1, double distance2, double distance3, double distance4){
        lf.setTargetPosition((int) (distance1*wheelOneInch));
        rf.setTargetPosition((int) (distance2*wheelOneInch));
        lb.setTargetPosition((int) (distance3*wheelOneInch));
        rb.setTargetPosition((int) (distance4*wheelOneInch));
    }
    public void forward(double power) {//forward(1);forward(-1);
        lf.setPower(power);
        rf.setPower(power);
        lb.setPower(power);
        rb.setPower(power);

    }

    public void forward(double power,double distance) {//forward(1);forward(-1)
        resetEncoders();
        lf.setTargetPosition((int) (distance*wheelOneInch));
        rf.setTargetPosition((int) (distance*wheelOneInch));
        lb.setTargetPosition((int) (distance*wheelOneInch));
        rb.setTargetPosition((int) (distance*wheelOneInch));
//--------------------------------------------------------------------------------------------------
        runToPosition();
//--------------------------------------------------------------------------------------------------
        forward(power);

        //--------------------------------Telematry, gives data about position and makes sure it doesnt stop immediately.-----------------------
        while (lf.isBusy() && lb.isBusy() && rf.isBusy() && rb.isBusy()) {

        }
        //-------------------------End While--------------------------------------------------------
        forward(0); //stopping all motors
    }
//----------------------------------End of forward--------------------------------------------------


    public void right(double power) {
        lf.setPower(power);
        rf.setPower(-power);
        lb.setPower(-power);
        rb.setPower(power);

    }
    public void right(double power,double distance) {//forward(1);forward(-1);
        //rb.resetDeviceConfigurationForOpMode();
        resetEncoders();
        setTgPos(distance, -distance, -distance, distance);

        runToPosition();
//        lf.setTargetPosition((int) (distance*wheelOneInch));
//        rf.setTargetPosition((int) (-distance*wheelOneInch));
//        lb.setTargetPosition((int) (distance*wheelOneInch));
//        rb.setTargetPosition((int) (-distance*wheelOneInch));

        right(power);
    }

    public void left(double power) {
        lf.setPower(-power);
        rf.setPower(power);
        lb.setPower(power);
        rb.setPower(-power);

    }
    public void left(double power,double distance) {//forward(1);forward(-1);
        //rb.resetDeviceConfigurationForOpMode();
        resetEncoders();
        setTgPos(-distance, distance, distance, -distance);

        runToPosition();
//        lf.setTargetPosition((int) (distance*wheelOneInch));
//        rf.setTargetPosition((int) (-distance*wheelOneInch));
//        lb.setTargetPosition((int) (distance*wheelOneInch));
//        rb.setTargetPosition((int) (-distance*wheelOneInch));

        left(power);
    }

    public void Stop() {
        lf.setPower(0);
        rf.setPower(0);
        lb.setPower(0);
        rb.setPower(0);

    }

    public int SpikeCheck() {
        int max1= 10;
        int max2= 20;
        int spike;
        int i=0;
        forward(0.25,1);
        left(0.25,-2);
        while(sensor.getDistance(DistanceUnit.INCH)>=30) {
            right(0.25, 2);
            i+=2;
        }
        if (i<max1)
            spike=1;
        else if (i<max2)
            spike=2;
        else
            spike=3;
        left(0.25, 2*i);

        return spike;

    }

    //this is test for the first scrimmage
    public void autoScrimmage() {

        forward(-.25, -28);
        //sleep(200);
        int spike=SpikeCheck();
        if(spike==1){ //Put yes statement here for detection of spike marker
            forward(-.25, -9);
            forward(.25, 9);
        } //end of yes statement
        else{ //Put no statement here for detection of spike marker
            right(.25,3);
            if(spike==2){ //Put yes statement here for detection of spike marker
                forward(-.25, -4);
                forward(.25, 4);
                right(-.25,-3);
            } //end of yes statement
            else { //Put no statement here for detection of spike marker
                right(-.25, -6);
                forward(-.25, -4);
                forward(.25, 4);
                right(.25, 3);
            }

        }
        //forward(.25, 28);

    }
    private double minRed=20;
    //minimum amount of red in a gamepiece
//    public boolean GetColorF(){
//        return minRed<Fsensor.red();
//    }
////    public boolean GetColorB(){
////        return minRed<Bsensor.red();
////    }
    public void turn(double power){
        lf.setPower(power);
        rf.setPower(power);
        lb.setPower(power);
        rb.setPower(power);
    }
    public void turn(double power, double degrees){
        runWithoutEncoders();
        //YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        //double initdirection = orientation.getYaw(AngleUnit.DEGREES);
        imu.resetYaw();
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        while (Math.abs (orientation.getYaw(AngleUnit.DEGREES)) <=degrees-3) {


            turn(power);
//            telemetry.addData("Yaw (Z)", "%.2f Deg. (Heading)", orientation.getYaw(AngleUnit.DEGREES));
//            telemetry.addData("Pitch (X)", "%.2f Deg.", orientation.getPitch(AngleUnit.DEGREES));
//            telemetry.addData("Roll (Y)", "%.2f Deg.\n", orientation.getRoll(AngleUnit.DEGREES));
//            telemetry.update();
            orientation = imu.getRobotYawPitchRollAngles();
        }
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

    public void initHardware()    {
        lf = hardwareMap.get(DcMotor.class, "lf");
        rf = hardwareMap.get(DcMotor.class, "rf");
        lb = hardwareMap.get(DcMotor.class, "lb");
        rb = hardwareMap.get(DcMotor.class, "rb");
        rf.setDirection(DcMotorSimple.Direction.REVERSE);
        rb.setDirection(DcMotorSimple.Direction.REVERSE);
        //DistanceSensor sensor;
        //sensor=hardwareMap.get(DistanceSensor.class, "distance");
//        Fsensor=hardwareMap.get(ColorSensor.class, "Fsensor");
        //Bsensor=hardwareMap.get(ColorSensor.class, "Bsensor");
        imu = hardwareMap.get(IMU.class, "imu");
        RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.UP;
        RevHubOrientationOnRobot.UsbFacingDirection  usbDirection  = RevHubOrientationOnRobot.UsbFacingDirection.FORWARD;

        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);
        imu.initialize(new IMU.Parameters(orientationOnRobot));
        arm = hardwareMap.get(DcMotor.class,"arm");
        claw = hardwareMap.get(Servo.class, "claw");
        telearm = hardwareMap.get(DcMotor.class, "teleArm");
        /*
        YOU MUST ADD THIS TO ANYTHING THAT USES ENCODERS AFTER DEFINING MOTORS
        lf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        */
        //set the encoders to be enabled
        //lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //sets all motors to run without encoders. remember to set a target before turning encoders on!
//        lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //lf.setDirection(DcMotor.Direction.REVERSE);
        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //lb.setDirection(DcMotor.Direction.REVERSE);
        lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        System.out.print("Initialized!");
    }


    /*public void driveRobot(double drive, double strafe, double turn) {
        // the proper formula is left stick y, left stick x, right stick x. I.E you want to input those
        // values into this function to get it to be able to drive. Current sensitivity must be defined - default
        // as of 12/16/2022 is 1.0
        lf.setPower(
                GAIN_DRIVE * drive * currentSensitivity
                        - GAIN_STRAFE * strafe * currentSensitivity
                        + GAIN_TURN * turn * currentSensitivity
        );
        rf.setPower(
                GAIN_DRIVE * drive * currentSensitivity
                        + GAIN_STRAFE * strafe * currentSensitivity
                        - GAIN_TURN * turn * currentSensitivity
        );
        lb.setPower(
                GAIN_DRIVE * drive * currentSensitivity
                        + GAIN_STRAFE * strafe * currentSensitivity
                        + GAIN_TURN * turn * currentSensitivity
        );
        rb.setPower(
                GAIN_DRIVE * drive * currentSensitivity
                        - GAIN_STRAFE * strafe * currentSensitivity
                        - GAIN_TURN * turn * currentSensitivity
        );
   }
*/

}