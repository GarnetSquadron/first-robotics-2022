package org.firstinspires.ftc.teamcode.OpModes.autonomi;

public class ColorSensorConcept
{

    public static String detectColor(int red, int green, int blue)
    {
        // Threshold values for color detection
        int threshold = 100;

        if (red > threshold && green < threshold && blue < threshold) {
            return "Red";
        } else if (red < threshold && green < threshold && blue > threshold) {
            return "Blue";
        } else {
            return "Yellow";
        }
    }
}
/**
 * rgb values
 * <p>
 * Blue R:1 G:2 B: 6 (Highest) Yellow: R:3 G:4 (Highest) B:1 Red: R: 5 (Highest) G:2 B:1
 */