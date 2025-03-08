package org.firstinspires.ftc.teamcode;

public class ValueAtTimeStamp {
    final double value;
    final double timeStamp;
    public ValueAtTimeStamp(double value, double timeStamp){
        this.value = value;
        this.timeStamp = timeStamp;
    }
    public double getValue(){
        return value;
    }
    public double getTimeStamp(){
        return timeStamp;
    }

}
