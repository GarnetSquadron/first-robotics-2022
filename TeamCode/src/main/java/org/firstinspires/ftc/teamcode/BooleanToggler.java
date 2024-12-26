package org.firstinspires.ftc.teamcode;

import java.util.function.BooleanSupplier;

public class BooleanToggler {
    BooleanSupplier bool;
    boolean prevVal;
    boolean toggleState = false;
    public BooleanToggler(BooleanSupplier b){
        bool = b;
    }
    public void updateValue(){
        if(bool.getAsBoolean()!=prevVal){
            toggleState = !toggleState;
        }
    }
    public boolean getState(){
        return toggleState;
    }
}
