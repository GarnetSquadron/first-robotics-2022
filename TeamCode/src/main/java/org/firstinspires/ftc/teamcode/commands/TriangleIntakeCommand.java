package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff.Intake.CrankSlideSubSystem;
import org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff.TriangleIntake.ColorSensorSubSystem;
import org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff.TriangleIntake.TriangleIntake;
import org.firstinspires.ftc.teamcode.enums.Color;


public class TriangleIntakeCommand extends CommandBase
{
    TriangleIntake triangleIntake;
    CrankSlideSubSystem crankSlideSubSystem;
    public Color alianceColor;
    ColorSensorSubSystem colorSensor;
    Telemetry TELEMETRY;
    public Color c;
    long duration = 1500;
    long startTime = duration - System.currentTimeMillis();

    public TriangleIntakeCommand(TriangleIntake t, ColorSensorSubSystem c, Color a, Telemetry tel)
    {

        triangleIntake = t;
        alianceColor = a;
        colorSensor = c;
        TELEMETRY = tel;
    }

    boolean finished = false;

    public void stopEjecting()
    {
        startTime = duration - System.currentTimeMillis();
    }

    public void ejectForDuration()
    {
        startTime = System.currentTimeMillis();
    }

    @Override
    public void execute()
    {

        //triangleIntake.intake();

        c = colorSensor.getSensedColor();
        if (System.currentTimeMillis() - startTime < duration) {

            triangleIntake.eject();

        }
        //TELEMETRY.addData("color", c);
        if (c == null) {
            triangleIntake.intake();
        } else if (c == Color.YELLOW) {
            stopEjecting();
            triangleIntake.hold();
        } else if (c != alianceColor) {

            ejectForDuration();

        }
        if (c == alianceColor) {
            stopEjecting();
            triangleIntake.hold();

        }


    }
//    public void stop(){
//        triangleIntake.hold();
//    }
//    public void

    @Override
    public boolean isFinished()
    {
        return finished;
    }
}


