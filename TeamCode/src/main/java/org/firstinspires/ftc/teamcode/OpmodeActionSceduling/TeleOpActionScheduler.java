package org.firstinspires.ftc.teamcode.OpmodeActionSceduling;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.ToggleButtonReader;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class TeleOpActionScheduler {
    ArrayList <Action> actions = new ArrayList<>();
    ArrayList <Action> cancelOnAllOtherActions = new ArrayList<>();
    TelemetryPacket packet = new TelemetryPacket();
    Map<String,Action> IDMap = new HashMap<>();

    public TeleOpActionScheduler(){
    }
    public void SetID(Action action,String string){
        IDMap.put(string,action);
    }

    /**
     * adds an action to the que
     * @param action
     */
    public void start(Action action){
        for(Action a:cancelOnAllOtherActions){
            if(actions.contains(a)){
                cancel(a);
            }
        }
        if(!actions.contains(action)) {
            actions.add(action);
        }

    }

    /**
     * cancel with the id
     * @param ID
     */
    public void cancel(String ID){
        Action action = IDMap.get(ID);
        if(actions.contains(action)){
            actions.remove(action);
        }
    }

    /**
     * cancel the action
     * @param listOfActions
     */
    public void cancel(Action... listOfActions){
        for(Action action:listOfActions) {
            actions.remove(action);
        }
    }

    /**
     * cancel everything
     */
    public void cancelAll(){
        actions.clear();
    }
    public void CancelOnAnyOtherAction(Action... action){
        for(Action a:action) {
            cancelOnAllOtherActions.add(a);
        }
    }
//    public void onConditionStart(,Action action){
//        if(){
//
//        }
//    }

    /**
     * does action one if toggled, and action 2 otherwise. doesn't automatically update the toggle though
     * @param toggle
     * @param action1
     * @param action2
     */
    public void actionTogglePair(ToggleButtonReader toggle,Action action1,Action action2){
        if(toggle.getState()){
            start(action1);
            cancel(action2);
        }
        else {
            start(action2);
            cancel(action1);
        }
    }
    public void StopEverythingAndStart(Action action){
        cancelAll();
        start(action);
    }
    public void update(){
        ArrayList <Action> actionsKept = new ArrayList<>();//temporary list for keeping track of the actions we want
        for(Action action:actions){
            if(action.run(packet)){
                actionsKept.add(action);
            }
        }
        actions.clear();
        actions = actionsKept;
    }
    public ArrayList<Action> getActions(){
        return actions;
    }

    /**
     * doesnt work rn sorry
     * @return
     */
    public ArrayList<String> getActionIDs(){
        ArrayList IDs = new ArrayList<>();
        for(Action action:actions){

        }
        return IDs;
    }

}
