package org.firstinspires.ftc.teamcode.commands;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;


import com.qualcomm.robotcore.hardware.ColorSensor;
import org.firstinspires.ftc.teamcode.Subsystems.ColorSensorSubSystem;
import org.firstinspires.ftc.teamcode.Subsystems.TriangleIntake;
import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.enums.AlianceColor;
import org.firstinspires.ftc.teamcode.enums.Color;


public class TriangleIntakeCommand extends CommandBase {
    TriangleIntake triangleIntake;
    Color alianceColor;
    ColorSensorSubSystem colorSensor;
    TriangleIntakeCommand(TriangleIntake t, ColorSensorSubSystem c, Color a){
        triangleIntake = t;
        alianceColor = a;
        colorSensor = c;
    }
//
    @Override
    public void execute() {
        triangleIntake.intake();



            Color c = colorSensor.getSensedColor();

            telemetry.addData("color", c);
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

            } else if (c == alianceColor){
                triangleIntake.hold();

            }else{
                telemetry.addLine("Else has been reached");
            }

        }
    }


