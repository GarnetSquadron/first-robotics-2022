package org.firstinspires.ftc.teamcode.OpModes.autonomi;

public class ColorSensorConcept {

    public static String detectColor(int red, int green, int blue) {
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