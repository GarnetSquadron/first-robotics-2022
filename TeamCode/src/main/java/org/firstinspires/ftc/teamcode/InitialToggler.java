package org.firstinspires.ftc.teamcode;

import java.util.function.BooleanSupplier;

public class InitialToggler {
    BooleanToggler toggler;
    BooleanChangeDetector changeDetector;
    public InitialToggler(BooleanSupplier bool){
        toggler = new BooleanToggler(bool);
        changeDetector = new BooleanChangeDetector(bool);
    }
    public void updateValue(){
        toggler.updateValue();
        changeDetector.update();
    }
    public boolean getState(){
        return toggler.getState();
    }
    public boolean JustChanged(){
        return changeDetector.getState();
    }
}
