package org.firstinspires.ftc.teamcode.time;

import java.util.function.DoubleSupplier;

/**
 * easier to use than util.Timer and has slightly different functionality. you can get timeover() to
 * see if the time is up, but this cannot schedule tasks like utils.Timer can
 */

public class TTimer
{
    double duration;
    double startTime;
    DoubleSupplier TimeSinceInit;
    boolean timeStarted;

    public TTimer(DoubleSupplier SystemTime)
    {
        duration = 0;
        TimeSinceInit = SystemTime;
        timeStarted = false;
    }

    public void StartTimer(double duration)
    {
        startTime = TimeSinceInit.getAsDouble();
        timeStarted = true;
        this.duration = duration;
    }

    public boolean timestarted()
    {
        return timeStarted;
    }

    public boolean timeover()
    {
        return duration + startTime < TimeSinceInit.getAsDouble();
    }

    public double timeLeft()
    {
        return startTime + duration - TimeSinceInit.getAsDouble();
    }

}
