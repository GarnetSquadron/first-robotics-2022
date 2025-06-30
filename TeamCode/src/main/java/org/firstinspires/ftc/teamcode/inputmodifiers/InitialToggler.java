package org.firstinspires.ftc.teamcode.inputmodifiers;

import org.firstinspires.ftc.teamcode.inputmodifiers.BooleanChangeDetector;
import org.firstinspires.ftc.teamcode.inputmodifiers.BooleanToggler;

import java.util.function.BooleanSupplier;

public class InitialToggler {
    BooleanToggler toggler;
    BooleanChangeDetector changeDetector;
    public InitialToggler(BooleanSupplier bool){
        toggler = new BooleanToggler(bool);
        changeDetector = new BooleanChangeDetector(toggler::getState);
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
