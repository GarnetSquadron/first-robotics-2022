package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Subsystems.ColorSensorSubSystem;
import org.firstinspires.ftc.teamcode.Subsystems.CrankSlideSubSystem;
import org.firstinspires.ftc.teamcode.Subsystems.IntakePivot;
import org.firstinspires.ftc.teamcode.Subsystems.TriangleIntake;
import org.firstinspires.ftc.teamcode.TTimer;
import org.firstinspires.ftc.teamcode.enums.Color;

public class AutomatedIntakeAndTransfer extends CommandBase {
    ColorSensorSubSystem cSensor;
    TriangleIntake triangleIntake;
    TriangleIntakeCommand triangleIntakeCommand;
    IntakePivot pivot;
    CrankSlideSubSystem crank;
    TTimer retractTime, transferTime;
    AutomatedIntakeAndTransfer(ColorSensorSubSystem Sensor, TriangleIntake tIntake, Color alianceColor, Telemetry telemetry, CrankSlideSubSystem c, IntakePivot p){
        cSensor = Sensor;
        triangleIntake = tIntake;
        triangleIntakeCommand = new TriangleIntakeCommand(triangleIntake,cSensor,alianceColor,telemetry);
        crank = c;
        pivot = p;
        retractTime = new TTimer(1,System::currentTimeMillis);
        transferTime = new TTimer(1,System::currentTimeMillis);

    }
    @Override
    public void execute(){

        if(triangleIntakeCommand.isFinished()){
            if(!retractTime.timestarted()){
                crank.undeploy();
                pivot.undeploy();
                retractTime.StartTimer();
            }
            if(retractTime.timeover()){
                if(!transferTime.timestarted())
                {
                    triangleIntake.eject();
                    transferTime.StartTimer();
                }
                if(transferTime.timeover()){
                    triangleIntake.hold();
                    
                }
            }
        }
        else{
            triangleIntakeCommand.execute();
        }

    }
}
