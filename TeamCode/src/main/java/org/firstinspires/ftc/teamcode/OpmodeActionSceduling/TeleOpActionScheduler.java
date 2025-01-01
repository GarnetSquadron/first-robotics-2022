package org.firstinspires.ftc.teamcode.OpmodeActionSceduling;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Actions;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import org.firstinspires.ftc.teamcode.InitialToggler;
import org.firstinspires.ftc.teamcode.MiscActions.CancelableAction;
import org.firstinspires.ftc.teamcode.risingEdgeDetector;

import com.acmerobotics.roadrunner.NullAction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * this is for running actions in an opmode loop
 */
public class TeleOpActionScheduler {
    ArrayList <TeleOpAction> CurrentTeleOpActions = new ArrayList<>();
    ArrayList <String> cancelOnAllOtherActions = new ArrayList<>();
    TelemetryPacket packet = new TelemetryPacket();
    public TeleOpAction getTeleOpActionFromID(String ID){
        for(TeleOpAction teleOpAction:CurrentTeleOpActions){
            if(teleOpAction.ID == ID){
                return teleOpAction;
            }
        }
        return null;
    }
    public boolean TeleOpActionRunning(String ID){
        return getTeleOpActionFromID(ID) != null;
    }

    /**
     * adds an action to the que
     * @param action
     */
    public void start(TeleOpAction action){
        for(String ID:cancelOnAllOtherActions){
            if(TeleOpActionRunning(ID)){
                cancel(ID);
            }
        }
        if(!CurrentTeleOpActions.contains(action)) {
            CurrentTeleOpActions.add(action);
        }

    }
    public void start(Action action, String ID){
        start(new TeleOpAction(ID,action));
    }
    /**
     * cancel with the id
     * @param ID
     */
    public void cancel(String ID){
        TeleOpAction teleAction = getTeleOpActionFromID(ID);
        if (TeleOpActionRunning(ID)){

            CurrentTeleOpActions.set(CurrentTeleOpActions.indexOf(teleAction),new TeleOpAction(ID+" canceling",getFailOvers(teleAction.action)));
        }
    }

    /**
     * cancel the action
     * @param listOfActions
     */

    /**
     * cancel everything
     */
    public void cancelAll(){
        for(TeleOpAction action: CurrentTeleOpActions){
            cancel(action.ID);
        }
    }
    public void CancelOnAnyOtherAction(String... IDs){
        cancelOnAllOtherActions.addAll(Arrays.asList(IDs));
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

    }
    public void actionTogglePair(InitialToggler toggler, Action action1, String ID1, Action action2, String ID2){
        actionBooleanPair(toggler.JustChanged(),toggler.getState(),action1,ID1,action2,ID2);
    }

    /**
     * not sure what to call this. i will probably change the name later. How it works: Depending on the value of a boolean, it will start one of the two actions supplied. I made this method because most of the mechanisms use this. Most of the time, the boolean represents the state of a mechanism, ie is the claw open or closed, is the viper up or down, etc.
     */
    public void actionBooleanPair(boolean REButtonDetector, boolean condition, Action action1,String ID1, Action action2, String ID2) {
        if (REButtonDetector) {
            if (condition) {
                cancel(ID2);
                start(action1, ID1);
            } else {
                cancel(ID1);
                start(action2, ID2);
            }
        }
    }
    public void actionTogglePair(){

    }
    public void StopEverythingAndStart(Action action,String ID){
        cancelAll();
        start(action,ID);
    }
    public Action getFailOvers(Action action){
        if(action.getClass()== CancelableAction.class){
            return ((CancelableAction) action).getFailoverAction();
        }
        if(action.getClass() == SequentialAction.class){
            List<Action> initialActions = ((SequentialAction) action).getInitialActions();
            List<Action> canceledActions = new ArrayList<>();
            for(Action a:initialActions)
                canceledActions.add(getFailOvers(a));
            return new SequentialAction(canceledActions);
        }
        if(action.getClass() == ParallelAction.class){
            List<Action> initialActions = ((ParallelAction) action).getInitialActions();
            List<Action> canceledActions = new ArrayList<>();
            for(Action a:initialActions)
                canceledActions.add(getFailOvers(a));
            return new ParallelAction(canceledActions);
        }
        return new NullAction();
    }
    public void update(){
        ArrayList<TeleOpAction> newActions = new ArrayList<>();
        for(TeleOpAction teleOpAction: CurrentTeleOpActions){
            if(teleOpAction.action.run(packet)){
                newActions.add(teleOpAction);
            }

        }
        CurrentTeleOpActions.clear();
        CurrentTeleOpActions = newActions;
    }
    public ArrayList<TeleOpAction> getCurrentTeleOpActions(){
        return CurrentTeleOpActions;
    }


    public ArrayList<String> getActionIDs(){
        ArrayList<String> IDs = new ArrayList<>();
        for(TeleOpAction teleOpAction: CurrentTeleOpActions){
            IDs.add(teleOpAction.ID);
        }
        return IDs;
    }
    public ArrayList<Action> getCurrentSubActionsFromAGivenAction(String ID) {
        ArrayList<String> IDs = new ArrayList<>();
        TeleOpAction action = getTeleOpActionFromID(ID);
        if(action!=null){
            if(action.action.getClass()==SequentialAction.class){
            }
        }
        return null;
    }
}
