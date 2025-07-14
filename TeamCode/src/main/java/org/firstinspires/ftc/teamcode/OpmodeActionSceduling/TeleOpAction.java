package org.firstinspires.ftc.teamcode.OpmodeActionSceduling;

import com.acmerobotics.roadrunner.Action;

public class TeleOpAction
{
    public Action action;
    public String ID;

    TeleOpAction(String ID, Action action)
    {
        this.ID = ID;
        this.action = action;
    }

}
