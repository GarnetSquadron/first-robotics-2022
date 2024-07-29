package org.firstinspires.ftc.teamcode.oldStuff;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.IMU;


import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;
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

//@TeleOp(name="Dont touch this plz", group=":p")
public class VoidsAndThings extends LinearOpMode{

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
    //Above comment is out dated i believe fyi. dont wana delete jus in case

    private DcMotor Test = null;
    private NullableHardware test;
    private DcMotor lf = null;
    private DcMotor rf = null;
    private DcMotor lb = null;
    private DcMotor rb = null;
    private DcMotor telearm = null;
    private DistanceSensor sensor = null;
    private DcMotor lift = null;
    private Servo claw = null;
    private Servo Funnel = null;
    private DcMotor XOdometryEnc;
    private DcMotor YOdometryEnc;
    /*
    private DcMotor LF = null;
    private DcMotor RF = null;
    private DcMotor LB = null;
    private DcMotor RB = null;
    private DcMotor Telearm = null;
    private DistanceSensor Sensor = null;
    private DcMotor Lift = null;
    private Servo Claw = null;
    private Servo Funnel = null;

    private NullableHardware lf;
    private NullableHardware rf;
    private NullableHardware lb;
    private NullableHardware rb;
    private NullableHardware telearm;
    private NullableHardware sensor;
    private NullableHardware;
    private NullableHardware;
    private NullableHardware;
     */
    //ColorSensor Fsensor;
    //ColorSensor Bsensor;


    private DcMotor arm = null;

    //ColorSensor colorSensor;
    IMU imu = null;
    //Wheel encoders

    OdometryPositionHandler OPH;
    double robotWidth;
    double robotLength;
    double OdometryXseperation;
    double OdometryYseperation;
    double lPower;
    double rPower;
    double lunscaled;
    double runscaled;
    double Power;
    boolean moving;
    double disx;
    double disy;
    final double wheelUnitTicks;
    final double OdometryWheelRotation;
    final double OdometryTickPerInch;

    //this is the gear ratio (this is to make it looks consistent with the arm encoders)
    final double wheelGearRatios = 1;

    //multiplies wheelUnitTicks by wheelGearRatios
    final double wheelRotation;


    final double wheelCircumference;

    final double OdometryWheelDiameter;
    final double OdometryWheelCircumference;

    final double wheelTickPerInch;
    //private HardwareMap robotHardware; // gain access to methods in the calling OpMode.
    // Define a constructor that allows the OpMode to pass a reference to itself.

    /**
     * hi. just always remember to measure the wheels at the point of contact with the floor.
     * @param voidsAndThings hardware map
     * @param width the width measured from the point of contact of one mechanum wheel with the floor to the point of contact of the one across the width
     * @param length the length, measured in the sam way as the width
     * @param wC the circumference of the mechanum wheels
     * @param oX the difference in x from the two wheels
     * @param oY
     * @param oD the diameer of the odometry wheels
     */
//    public VoidsAndThings(HardwareMap voidsAndThings, double width, double length,double wC,double oX,double oY, double oD) {
//        hardwareMap = voidsAndThings;
//        robotWidth = width;
//        robotLength = length;
//        wheelCircumference = wC;
//        wheelRotation = (wheelUnitTicks * wheelGearRatios);
//        OdometryXseperation = oX;
//        OdometryYseperation = oY;
//        OdometryWheelDiameter = oD;
//        OdometryWheelCircumference = OdometryWheelDiameter*Math.PI;
//
//        hardwareMap = voidsAndThings;
//
//        //-------------mechanum wheel measurements------------
//        robotWidth = width;
//        robotLength = length;
//        //This is the circumference value for the gobild wheels we use in inches
//        wheelCircumference = wC;
//        //This is the gobilda encoder value that is used
//        wheelUnitTicks = 537.7;
//        wheelRotation = (wheelUnitTicks * wheelGearRatios);
//        //this is the wheelUnitTicks divided by wheelCircumference to get the amount of ticks to move one inch, this
//        //makes it so we can just call on this value instead of pasting the number each time in code, as well as
//        //the value can be multiplied by the amount of inches desired to make it more simple.
//        wheelOneInch = (wheelRotation / wheelCircumference);
//        //--------------odometry measurements-------------
//        OdometryXseperation = 5;//random number, ill change later
//        OdometryYseperation = 5;//random number, ill change later
//        //diameter is 48mm = 1.89 inches
//        OdometryWheelDiameter = 1.89;
//        OdometryWheelCircumference = OdometryWheelDiameter*Math.PI;
//        OdometryWheelUnitTicks = 537.7;
//
//    }

