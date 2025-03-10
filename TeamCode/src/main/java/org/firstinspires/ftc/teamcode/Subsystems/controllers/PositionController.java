package org.firstinspires.ftc.teamcode.Subsystems.controllers;

import org.firstinspires.ftc.teamcode.ExtraMath;
import org.firstinspires.ftc.teamcode.Subsystems.Encoder;

public abstract class PositionController extends Controller{
    double targetPosition, tolerance = 0;
    public void setTargetPosition(double targetPosition){
        this.targetPosition = targetPosition;
    }
    public void setTolerance(double tolerance){
        this.tolerance = tolerance;
    }
    public double getTargetPosition(){
        return targetPosition;
    }
    public boolean targetReached(){
        return ExtraMath.ApproximatelyEqualTo(targetPosition,encoder.getPos(),tolerance);
    }
    public double calculateIfWithinTolerance(){
        if(targetReached()){
            return 0;
        }
        else {
            return calculate();
        }
    }
}
