package org.firstinspires.ftc.teamcode.OpmodeActionSceduling;

import java.util.ArrayList;
import java.util.Arrays;

public class TeleOpCancelGroup
{
    String name;
    ArrayList<String> TeleOpActions;

    TeleOpCancelGroup(String name, String... actions)
    {
        this.name = name;
        for (String action : actions) {
            addActions(action);
        }
    }

    TeleOpCancelGroup(String name, TeleOpCancelGroup... groups)
    {
        this.name = name;
        for (TeleOpCancelGroup group : groups) {
            addActions(group);
        }
    }

    public void addActions(String... actions)
    {
        TeleOpActions.addAll(Arrays.asList(actions));
    }

    public void addActions(TeleOpCancelGroup... groups)
    {
        for (TeleOpCancelGroup group : groups) {
            for (String ID : group.getActionIDs()) {
                addActions(ID);
            }
        }
    }

    public ArrayList<String> getActionIDs()
    {
        return TeleOpActions;
    }
}
