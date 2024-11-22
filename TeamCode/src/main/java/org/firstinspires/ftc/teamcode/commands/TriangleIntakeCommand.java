package org.firstinspires.ftc.teamcode.commands;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Subsystems.ColorSensorSubSystem;
import org.firstinspires.ftc.teamcode.Subsystems.TriangleIntake;
import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.enums.Color;


public class TriangleIntakeCommand extends CommandBase {
    TriangleIntake triangleIntake;
    public Color alianceColor;
    ColorSensorSubSystem colorSensor;
    Telemetry TELEMETRY;
    public Color c;
    public TriangleIntakeCommand(TriangleIntake t, ColorSensorSubSystem c, Color a, Telemetry tel){
        triangleIntake = t;
        alianceColor = a;
        colorSensor = c;
        TELEMETRY = tel;
    }
    boolean finished = false;

    @Override
    public void execute() {
        triangleIntake.intake();

        c = colorSensor.getSensedColor();

        //TELEMETRY.addData("color", c);
        if (c == null) {
            triangleIntake.intake();
        }
        else if (c != alianceColor) {
            long duration = 1500;
            long startTime = System.currentTimeMillis();

            while (System.currentTimeMillis() - startTime < duration) {

                triangleIntake.eject();

            }

        } else if (c == Color.YELLOW){
            triangleIntake.hold();
            finished = true;
        } else if (c == alianceColor){
            triangleIntake.hold();
            finished = true;
        }else{
            //telemetry.addLine("Else has been reached");
        }


    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}


