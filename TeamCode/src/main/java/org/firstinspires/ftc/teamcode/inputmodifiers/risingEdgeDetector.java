package org.firstinspires.ftc.teamcode.inputmodifiers;

import java.util.function.BooleanSupplier;

public class risingEdgeDetector
{
    BooleanSupplier bool;
    boolean State;
    boolean prevVal;

    public risingEdgeDetector(BooleanSupplier bool)
    {
        this.bool = bool;
    }

    public void update()
    {
        State = !prevVal && bool.getAsBoolean();
        prevVal = bool.getAsBoolean();
    }

    public boolean getState()
    {
        return State;
    }
}