    /**
     * hi.
     * @param voidsAndThings
     */
    public VoidsAndThings(HardwareMap voidsAndThings) {
        hardwareMap = voidsAndThings;
        OPH = new OdometryPositionHandler(hardwareMap);
        //-------------mechanum wheel measurements------------
        robotWidth = 18;
        robotLength = 18;
        //This is the circumference value for the gobild wheels we use in inches
        wheelCircumference = 12.5663706144;
        //This is the gobilda encoder value that is used
        wheelUnitTicks = 537.7;
        wheelRotation = (wheelUnitTicks * wheelGearRatios);
        //this is the wheelUnitTicks divided by wheelCircumference to get the amount of ticks to move one inch, this
        //makes it so we can just call on this value instead of pasting the number each time in code, as well as
        //the value can be multiplied by the amount of inches desired to make it more simple.
        //this is dj- idk why they called it "wheeloneinch"? i renamed it to wheelTickPerInch caus that makes much more sense
        wheelTickPerInch = (wheelRotation / wheelCircumference);
        //--------------odometry measurements-------------
        OdometryXseperation = 5;//random number, ill change later
        OdometryYseperation = 5;//random number, ill change later
        //diameter is 48mm = 1.89 inches
        OdometryWheelDiameter = 1.89;
        OdometryWheelCircumference = OdometryWheelDiameter*Math.PI;
        OdometryWheelRotation = 2000;
        OdometryTickPerInch = (OdometryWheelRotation / OdometryWheelCircumference);

        lPower=100;
        rPower=-100;
        lunscaled=200;
        runscaled=300;
        Power=20;
        moving=false;

    }




    private HardwareMap hardwareMap;
    private double getLFromXY(double x,double y){
        return (y-x)/Math.sqrt(2);
    }
    private double getRFromXY(double x,double y){
        return (y+x)/Math.sqrt(2);
    }
    private double getXFromLR(double l,double r){
        return (r-l)/Math.sqrt(2);
    }
    private double getYFromLR(double l,double r){
        return (r+l)/Math.sqrt(2);
    }
    public double getX(){
        return OPH.getArcsX();
    }
    public double getY(){
        return OPH.getArcsY();
    }
//    public double updatePos(){
//
//    }
    public double getTheta(){
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }
    private double getX(double originalX){
        return getX()-originalX;
    }
    private double getY(double originalY){
        return getY()-originalY;
    }
    private double getTheta(double originalTheta){
        return getTheta()-originalTheta;
    }



    /**
     *this tries to find a part, but doesnt stop the entire program if it cannot find a part.
     *that way, we can take off parts and not worry as much as possible.
     *It might not be possible to get this to work fyi
     */
//    private <T extends HardwareDevice> T GetHardwareCatchError(Class<T> Part, String name) {
//
//        try {
//            return hardwareMap.get(Part, name);
//        } catch (Exception e) {
//            System.out.print("could not find " + Part + ".");
//            return null;
//        }
//    }
//    private <T extends HardwareDevice> void testMoveMotor(Class<T> part, ){
//        DcMotor H = null;
//        H.setPower(9);
//
//    }
    public void moveWheels(double LF,double RF,double LB,double RB){
        lf.setPower(LF);
        rf.setPower(RF);
        lb.setPower(LB);
        rb.setPower(RB);
    }
    /**
     * make the wheels turn a given distance. note that this is just the distance the encoders count, not necisarily the distance the wheels move that part of the bot.
     * @param lfDistance the distance lf moves
     * @param rfDistance the distance rf moves
     * @param lbDistance the distance lb moves
     * @param rbDistance the distance rb moves
     */
    public void setWheelTgPos(double lfDistance, double rfDistance, double lbDistance, double rbDistance){
        lf.setTargetPosition((int) (lfDistance* wheelTickPerInch));
        rf.setTargetPosition((int) (rfDistance* wheelTickPerInch));
        lb.setTargetPosition((int) (lbDistance* wheelTickPerInch));
        rb.setTargetPosition((int) (rbDistance* wheelTickPerInch));
    }

