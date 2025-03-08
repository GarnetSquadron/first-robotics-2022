package org.firstinspires.ftc.teamcode.Subsystems.controllers;

import org.firstinspires.ftc.teamcode.ExtraMath;
import org.firstinspires.ftc.teamcode.Subsystems.Encoder;
import org.firstinspires.ftc.teamcode.TIME;
import org.firstinspires.ftc.teamcode.ValueAtTimeStamp;

import java.util.function.DoubleSupplier;

public class PIDCon extends PositionController {
    double kp,ki,kd;
//    double prevTimeStamp;
//    double prevPos;
    ValueAtTimeStamp prevPos;
    double integral;
    public PIDCon(double kp, double ki, double kd, Encoder encoder){
        super(encoder);
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
    }
    public void reset(){
        integral = 0;
        prevPos = new ValueAtTimeStamp(0,TIME.getTime());
    }
    @Override
    public double calculate() {
        double error = targetPosition-position.getAsDouble();
        integral+= ExtraMath.integration.trapazoid(prevPos,new ValueAtTimeStamp(error, TIME.getTime()));
        prevPos = new ValueAtTimeStamp(error,TIME.getTime());
        return kp*error+ki*integral+kd*velocity.getAsDouble();
    }
}
