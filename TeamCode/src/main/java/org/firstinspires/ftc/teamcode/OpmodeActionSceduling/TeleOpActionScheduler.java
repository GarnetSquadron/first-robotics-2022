package org.firstinspires.ftc.teamcode.OpmodeActionSceduling;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;

import org.firstinspires.ftc.teamcode.InitialToggler;
import org.firstinspires.ftc.teamcode.MiscActions.CancelableAction;

import java.util.ArrayList;
import java.util.List;

public class TeleOpActionScheduler {
    ArrayList <Action> actions = new ArrayList<>();
    ArrayList <Action> cancelOnAllOtherActions = new ArrayList<>();
    TelemetryPacket packet = new TelemetryPacket();
    ArrayList <Action> registeredActions = new ArrayList<>();
    ArrayList <String> registeredIDs = new ArrayList<>();
    public void AssignID(Action action, String ID){
        if(!(registeredActions.indexOf(action)==registeredIDs.indexOf(ID)&&registeredActions.contains(action))){
            registeredActions.add(action);
            registeredIDs.add(ID);
        }
    }
    public Action getActionFromID(String ID){
        return registeredActions.get(registeredIDs.indexOf(ID));
    }
    public String getIDFromAction(Action action){
        return registeredIDs.get(registeredActions.indexOf(action));
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
    public void start(Action action, String ID){
        start(action);
        AssignID(action,ID);
    }
    /**
     * cancel with the id
     * @param ID
     */
    public void cancel(String ID){
        if(actions.contains(getActionFromID(ID))){
            actions.remove(getActionFromID(ID));
        }
    }

    /**
     * cancel the action
     * @param listOfActions
     */
    public void cancel(Action... listOfActions){
        for(Action action:listOfActions) {
            if(actions.contains(action)) {
                actions.remove(action);
            }
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
     * @param toggler
     * @param action1
     * @param action2
     */
    public void actionTogglePair(InitialToggler toggler, Action action1, Action action2){
        if(toggler.JustChanged()){
            if (toggler.getState()) {
                start(action1);
                cancel(action2);
            } else {
                start(action2);
                cancel(action1);
            }
        }
    }
    public void actionTogglePair(InitialToggler toggler, Action action1, String ID1, Action action2, String ID2){
        AssignID(action1,ID1);
        AssignID(action2,ID2);
        actionTogglePair(toggler,action1,action2);
    }
    public void StopEverythingAndStart(Action action){
        cancelAll();
        start(action);
    }
    public void runFailOvers(Action action){
        if(action.getClass()== CancelableAction.class){
            ((CancelableAction) action).failover();
        }
        if(action.getClass() == SequentialAction.class){
            List<Action> initialActions = ((SequentialAction) action).getInitialActions();
            for(Action a:initialActions)
                runFailOvers(a);
        }
        if(action.getClass() == ParallelAction.class){
            List<Action> initialActions = ((ParallelAction) action).getInitialActions();
            for(Action a:initialActions)
                runFailOvers(a);
        }
    }
    public void update(){
        for(Action action:actions){
            if(!action.run(packet)){
                actions.remove(action);

            }
        }
    }
    public ArrayList<Action> getActions(){
        return actions;
    }


    public ArrayList<String> getActionIDs(){
        ArrayList IDs = new ArrayList<>();
        for(Action action:actions){
            if(registeredActions.contains(action))
                IDs.add(getIDFromAction(action));
        }
        return IDs;
    }

}
