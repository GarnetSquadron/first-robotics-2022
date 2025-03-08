package org.firstinspires.ftc.teamcode.Subsystems.hardwareClasses.motors;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.Encoder;
import org.firstinspires.ftc.teamcode.Subsystems.controllers.Controller;
import org.firstinspires.ftc.teamcode.Subsystems.controllers.NullController;
import org.firstinspires.ftc.teamcode.Subsystems.controllers.PIDCon;
import org.firstinspires.ftc.teamcode.Subsystems.controllers.PositionController;

import java.util.function.DoubleSupplier;

public class MOTOR extends RAWMOTOR {
    Controller extTorqueController = new NullController();
    PositionController positionController;
    Encoder encoder;
    DoubleSupplier velocitySupplier;
    public MOTOR(HardwareMap hardwareMap, String name){
        super(hardwareMap, name);
        encoder = new Encoder(motor::getCurrentPosition,motor::getVelocity);
    }
    public void setEncoder(Encoder encoder){
        this.encoder = encoder;
    }
    public void setVelocitySupplier(DoubleSupplier supplier){
        velocitySupplier = supplier;
    }
    public void setExtTorqueController(Controller controller){
        extTorqueController = controller;
    }
    public void setPositionController(PositionController positionController){
        this.positionController = positionController;
    }
    public void setPID(double kp, double ki, double kd){
        setPositionController(new PIDCon(kp,ki,kd, encoder));
    }

    /**
     * hello
     * @param targetPosition
     */
    public void setTargetPosition(double targetPosition){
        positionController.setTargetPosition(targetPosition);
    }
    public void runToTargetPosition(){
        setNetTorque(positionController.calculate());
    }
    public void setNetTorque(double power){
        motor.set(power-extTorqueController.calculate());
    }
    public void runToPos(double tgtPos) {
        if(positionController.getTargetPosition()!=tgtPos){
            setTargetPosition(tgtPos);
        }
        runToTargetPosition();
    }
    public boolean targetReached(){
        return positionController.targetReached();
    }

}
