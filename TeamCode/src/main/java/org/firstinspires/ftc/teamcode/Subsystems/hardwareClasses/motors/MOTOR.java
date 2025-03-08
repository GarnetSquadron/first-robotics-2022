package org.firstinspires.ftc.teamcode.Subsystems.hardwareClasses.motors;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.Encoder;
import org.firstinspires.ftc.teamcode.Subsystems.controllers.Controller;
import org.firstinspires.ftc.teamcode.Subsystems.controllers.NullController;
import org.firstinspires.ftc.teamcode.Subsystems.controllers.PIDCon;

import java.util.function.DoubleSupplier;

public class MOTOR extends RAWMOTOR {
    Controller extTorqueController = new NullController();
    Controller controller;
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
    public void setController(Controller controller){
        this.controller = controller;
    }
    public void setPID(double kp, double ki, double kd){
        setController(new PIDCon(kp,ki,kd, encoder));
    }

    /**
     * hello
     * @param targetPosition
     */
    public void setTargetPosition(double targetPosition){
        controller.setTargetPosition(targetPosition);
    }
    public void runToTargetPosition(){
        setNetTorque(controller.calculate());
    }
    public void setNetTorque(double power){
        motor.set(power-extTorqueController.calculate());
    }
    public void runToPos(double tgtPos) {
        if(controller.getTargetPosition()!=tgtPos){
            setTargetPosition(tgtPos);
        }
        runToTargetPosition();
    }
    public boolean targetReached(){
        return targetReached();
    }

}
