package org.firstinspires.ftc.teamcode;

import android.telecom.Call;

import com.arcrobotics.ftclib.command.button.Button;

import java.util.concurrent.Callable;

public class TeleOpLoop {
    Runnable[] methods;
    void runLoop(){
        for (Runnable method : methods) {
            method.run();
        }
    }
    void add(Runnable f){

    }
//    void OnPress(Button, Runnable f){
//
//    }

}