    public void TestTheTest(){
        test.Run("setPower",0.5);
    }

    /**
     * generously assumes no sliding and a perfectly square chassis, but... SWERVE DRIVE!!!!!! but really the calculations are easy
     * @param l
     * @param r
     * @param rotationalPower
     */
    public void TheoreticalSwerveDrive(double l, double r,double rotationalPower) {

        moveWheels(r-rotationalPower, r+rotationalPower, l-rotationalPower, r+rotationalPower);
    }

    /**
     * moves as a sum of two diagonal(since mechanum wheels apply a diagonal force) components, l and r.
     * @param l the left diagonal component
     * @param r the right diagonal component
     */
    public void moveLinearly(double l, double r){
//        moveWheels(r,l,l,r);
        TheoreticalSwerveDrive(l,r,0);
    }
//    public void TheoreticalSwerveDrive(double l, double r, double distance, double degrees){
//        runWithoutEncoders();
//        //YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
//        //double initdirection = orientation.getYaw(AngleUnit.DEGREES);
//        double originalOrientation = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
//        double orientation = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
//        while (orientation-originalOrientation-degrees!=0) {
//
//
//            turn(power*Math.signum(orientation-originalOrientation-degrees));
////            telemetry.addData("Yaw (Z)", "%.2f Deg. (Heading)", orientation.getYaw(AngleUnit.DEGREES));
////            telemetry.addData("Pitch (X)", "%.2f Deg.", orientation.getPitch(AngleUnit.DEGREES));
////            telemetry.addData("Roll (Y)", "%.2f Deg.\n", orientation.getRoll(AngleUnit.DEGREES));
////            telemetry.update();
////            oldOrientation = orientation;
//            orientation = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
//        }
//    }


    /**
     * turns with a given power, clockwise for positive power and counter clockwise for nevgative power
     * @param power power of the motors, must be between 1 and -1
     */
    public void turn(double power){
        //moveWheels(-power, power,-power,power);
        TheoreticalSwerveDrive(0,0,power);
    }

    /**
     * turns with the imu to a given degrees and power
     * @param power sign determines counterclockwise or clockwise
     * @param degrees sign doesn't matter
     */
    public void turn(double power, double degrees){
        runWithoutEncoders();
        //YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        //double initdirection = orientation.getYaw(AngleUnit.DEGREES);
        double originalOrientation = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
        double orientation = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
        while (orientation-originalOrientation-degrees!=0) {


            turn(power*Math.signum(orientation-originalOrientation-degrees));
//            telemetry.addData("Yaw (Z)", "%.2f Deg. (Heading)", orientation.getYaw(AngleUnit.DEGREES));
//            telemetry.addData("Pitch (X)", "%.2f Deg.", orientation.getPitch(AngleUnit.DEGREES));
//            telemetry.addData("Roll (Y)", "%.2f Deg.\n", orientation.getRoll(AngleUnit.DEGREES));
//            telemetry.update();
//            oldOrientation = orientation;
            orientation = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
        }
    }

    /**
     * turns to an angle, not by an angle
     * @param power
     * @param degrees
     */
    public void turnTo(double power, double degrees){
        while(getTheta()-degrees>1||-1>getTheta()-degrees){
            if( 180 < Math.abs(getTheta()-degrees))
                turn(Math.signum(degrees-getTheta())*power);
            else
                turn(Math.signum(getTheta()-degrees)*power);
        }
        turn(0);
    }

