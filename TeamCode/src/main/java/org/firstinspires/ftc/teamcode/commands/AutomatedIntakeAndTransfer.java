package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Subsystems.depricated.TriangleIntake.ColorSensorSubSystem;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.CrankSlideSubSystem;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakePivot;
import org.firstinspires.ftc.teamcode.Subsystems.depricated.TriangleIntake.TriangleIntake;
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
        retractTime = new TTimer(System::currentTimeMillis);
        transferTime = new TTimer(System::currentTimeMillis);
    }
    @Override
    public void execute(){

        if(triangleIntakeCommand.isFinished()){
            if(!retractTime.timestarted()){
                crank.undeploy();
                pivot.undeploy();
                retractTime.StartTimer(100);
            }
            if(retractTime.timeover()){
                if(!transferTime.timestarted())
                {
                    triangleIntake.eject();
                    transferTime.StartTimer(100);
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
