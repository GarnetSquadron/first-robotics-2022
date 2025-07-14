package org.firstinspires.ftc.teamcode.GamepadClasses;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.inputmodifiers.BooleanToggler;

public class ToggleGamepad extends BetterControllerClass
{
    public BooleanToggler aToggle, bToggle, xToggle, yToggle, leftTriggerToggle, rightTriggerToggle, leftBumperToggle, rightBumperToggle;

    public ToggleGamepad(Gamepad gamepad)
    {
        super(gamepad);
    }


}
