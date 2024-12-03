package org.firstinspires.ftc.teamcode;

import java.util.TimerTask;
import java.util.function.DoubleSupplier;

public class TTimer {
    double duration;
    double startTime;
    DoubleSupplier TimeSinceInit;
    boolean timeStarted;
    public TTimer(double duration, DoubleSupplier SystemTime){
        this.duration = duration;
        TimeSinceInit = SystemTime;
        timeStarted = false;
    }
    public void StartTimer(){
        startTime = TimeSinceInit.getAsDouble();
        timeStarted = true;

    }
    public boolean timestarted(){
        return timeStarted;
    }
    public boolean timeover(){
        return duration+startTime<TimeSinceInit.getAsDouble();
    }

}