    /**
     * Uses encoders to move linearly a specific distance
     * @param l
     * @param r
     * @param distance
     */
    public void MoveLinearlyEncoders(double l, double r, double distance, boolean rescale) {//forward(1);forward(-1)
        resetEncoders();
        double rScaled;
        double lScaled;
        if(rescale) {
            rScaled = r / Math.hypot(l, r);
            lScaled = l / Math.hypot(l, r);
        }
        else{
            rScaled = r;
            lScaled = l;
        }
        setWheelTgPos(rScaled*distance,lScaled*distance,lScaled*distance,rScaled*distance);
//--------------------------------------------------------------------------------------------------
        runToPosition();
//--------------------------------------------------------------------------------------------------
        moveLinearly(l,r);

        //--------------------------------Telemetry, gives data about position and makes sure it doesn't stop immediately.-----------------------
        while (lf.isBusy() && lb.isBusy() && rf.isBusy() && rb.isBusy()) {

        }
        //-------------------------End While--------------------------------------------------------
        moveLinearly(0,0); //stopping all motors
    }

    /**
     * moves linearly a given distance
     * @param l the left diagonal vector
     * @param r the right diagonal vector
     * @param distance the distance it travels
     */
    public void MoveLinearlyEncoders(double l, double r, double distance) {//forward(1);forward(-1)
        resetEncoders();
        setWheelTgPos(
                getUnitXComponentWithRatio(l,r)*distance,
                getUnitXComponentWithRatio(r,l)*distance,
                getUnitXComponentWithRatio(r,l)*distance,
                getUnitXComponentWithRatio(l,r)*distance);
//--------------------------------------------------------------------------------------------------
        runToPosition();
//--------------------------------------------------------------------------------------------------
        moveLinearly(l,r);

        //--------------------------------Telematry, gives data about position and makes sure it doesnt stop immediately.-----------------------
        while (lf.isBusy() && lb.isBusy() && rf.isBusy() && rb.isBusy()) {

        }
        //-------------------------End While--------------------------------------------------------
        moveLinearly(0,0); //stopping all motors
    }

    /**
     * given an x component and a y component of a vector, it computes the x component of the parralel unit vector pointing in the same direction.
     * @param x the x component of the unscaled vector
     * @param y the y component of the unscaled vector
     * @return the x component of the scaled (unit) vector
     */

    private double getUnitXComponentWithRatio(double x,double y){
        /*
        The Math:
        X^2+Y^2 = 1,
        Y = Xy/x,
        X^2+X^2(y/x)^2=1,
        X^2(1+(y/x)^2) = 1,
        X = sqrt(1/(1+(y/x)^2))
        QED
        */
        return Math.sqrt(1/(1+Math.pow((y/x),2)));
    }
    /**
     * this makes the robot straife with a velocity vector with an angle(the angle from the forward facing line (the line that divides
     * the robot into two halves(yes I am using nested parentheses in a sentence(fight me ):-) )))) and magnitude as direction and power.
     * In other words, it takes the circle with a radius power and moves with a velocity vector on a radius that points in the direction.
     * Note that this means that it cant always go full speed.
     * @param direction the direction counterclockwise i think
     * @param power the power duh
     */
    public void moveCircle(double direction, double power) {
        double direction2 = direction+Math.PI/4;
        moveLinearly(Math.cos(direction2) * power,Math.sin(direction2) * power);
    }
    /**
     * this is like moveCircle(), with a direction and power, but the corresponding vector is on a square which is scaled by power, not a circle. This way it can go full speed.
     * @param direction
     * @param power
     */
    public void moveSquare(double direction, double power){
        double direction2 = direction+Math.PI/4;
        double l = Math.max(1,Math.tan(direction2));
        double r = Math.max(1,1/Math.tan(direction2));
        moveLinearly(SquareCoordinate(direction2)*power,SquareCoordinate(direction2-Math.PI/2)*power);//this line is a formula I made to replace the commented code below. That way its less redundant lol. Have fun decoding it :p
//        if(direction%(2*Math.PI)>=0 && direction%(2*Math.PI)<Math.PI/2) {
//            moveLinearly(Math.tan(direction2) * power,power);
//        }
//        if(direction%(2*Math.PI)>=Math.PI/2 && direction%(2*Math.PI)<Math.PI) {
//            moveLinearly(power,Math.tan(direction2) * power);
//        }
//        if(direction%(2*Math.PI)>=Math.PI && direction%(2*Math.PI)<3*Math.PI/2) {
//            moveLinearly(Math.tan(direction2) * power,-power);
//        }
//        if(direction%(2*Math.PI)>=3*Math.PI/2 && direction%(2*Math.PI)<2*Math.PI) {
//            moveLinearly(-power,Math.tan(direction2) * power);
//        }
    }
    /**
     * here's the breakdown: Take the perpendicular bisector to the side of the square that the vector is on. call the angle between that bisector and the
     * vector theta. Then the length from the midpoint of the side to the tip of the vector is tan(theta). The vector is of course made of the two
     * perpendicular components corresponding to the forces of the individual wheels (two wheels have one component and two have the other). Whenever
     * one component is tan(theta), the other must be 1. This function figures out which one is which.
     * it def works, i tried it on desmos and it works like a charm
     * @param theta the angle counterclockwise i think
     * */
    public double SquareCoordinate(double theta){
        if (Double.isNaN(Math.tan(theta))){//check for when the tangent is undefined
            return Math.signum(Math.sin(theta));//returns the sign of the sine lol. This is always the correct number cause' infinity > 1
        }
        return Math.max(Math.min(Math.min(Math.signum(Math.sin(theta)),Math.abs(Math.tan(theta))),1),-1);//this line is a beautiful formula I made in desmos
    }


