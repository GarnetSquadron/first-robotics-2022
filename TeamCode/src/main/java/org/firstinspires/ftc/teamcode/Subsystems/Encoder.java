package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.ExtraMath.getAverageTimeDerivative;

import org.firstinspires.ftc.teamcode.ExtraMath;
import org.firstinspires.ftc.teamcode.TIME;
import org.firstinspires.ftc.teamcode.ValueAtTimeStamp;

import java.util.function.DoubleSupplier;

public class Encoder {
    ValueAtTimeStamp prevPos;
    DoubleSupplier supplier,velocitySupplier;
    double offset = 0,scale = 1;
    public Encoder(DoubleSupplier supplier){
        this.supplier = supplier;
        velocitySupplier = ()->getAverageTimeDerivative(prevPos,getCurrentPositionAndTime());
    }
    public Encoder(DoubleSupplier supplier,DoubleSupplier velocitySupplier){
        this.supplier = supplier;
        this.velocitySupplier = velocitySupplier;
    }
    public double getPos(){
        return (supplier.getAsDouble() + offset)*scale;
    }
    public void setPos(double pos){
        offset = pos-supplier.getAsDouble();
    }
    public void scalePos(double scale){
        this.scale = scale;
    }

    /**
     * records the current position and time. must be run in a loop constantly in order to use getVelocity()
     */
    public void updateVelocity(){
        prevPos = new ValueAtTimeStamp(getPos(), TIME.getTime());
    }
    public ValueAtTimeStamp getCurrentPositionAndTime(){
        return new ValueAtTimeStamp(supplier.getAsDouble(),TIME.getTime());
    }
    public double getVelocity(){
        return velocitySupplier.getAsDouble();
    }
}
