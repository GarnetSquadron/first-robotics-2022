package org.firstinspires.ftc.teamcode;
//todo: import bob hardware then literally write a entire auto with encoders

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;



@Autonomous(name="bob first auto", group="Robot")
@Disabled

public class bobfirstauto extends LinearOpMode {
    //define motors and servos. We're doing this outside of BobHardware because I want to use encoders.
    //...i want to use encoders the non easy way.
    //And frankly, do YOU understand encoders? Probably. But I don't. I'm also probably having
    //a mental crisis, just like the rest of the programming team. -Bass
    private DcMotor lf;
    private DcMotor rf;
    private DcMotor lb;
    private DcMotor rb;
    @Override
    public void runOpMode() {
        lf = hardwareMap.get(DcMotor.class, "lf");
        rf = hardwareMap.get(DcMotor.class, "rf");
        lb = hardwareMap.get(DcMotor.class, "lb");
        rb = hardwareMap.get(DcMotor.class, "rb");

        lf.setDirection(DcMotor.Direction.REVERSE);
        rf.setDirection(DcMotor.Direction.REVERSE);
        lb.setDirection(DcMotor.Direction.REVERSE);
        rb.setDirection(DcMotor.Direction.REVERSE);

        lf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rb.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        waitForStart();
        lf.setTargetPosition(1608);
        rf.setTargetPosition(1608);
        lb.setTargetPosition(1608);
        rb.setTargetPosition(1608);

        lf.setPower(0.25);
        rf.setPower(0.25);
        lb.setPower(0.25);
        rb.setPower(0.25);
    }
}
