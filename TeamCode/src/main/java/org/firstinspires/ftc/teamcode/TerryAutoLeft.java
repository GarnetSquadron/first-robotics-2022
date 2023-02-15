package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "TO THE LEFT TO THE LEFT EVERYTHING YOU OWN TO THE BOX TO THE LEFT")
public class TerryAutoLeft extends LinearOpMode {
    //We are defining all motors here, as to manually control each motor rather than use terry hardware.
    private DcMotor lf;
    private DcMotor rf;
    private DcMotor lb;
    private DcMotor rb;
    private DcMotor arm;

    @Override
    public void runOpMode() throws InterruptedException {
        lf = hardwareMap.get(DcMotor.class, "lf");
        rf = hardwareMap.get(DcMotor.class, "rf");
        lb = hardwareMap.get(DcMotor.class, "lb");
        rb = hardwareMap.get(DcMotor.class, "rb");
        arm = hardwareMap.get(DcMotor.class, "arm");


        waitForStart();

        arm.setTargetPosition(0);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
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
        //This is the gobuilda encoder value that is used
        final double wheelUnitTicks = 537.7;

        //this is the gear ratio (this is to make it looks consistent with the arm encoders)
        final double wheelGearRatios = 1;

        //multiplies wheelUnitTicks by wheelGearRatios
        final double wheelRotation = (wheelUnitTicks * wheelGearRatios);

        //This is the circumference value for the gobuilda wheels we use
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
        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        lf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //Setting target position to go 12 inches and also convert it to a integer. setTargetPosition requires it to be a integer.
        lf.setTargetPosition((int) (12 * wheelOneInch));
        rf.setTargetPosition((int) (12 * wheelOneInch));
        lb.setTargetPosition((int) (12 * wheelOneInch));
        rb.setTargetPosition((int) (12 * wheelOneInch));
        //Overly cautious wait, could be deleted. Just time to get out of the way while testing.
        sleep(1000);
        //Starts moving the bot - this only goes up to the target position due to the mode.
        lf.setPower(0.25);
        rf.setPower(0.25);
        lb.setPower(0.25);
        rb.setPower(0.25);
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
            telemetry.update();
        }
    }

    ;


