package org.firstinspires.ftc.teamcode.Subsystems.hardwareClasses.motors;

import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.sun.tools.javac.code.Attribute;

import org.firstinspires.ftc.teamcode.Subsystems.Encoder;
import org.firstinspires.ftc.teamcode.Subsystems.controllers.Controller;
import org.firstinspires.ftc.teamcode.Subsystems.controllers.NullController;
import org.firstinspires.ftc.teamcode.Subsystems.controllers.PIDCon;
import org.firstinspires.ftc.teamcode.Subsystems.controllers.PositionController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.function.DoubleSupplier;

public class MOTOR extends RAWMOTOR {
    Controller extTorqueController = new NullController();
    PositionController positionController;
    public MOTOR(HardwareMap hardwareMap, String name){
        super(hardwareMap, name);
        setEncoderToMotorEncoder();
        setPID(1,0,0);
    }
    @Override
    public void setEncoder(Encoder encoder){
        this.encoder = encoder;
        positionController.setEncoder(encoder);
        extTorqueController.setEncoder(encoder);
    }
    public void setExtTorqueController(Controller controller){
        controller.setEncoder(encoder);
        extTorqueController = controller;
    }
    public void setPositionController(PositionController positionController){
        positionController.setEncoder(encoder);
        this.positionController = positionController;
    }
    public void setPID(double kp, double ki, double kd){
        setPositionController(new PIDCon(kp,ki,kd));
    }
    public void setTolerance(double tolerance){
        positionController.setTolerance(tolerance);
    }

    /**
     * hello
     * @param targetPosition
     */
    public void setTargetPosition(double targetPosition){
        positionController.setTargetPosition(targetPosition);
    }
    public double getTargetPosition(){
        return positionController.getTargetPosition();
    }
    public void runToTargetPosition(){
        setNetTorque(positionController.calculate());
    }
    public void setNetTorque(double power){
        setPower(power-extTorqueController.calculate());
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
