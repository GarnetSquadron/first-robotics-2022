package org.firstinspires.ftc.teamcode.GamepadClasses;

import android.widget.ToggleButton;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.BooleanToggler;

import java.util.function.BooleanSupplier;

public class ToggleGamepad extends BetterControllerClass{
    public BooleanToggler aToggle,bToggle,xToggle,yToggle,leftTriggerToggle,rightTriggerToggle,leftBumperToggle,rightBumperToggle;
    public ToggleGamepad(Gamepad gamepad) {
        super(gamepad);
    }


}