    public void moveSquareToWithOdometry(double power, double x, double y){
        runWithoutEncoders();

        double StartX = OPH.getLinesX();
        double StartY = OPH.getLinesY();
        double DisX = StartX-x;
        double DisY = StartY-y;
        //double StartTheta = getTheta();
        double DisL = getLFromXY(DisX,DisY);
        double DisR = getRFromXY(DisX,DisY);

        moving = !(DisX==0 && DisY == 0)&& opModeIsActive();
        while (!(DisX==0 && DisY == 0)){
            moving = !(DisX==0 && DisY == 0);
            DisX = x-OPH.getArcsX();
            DisY = y-OPH.getArcsY();
            DisL = getLFromXY(DisX,DisY);
            DisR = getRFromXY(DisX,DisY);
            disx=DisX;
            disy=DisY;
            lPower =Math.signum(DisL)*Math.min(1,Math.abs(DisL/DisR))*Math.abs(power);
            rPower =Math.signum(DisL)*Math.min(1,Math.abs(DisR/DisL))*Math.abs(power);
            lunscaled =Math.min(1,DisL/DisR);
            runscaled =Math.min(1,DisR/DisL);
            Power =Math.abs(power);
            moving = !(DisX==0 && DisY == 0);


            moveLinearly(lPower, rPower);

//            if (
//                    !(
//                            (getX(StartX) == 0 && getY(StartY) == 0)
//                            ||
//                            (getX(StartX) / getY(StartY) == x / y )
//                    )
//            ) {
//            }
        }

//        while(getTheta()!=StartTheta){
//            turnTo(power,StartTheta);
//        }
    }

//hello


    /**
     * It resets the encoders SPECIFICALLY in the wheels
     */
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


    /**
     * moves the telearm
     * @param power the desired power (1 to -1 of course)
     * @param height the desired height
     */
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

