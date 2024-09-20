package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class ClassExample {
    public class Arm{
        DcMotor motor;
        double length;
        Servo servo;
        void move(){
            //DcMotor.setPower(1);
        }
        Arm(DcMotor motor, double length, Servo servo){
            this.motor = motor;
            this.length = length;
            this.servo = servo;
        }

    }
    void run(){
//        DcMotor motor1 = 1;
//        Arm arm1 = new Arm(motor1,5,servo5);
//        Arm arm2 = new Arm(motor100, 100,servo100);
//        arm1.move();
    }
}