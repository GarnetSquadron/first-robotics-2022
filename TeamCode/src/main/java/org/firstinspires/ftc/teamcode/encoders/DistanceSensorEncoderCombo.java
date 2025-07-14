package org.firstinspires.ftc.teamcode.encoders;

import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class DistanceSensorEncoderCombo extends Encoder
{
    //DistanceSensor distanceSensor;
    Encoder distanceSensorEncoder;

    public DistanceSensorEncoderCombo(Encoder encoder, DistanceSensor distanceSensor)
    {
        super(encoder);
        distanceSensorEncoder = new Encoder(() -> distanceSensor.getDistance(DistanceUnit.INCH));
    }

    /**
     * Using the distance sensor takes around 0.03 seconds, so keep that in mind when calling this
     * function
     */
    public void updateDistance()
    {
        super.setPosition(distanceSensorEncoder.getPos());
    }

    @Override
    public void setPosition(double position)
    {
        super.setPosition(position);
        distanceSensorEncoder.setPosition(position);
    }
}