    /**
     * set the wheel's runmode to run without encoder
     */
    public void runWithoutEncoders() {
        lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /**
     * set the wheel's runmode to run to position
     */
    public void runToPosition()  {
        lf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }



    public void forward(double power) {//forward(1);forward(-1);
        moveLinearly(power,power);

    }

    public void forward(double power,double distance) {//forward(1);forward(-1)
        resetEncoders();
        setWheelTgPos(distance,distance,distance,distance);
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

    /**
     * strafes sideways with mechanum wheels with a given power
     * @param power positive to move right, negative to move left, ranges from 1 to -1 of course
     */
    public void strafe(double power) {
        lf.setPower(power);
        rf.setPower(-power);
        lb.setPower(-power);
        rb.setPower(power);

    }

    /**
     * trafes sideways with mechanum wheels with a given power and distance
     * @param power positive to move right, negative to move left, ranges from 1 to -1 of course
     * @param distance however far you are going to strafe, not so accurate in strafe for whatever reason
     */
    public void strafe(double power, double distance) {//forward(1);forward(-1);
        //rb.resetDeviceConfigurationForOpMode();
        resetEncoders();
        setWheelTgPos(distance, -distance, -distance, distance);

        runToPosition();
//        lf.setTargetPosition((int) (distance*wheelOneInch));
//        rf.setTargetPosition((int) (-distance*wheelOneInch));
//        lb.setTargetPosition((int) (distance*wheelOneInch));
//        rb.setTargetPosition((int) (-distance*wheelOneInch));

        strafe(power);
    }

//    public void left(double power) {
//        lf.setPower(-power);
//        rf.setPower(power);
//        lb.setPower(power);
//        rb.setPower(-power);
//
//    }
//    public void left(double power,double distance) {//forward(1);forward(-1);
//        //rb.resetDeviceConfigurationForOpMode();
//        resetEncoders();
//        setTgPos(-distance, distance, distance, -distance);
//
//        runToPosition();
////        lf.setTargetPosition((int) (distance*wheelOneInch));
////        rf.setTargetPosition((int) (-distance*wheelOneInch));
////        lb.setTargetPosition((int) (distance*wheelOneInch));
////        rb.setTargetPosition((int) (-distance*wheelOneInch));
//
//        left(power);
//    }


    /**
     * for recognizing things with computer vision in 2023-2024 season;)
     * @return the spike number
     */
    public int getSpikeMarkVision() {

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

    //this is test for the first scrimmage
//    public void autoScrimmage() {
//
//        forward(-.25, -28);
//        //sleep(200);
//        int spike=SpikeCheck();
//        if(spike==1){ //Put yes statement here for detection of spike marker
//            forward(-.25, -9);
//            forward(.25, 9);
//        } //end of yes statement
//        else{ //Put no statement here for detection of spike marker
//            right(.25,3);
//            if(spike==2){ //Put yes statement here for detection of spike marker
//                forward(-.25, -4);
//                forward(.25, 4);
//                right(-.25,-3);
//            } //end of yes statement
//            else { //Put no statement here for detection of spike marker
//                right(-.25, -6);
//                forward(-.25, -4);
//                forward(.25, 4);
//                right(.25, 3);
//            }
//
//        }
//        //forward(.25, 28);
//
//    }

    private double minRed=20;
    //minimum amount of red in a gamepiece
//    public boolean GetColorF(){
//        return minRed<Fsensor.red();
//    }
////    public boolean GetColorB(){
////        return minRed<Bsensor.red();
////    }

    /**
     * initializes the computer vision. plz delete it after using to save memory during the program
     */
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

    /**
     * initialize all that good hardware
     */
    public void initHardware()    {
        OPH.initHardware();
        //lf = GetHardwareCatchError(DcMotor.class, "lf");
        lf = hardwareMap.get(DcMotor.class, "lf");
        rf = hardwareMap.get(DcMotor.class, "rf");
        lb = hardwareMap.get(DcMotor.class, "lb");
        rb = hardwareMap.get(DcMotor.class, "rb");
        XOdometryEnc = hardwareMap.get(DcMotor.class, "lift");
        YOdometryEnc = hardwareMap.get(DcMotor.class, "y encoder");
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

        test = new NullableHardware(Test);
        test.InitializeHardwareCatchError("test", hardwareMap);
        //lf.setDirection(DcMotor.Direction.REVERSE);
//        lb.setDirection(DcMotor.Direction.REVERSE);
//        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//
//        lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        imu.resetYaw();
        XOdometryEnc.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        YOdometryEnc.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

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

    @Override
    public void runOpMode() {}

}