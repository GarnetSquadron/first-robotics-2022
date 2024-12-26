package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

public class BetterControllerClass {
    Gamepad gamepad;
    public BetterControllerClass(Gamepad gamepad){
        this.gamepad = gamepad;
    }
    public boolean A(){
        return gamepad.a;
    }
    public boolean B(){
        return gamepad.b;
    }
    public boolean X(){
        return gamepad.x;
    }
    public boolean Y(){
        return gamepad.y;
    }
    public boolean RightBumper(){
        return gamepad.right_bumper;
    }
    public boolean LeftBumper(){
        return gamepad.left_bumper;
    }

}
