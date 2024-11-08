package org.firstinspires.ftc.teamcode;

import android.telecom.Call;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public abstract class TeleOpLoop extends OpMode {
    ArrayList<Runnable> methods = new ArrayList<>();
    void add(Runnable f){
        methods.add(f);
    }
    void onButton(Button button, Runnable f){
        if(button.get()){
            f.run();
        }
    }
    @Override
    public void init(){

    }
    @Override
    public void loop(){
        for(Runnable method:methods){
            method.run();
        }
    }

}
