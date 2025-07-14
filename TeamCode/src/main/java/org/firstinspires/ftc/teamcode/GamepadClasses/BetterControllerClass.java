package org.firstinspires.ftc.teamcode.GamepadClasses;

import com.qualcomm.robotcore.hardware.Gamepad;

public class BetterControllerClass
{
    Gamepad gamepad;

    public BetterControllerClass(Gamepad gamepad)
    {
        this.gamepad = gamepad;
    }

    public boolean A()
    {
        return gamepad.a;
    }

    public boolean B()
    {
        return gamepad.b;
    }

    public boolean X()
    {
        return gamepad.x;
    }

    public boolean Y()
    {
        return gamepad.y;
    }

    public boolean RightBumper()
    {
        return gamepad.right_bumper;
    }

    public boolean LeftBumper()
    {
        return gamepad.left_bumper;
    }

    public boolean LeftTrigger()
    {
        return gamepad.left_trigger > 0.1;
    }

    public boolean RightTrigger()
    {
        return gamepad.right_trigger > 0.1;
    }

    public boolean DpadLeft()
    {
        return gamepad.dpad_left;
    }

    public boolean DpadRight()
    {
        return gamepad.dpad_right;
    }

    public boolean DpadUp()
    {
        return gamepad.dpad_up;
    }

    public boolean DpadDown()
    {
        return gamepad.dpad_down;
    }

    public boolean leftStickDown()
    {
        return gamepad.left_stick_button;
    }

    public boolean rightStickDown()
    {
        return gamepad.right_stick_button;
    }
}
