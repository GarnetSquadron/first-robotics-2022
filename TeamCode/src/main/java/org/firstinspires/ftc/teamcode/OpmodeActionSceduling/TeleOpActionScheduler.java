package org.firstinspires.ftc.teamcode.OpmodeActionSceduling;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.ToggleButtonReader;

import java.util.ArrayList;

public class TeleOpActionScheduler {
    ArrayList <Action> actions = new ArrayList<>();
    ArrayList <Action> cancelOnAllOtherActions = new ArrayList<>();
    ArrayList <String> IDs = new ArrayList<> ();
    TelemetryPacket packet = new TelemetryPacket();

    public TeleOpActionScheduler(){
    }

    /**
     * adds an action to the que
     * @param action
     * @param ID identifier for identifying the action later
     */
    public void start(Action action,String ID){
        for(Action a:cancelOnAllOtherActions){
            if(actions.contains(a)){
                actions.remove(a);
            }
        }
        actions.add(action);
        IDs.add(ID);
    }

    /**
     * adds an action to the que. I quickly realized the identifier is possibly pointless so here it is without the id. you have to cancel by inputting an action instead of an id if you want to cancel it. So like  cancel(Action)
     * @param action
     */
    public void start(Action action){
        start(action,null);
    }

    /**
     * cancel with the id
     * @param ID
     */
    public void cancel(String ID){
        if(IDs.contains(ID)){
            int i = IDs.indexOf(ID);
            IDs.remove(ID);
            actions.remove(actions.get(i));
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
    public void update(){
        ArrayList <Action> actionsKept = new ArrayList<>();//temporary list for keeping track of the actions we want
        for(Action action:actions){
            if(action.run(packet)){
                actionsKept.add(action);
            }
        }
        actions = actionsKept;
    }
    public ArrayList<Action> getActions(){
        return actions;
    }

}
