package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.ExtraMath.getAverageTimeDerivative;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;

import org.firstinspires.ftc.teamcode.ExtraMath;
import org.firstinspires.ftc.teamcode.TIME;
import org.firstinspires.ftc.teamcode.ValueAtTimeStamp;
import org.firstinspires.ftc.teamcode.enums.AngleUnitV2;

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
    public Encoder(Motor motor){
        this(motor::getCurrentPosition, (motor.getClass()==MotorEx.class)?(((MotorEx)motor)::getVelocity):(motor::getRate));
        scaleToAngleUnit(motor,AngleUnitV2.RADIANS);//Defaults to radians because radians are great
    }
    public double getPos(){
        return (supplier.getAsDouble() + offset)*scale;
    }
    public void setPos(double pos){
        offset = pos-supplier.getAsDouble();
    }
    public void setScale(double scale){
        this.scale = scale;
    }
    public void scaleScaleBy(double scale){
        this.scale*=scale;
    }
    public void scaleToAngleUnit(Motor motor, AngleUnitV2 unit){
        scale = ExtraMath.ConvertAngleUnit(2/(motor.getCPR()),AngleUnitV2.REVOLUTIONS,unit);//it was off by a factor of 2 so I added the 2, idk why its like that
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
    public boolean isStopped(){
        return getVelocity()==0;
    }
}
