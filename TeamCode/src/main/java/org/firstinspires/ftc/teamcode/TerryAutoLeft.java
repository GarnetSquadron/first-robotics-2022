package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.JavaUtil;

@Autonomous(name = "TO THE LEFT TO THE LEFT EVERYTHING YOU OWN TO THE BOX TO THE LEFT")
public class TerryAutoLeft extends LinearOpMode {
    //We are defining all motors here, as to manually control each motor rather than use terry hardware.
    private DcMotor lf;
    private DcMotor rf;
    private DcMotor lb;
    private DcMotor rb;
    private DcMotor arm;
    private Servo claw;
    ColorSensor colorSensor;
    //Stop and reset encoder. Used after most movements - we want to minimize the amount of numbers we have to remember.
    public void resetEncoders() {
        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    //Sets mode to run to position. Useful if we ever swap.
    public void runToPosition()  {
        lf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    //Must be a integer. Cast argument to int (literally just do (int) (your broke variable here) )
    public void setTargetPos0() {
        lf.setTargetPosition(0);
        rf.setTargetPosition(0);
        lb.setTargetPosition(0);
        rb.setTargetPosition(0);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        lf = hardwareMap.get(DcMotor.class, "lf");
        rf = hardwareMap.get(DcMotor.class, "rf");
        lb = hardwareMap.get(DcMotor.class, "lb");
        rb = hardwareMap.get(DcMotor.class, "rb");
        arm = hardwareMap.get(DcMotor.class, "arm");
        claw = hardwareMap.get(Servo.class, "claw");
        colorSensor = hardwareMap.colorSensor.get("color");


        waitForStart();

        arm.setTargetPosition(0);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // 2,786.2 * 1/99.5 * 3/1
        //unitTicks taken from https://www.gobilda.com/5203-series-yellow-jacket-planetary-gear-motor-99-5-1-ratio-24mm-length-8mm-rex-shaft-60-rpm-3-3-5v-encoder/
        final double unitTicks = 2786.2;
        //gearRatios is 3/1
        final double gearRatios = -3;
        //this makes the full equation 2,786.2 (unitTicks) * 1/99.5 * 3/1 (gearRatios) and defines it to armRotation
        final double armRotation = (unitTicks * gearRatios);
        //1 is placeholder for now
        final double angle = 115;

        //taking angle and making it into a percent
        final double percentAngle = angle / 360;
        //will define max rotation
        final double allowedRotation = (percentAngle * armRotation);

        //Wheel encoders
        //This is the gobilda encoder value that is used
        final double wheelUnitTicks = 537.7;

        //this is the gear ratio (this is to make it looks consistent with the arm encoders)
        final double wheelGearRatios = 1;

        //multiplies wheelUnitTicks by wheelGearRatios
        final double wheelRotation = (wheelUnitTicks * wheelGearRatios);

        //This is the circumference value for the gobilda wheels we use
        final double wheelCircumference = 12.5663706144;

        //this is the wheelUnitTicks divided by wheelCircumference to get the amount of ticks to move one inch, this
        //makes it so we can just call on this value instead of pasting the number each time in code, as well as
        //the value can be multiplied by the amount of inches desired to make it more simple.
        final double wheelOneInch = (wheelRotation / wheelCircumference);


//        public void resetEncoders() {
//            lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        };
//       public void forward1(int speed, int distance) {
//
//        };

        //-2290 is the medium pos

        //Preparing the motors to have the correct mode for encoders.
        //This calls the method to set target position to 0 before continuing
        setTargetPos0();
        //Calls method
        resetEncoders();
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        runToPosition();
        claw.setPosition(0.05);
        sleep(600);
        arm.setTargetPosition(-300);
        //sleep(500);
        arm.setPower(.75);
        //-240 is the cone for detection
        sleep(500);



        //Setting target position to go 15 inches and also convert it to a integer. setTargetPosition requires it to be a integer.
        lf.setTargetPosition((int) (-15 * wheelOneInch));
        rf.setTargetPosition((int) (15 * wheelOneInch));
        lb.setTargetPosition((int) (-15 * wheelOneInch));
        rb.setTargetPosition((int) (15 * wheelOneInch));
        //Overly cautious wait, could be deleted. Just time to get out of the way while testing.
        sleep(1000);
        //Starts moving the bot - this only goes up to the target position due to the mode.
        lf.setPower(0.25);
        rf.setPower(0.25);
        lb.setPower(0.25);
        rb.setPower(0.25);
        sleep(4000);
        int SavedColor = 0;
        SavedColor = ((int)(JavaUtil.rgbToHue(colorSensor.red(),colorSensor.green(),colorSensor.blue())));
        lf.setPower(0);
        rf.setPower(0);
        lb.setPower(0);
        rb.setPower(0);
        resetEncoders();
        runToPosition();
        arm.setTargetPosition(0);
        sleep(200);
        lf.setTargetPosition((int) (-16 * wheelOneInch));
        rf.setTargetPosition((int) (16 * wheelOneInch));
        lb.setTargetPosition((int) (-16 * wheelOneInch));
        rb.setTargetPosition((int) (16 * wheelOneInch));

        lf.setPower(0.25);
        rf.setPower(0.25);
        lb.setPower(0.25);
        rb.setPower(0.25);
        sleep(2000);
        lf.setTargetPosition((int) (0 * wheelOneInch));
        rf.setTargetPosition((int) (0 * wheelOneInch));
        lb.setTargetPosition((int) (0 * wheelOneInch));
        rb.setTargetPosition((int) (0 * wheelOneInch));
        sleep(2000);
        //setTargetPos0();
        //turns off the motors, resets them. keeping big numbers in head is hard
        lf.setPower(0);
        rf.setPower(0);
        lb.setPower(0);
        rb.setPower(0);
        arm.setPower(0);

        resetEncoders();
        runToPosition();
        //turns right, to score.
        lf.setTargetPosition((int) (-5 * wheelOneInch));
        rf.setTargetPosition((int) (-4 * wheelOneInch));
        lb.setTargetPosition((int) (-5 * wheelOneInch));
        rb.setTargetPosition((int) (-4 * wheelOneInch));
        arm.setTargetPosition(-2193);

        lf.setPower(.25);
        rf.setPower(.25);
        lb.setPower(.25);
        rb.setPower(.25);
        arm.setPower(1);
        sleep(500);

        //setTargetPos0();
        // resets to keep number low

        lf.setPower(0);
        rf.setPower(0);
        lb.setPower(0);
        rb.setPower(0);
        arm.setPower(0);

        resetEncoders();
        runToPosition();
        //drives forward to scoring position
        lf.setTargetPosition((int) (-19 * wheelOneInch));
        rf.setTargetPosition((int) (19 * wheelOneInch));
        lb.setTargetPosition((int) (-19 * wheelOneInch));
        rb.setTargetPosition((int) (19 * wheelOneInch));
        arm.setTargetPosition(-2400);

        lf.setPower(.25);
        rf.setPower(.25);
        lb.setPower(.25);
        rb.setPower(.25);
        arm.setPower(.8);
        sleep(3000);
        //score a cone on a medium!!!
        claw.setPosition(0.4);
        sleep(500);
        //resets, large numbers are hard to remember
        resetEncoders();
        setTargetPos0();
        runToPosition();

        lf.setTargetPosition((int) (8 * wheelOneInch));
        rf.setTargetPosition((int) (-8 * wheelOneInch));
        lb.setTargetPosition((int) (8 * wheelOneInch));
        rb.setTargetPosition((int) (-8 * wheelOneInch));
        //Moves the bot back before lowering arm so we don't accidentally flip
        //we got very close one time! worlds first garnet squadron wheelie-No longer does a wheelie!
        sleep(600);
        arm.setTargetPosition(0);

        lf.setPower(.25);
        rf.setPower(.25);
        lb.setPower(.25);
        rb.setPower(.25);
        arm.setPower(.8);
        sleep(3000);

        claw.setPosition(0.4);
        sleep(200);

        resetEncoders();
        setTargetPos0();
        runToPosition();

//        //Turns the bot right to go into right zone
//        lf.setTargetPosition((int) (-8 * wheelOneInch));
//        rf.setTargetPosition((int) (-8 * wheelOneInch));
//        lb.setTargetPosition((int) (-8 * wheelOneInch));
//        rb.setTargetPosition((int) (-8 * wheelOneInch));
//        arm.setTargetPosition(0);
//
//        lf.setPower(.25);
//        rf.setPower(.25);
//        lb.setPower(.25);
//        rb.setPower(.25);
//        arm.setPower(.8);
//        sleep(1000);
//
//        sleep(200);
//
//        resetEncoders();
//        setTargetPos0();
//        runToPosition();
//
//        //moves bot forward more into right zone
//        lf.setTargetPosition((int) (-23 * wheelOneInch));
//        rf.setTargetPosition((int) (23 * wheelOneInch));
//        lb.setTargetPosition((int) (-23 * wheelOneInch));
//        rb.setTargetPosition((int) (23 * wheelOneInch));
//        arm.setTargetPosition(0);
//
//        lf.setPower(.25);
//        rf.setPower(.25);
//        lb.setPower(.25);
//        rb.setPower(.25);
//        arm.setPower(.8);
//        sleep(3000);
//
//        claw.setPosition(0.4);
//        sleep(200);
//
//        resetEncoders();
//        setTargetPos0();
//        runToPosition();
//        //lines up the bot parallel to drivers
//        lf.setTargetPosition((int) (10 * wheelOneInch));
//        rf.setTargetPosition((int) (10 * wheelOneInch));
//        lb.setTargetPosition((int) (10 * wheelOneInch));
//        rb.setTargetPosition((int) (10 * wheelOneInch));
//        arm.setTargetPosition(0);
//
//        lf.setPower(.25);
//        rf.setPower(.25);
//        lb.setPower(.25);
//        rb.setPower(.25);
//        arm.setPower(.8);
//        sleep(1000);
//
//        claw.setPosition(0.4);
//        sleep(200);
//
//        resetEncoders();
//        setTargetPos0();
//        runToPosition();







// this is detecting the saved color of red and giving the bot commands to move to the middle
        if(SavedColor<30){
            //turn left towards middle
            lf.setTargetPosition((int) (9 * wheelOneInch));
            rf.setTargetPosition((int) (8 * wheelOneInch));
            lb.setTargetPosition((int) (9 * wheelOneInch));
            rb.setTargetPosition((int) (8 * wheelOneInch));
            arm.setTargetPosition(0);

            lf.setPower(.25);
            rf.setPower(.25);
            lb.setPower(.25);
            rb.setPower(.25);
            arm.setPower(1);
            sleep(1000);


            lf.setPower(0);
            rf.setPower(0);
            lb.setPower(0);
            rb.setPower(0);
            arm.setPower(0);

            resetEncoders();
            runToPosition();

            //go forward towards middle
            lf.setTargetPosition((int) (-8 * wheelOneInch));
            rf.setTargetPosition((int) (8 * wheelOneInch));
            lb.setTargetPosition((int) (-8 * wheelOneInch));
            rb.setTargetPosition((int) (8 * wheelOneInch));
            arm.setTargetPosition(0);

            lf.setPower(.25);
            rf.setPower(.25);
            lb.setPower(.25);
            rb.setPower(.25);
            arm.setPower(1);
            sleep(1500);


            lf.setPower(0);
            rf.setPower(0);
            lb.setPower(0);
            rb.setPower(0);
            arm.setPower(0);

            resetEncoders();
            runToPosition();

            //angles it back straight
            lf.setTargetPosition((int) (-4 * wheelOneInch));
            rf.setTargetPosition((int) (-4 * wheelOneInch));
            lb.setTargetPosition((int) (-4 * wheelOneInch));
            rb.setTargetPosition((int) (-4 * wheelOneInch));
            arm.setTargetPosition(0);

            lf.setPower(.25);
            rf.setPower(.25);
            lb.setPower(.25);
            rb.setPower(.25);
            arm.setPower(1);
            sleep(1000);


            lf.setPower(0);
            rf.setPower(0);
            lb.setPower(0);
            rb.setPower(0);
            arm.setPower(0);

            resetEncoders();
            runToPosition();

        }
        //Finds value of SavedColor, if within set hue amount, it goes to the third parking station
        else if(SavedColor<160 && SavedColor>120){
            //Turns the bot right to go into right zone
            lf.setTargetPosition((int) (-8 * wheelOneInch));
            rf.setTargetPosition((int) (-8 * wheelOneInch));
            lb.setTargetPosition((int) (-8 * wheelOneInch));
            rb.setTargetPosition((int) (-8 * wheelOneInch));
            arm.setTargetPosition(0);

            lf.setPower(.25);
            rf.setPower(.25);
            lb.setPower(.25);
            rb.setPower(.25);
            arm.setPower(.8);
            sleep(1000);

            sleep(200);

            resetEncoders();
            setTargetPos0();
            runToPosition();

            //moves bot forward more into right zone
            lf.setTargetPosition((int) (-23 * wheelOneInch));
            rf.setTargetPosition((int) (23 * wheelOneInch));
            lb.setTargetPosition((int) (-23 * wheelOneInch));
            rb.setTargetPosition((int) (23 * wheelOneInch));
            arm.setTargetPosition(0);

            lf.setPower(.25);
            rf.setPower(.25);
            lb.setPower(.25);
            rb.setPower(.25);
            arm.setPower(.8);
            sleep(3000);

            claw.setPosition(0.4);
            sleep(200);

            resetEncoders();
            setTargetPos0();
            runToPosition();
            //lines up the bot parallel to drivers
            lf.setTargetPosition((int) (10 * wheelOneInch));
            rf.setTargetPosition((int) (10 * wheelOneInch));
            lb.setTargetPosition((int) (10 * wheelOneInch));
            rb.setTargetPosition((int) (10 * wheelOneInch));
            arm.setTargetPosition(0);

            lf.setPower(.25);
            rf.setPower(.25);
            lb.setPower(.25);
            rb.setPower(.25);
            arm.setPower(.8);
            sleep(1000);

            claw.setPosition(0.4);
            sleep(200);

            resetEncoders();
            setTargetPos0();
            runToPosition();
        }

        //looks for SavedColor for yellow, and checks if its in its range
        else if(SavedColor<100 && SavedColor>40){
            //turns it left to face towards first parking station
            lf.setTargetPosition((int) (20 * wheelOneInch));
            rf.setTargetPosition((int) (20 * wheelOneInch));
            lb.setTargetPosition((int) (20 * wheelOneInch));
            rb.setTargetPosition((int) (20 * wheelOneInch));
            arm.setTargetPosition(0);

            lf.setPower(.25);
            rf.setPower(.25);
            lb.setPower(.25);
            rb.setPower(.25);
            arm.setPower(1);
            sleep(4000);


            lf.setPower(0);
            rf.setPower(0);
            lb.setPower(0);
            rb.setPower(0);
            arm.setPower(0);

            resetEncoders();
            runToPosition();

            //drives forward into station
            lf.setTargetPosition((int) (-30 * wheelOneInch));
            rf.setTargetPosition((int) (30 * wheelOneInch));
            lb.setTargetPosition((int) (-30 * wheelOneInch));
            rb.setTargetPosition((int) (30 * wheelOneInch));
            arm.setTargetPosition(0);

            lf.setPower(.25);
            rf.setPower(.25);
            lb.setPower(.25);
            rb.setPower(.25);
            arm.setPower(1);
            sleep(4000);


            lf.setPower(0);
            rf.setPower(0);
            lb.setPower(0);
            rb.setPower(0);
            arm.setPower(0);

            resetEncoders();
            runToPosition();

            //angles for it to be straight in
            lf.setTargetPosition((int) (-12 * wheelOneInch));
            rf.setTargetPosition((int) (-12 * wheelOneInch));
            lb.setTargetPosition((int) (-12 * wheelOneInch));
            rb.setTargetPosition((int) (-12 * wheelOneInch));
            arm.setTargetPosition(0);

            lf.setPower(.25);
            rf.setPower(.25);
            lb.setPower(.25);
            rb.setPower(.25);
            arm.setPower(1);
            sleep(1500);


            lf.setPower(0);
            rf.setPower(0);
            lb.setPower(0);
            rb.setPower(0);
            arm.setPower(0);

            resetEncoders();
            runToPosition();

        }
        //makes an else if no other value detected, makes a failsafe so it goes to a parking station anyways
        else{
            lf.setTargetPosition((int) (9 * wheelOneInch));
            rf.setTargetPosition((int) (8 * wheelOneInch));
            lb.setTargetPosition((int) (9 * wheelOneInch));
            rb.setTargetPosition((int) (8 * wheelOneInch));
            arm.setTargetPosition(0);

            lf.setPower(.25);
            rf.setPower(.25);
            lb.setPower(.25);
            rb.setPower(.25);
            arm.setPower(1);
            sleep(1000);

            // resets to keep number low

            lf.setPower(0);
            rf.setPower(0);
            lb.setPower(0);
            rb.setPower(0);
            arm.setPower(0);

            resetEncoders();
            runToPosition();

            lf.setTargetPosition((int) (-8 * wheelOneInch));
            rf.setTargetPosition((int) (8 * wheelOneInch));
            lb.setTargetPosition((int) (-8 * wheelOneInch));
            rb.setTargetPosition((int) (8 * wheelOneInch));
            arm.setTargetPosition(0);

            lf.setPower(.25);
            rf.setPower(.25);
            lb.setPower(.25);
            rb.setPower(.25);
            arm.setPower(1);
            sleep(1500);

            //setTargetPos0();
            // resets to keep number low

            lf.setPower(0);
            rf.setPower(0);
            lb.setPower(0);
            rb.setPower(0);
            arm.setPower(0);

            resetEncoders();
            runToPosition();

            lf.setTargetPosition((int) (-4 * wheelOneInch));
            rf.setTargetPosition((int) (-4 * wheelOneInch));
            lb.setTargetPosition((int) (-4 * wheelOneInch));
            rb.setTargetPosition((int) (-4 * wheelOneInch));
            arm.setTargetPosition(0);

            lf.setPower(.25);
            rf.setPower(.25);
            lb.setPower(.25);
            rb.setPower(.25);
            arm.setPower(1);
            sleep(1000);

            // resets to keep number low

            lf.setPower(0);
            rf.setPower(0);
            lb.setPower(0);
            rb.setPower(0);
            arm.setPower(0);

            resetEncoders();
            runToPosition();

        }





        //telemetry for debug
        while (opModeIsActive()) {
            //low is -1325
            //537.7 ppr encoder resolution
            //12.5663706144 wheel circumference
            //arm.setTargetPosition(-2193);
            //
            //Unused code for scoring. Later will be used.
            //
            //terryHardware.setClawPosition(0.05);
            //sleep(400);
            //arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            //arm.setPower(0.4);


            //Bonus telemetry. Can be safely deleted. Only used for testing.
            telemetry.addData("Current Position", arm.getCurrentPosition());
            telemetry.addData("Target Position", arm.getTargetPosition());
            telemetry.addData("Allowed Rotation", allowedRotation);
            telemetry.addData("One Inch Value", wheelOneInch);
            telemetry.addData("lf target pos", lf.getTargetPosition());
            telemetry.addData("rf target pos", rf.getTargetPosition());
            telemetry.addData("lb target pos", lb.getTargetPosition());
            telemetry.addData("rb target pos", rb.getTargetPosition());
            telemetry.addData("Red",colorSensor.red());
            telemetry.addData("Green",colorSensor.green());
            telemetry.addData("Blue",colorSensor.blue());
            telemetry.addData("Alpha",colorSensor.alpha());
            telemetry.addData("ARGB", colorSensor.argb());
            telemetry.addData("hue ig???", JavaUtil.rgbToHue(colorSensor.red(),colorSensor.green(),colorSensor.blue()));
            telemetry.addData("stored color", SavedColor);
            telemetry.update();
        }
    }

    ;

}


