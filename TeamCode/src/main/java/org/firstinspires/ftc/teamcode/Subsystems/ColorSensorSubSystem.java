package org.firstinspires.ftc.teamcode.Subsystems;

//import static org.firstinspires.ftc.teamcode.Tests.autointestred.State.EJECTING;
//import static org.firstinspires.ftc.teamcode.Tests.autointestred.State.HOLDING;
import static org.firstinspires.ftc.teamcode.Tests.autointestred.State.INTAKING;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Tests.autointestred;
import org.firstinspires.ftc.teamcode.enums.Color;

public class ColorSensorSubSystem extends SubsystemBase {
    ColorSensor cSensor;

    public ColorSensorSubSystem(HardwareMap hardwaremap, String name) {

    }

    public Color getSensedColor() {
        double HighestColorValue = Math.max(Math.max(cSensor.red(), cSensor.green()), cSensor.blue());
        autointestred.State result = INTAKING;

        if (HighestColorValue < 200) {
            return null;
        }
        else if (cSensor.red() > cSensor.green() && cSensor.red() > cSensor.blue()) {
            return Color.RED;

        }
        else if (cSensor.green() > cSensor.red() && cSensor.green() > cSensor.blue()) {
            return Color.BLUE;

        }
        else if (cSensor.blue() > cSensor.red() && cSensor.blue() > cSensor.green()) {
            return Color.YELLOW;

        }
        return null;
    }
}



