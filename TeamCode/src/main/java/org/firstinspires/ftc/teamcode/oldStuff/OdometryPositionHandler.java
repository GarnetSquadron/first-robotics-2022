package org.firstinspires.ftc.teamcode.oldStuff;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class OdometryPositionHandler {
    private DcMotor XDeadWheelEnc;
    private DcMotor YDeadWheelEnc;
    private IMU imu;
    final double DeadWheelDiameter;
    final double DeadWheelCircumference;
    final double DeadWheelRotation;
    final double DeadWheelInchPerTick;
    final double DeadWheelXSeparation;
    final double DeadWheelYSeparation;
    /**
     *
     */
    final double OdometryCenterXDifference;
    final double OdometryCenterYDifference;
    public OdometryPositionHandler(HardwareMap hMap) {
        hardwareMap = hMap;
        DeadWheelDiameter = 1.89;
        DeadWheelCircumference = DeadWheelDiameter *Math.PI;
        DeadWheelRotation = 2000;
        DeadWheelInchPerTick = (DeadWheelCircumference / DeadWheelRotation);
        DeadWheelXSeparation = 1;
        DeadWheelYSeparation = 1;
        OdometryCenterXDifference = -7.5;
        OdometryCenterYDifference = -1.5;
        realOffCenterArcsX = -OdometryCenterXDifference;
        realOffCenterArcsY = -OdometryCenterYDifference;
        realOffCenterLinesX = -OdometryCenterXDifference;
        realOffCenterLinesY = -OdometryCenterYDifference;
    }
    private HardwareMap hardwareMap;
    private double realOffCenterArcsX;
    private double realOffCenterArcsY;
    private double realOffCenterLinesX;
    private double realOffCenterLinesY;
    private double DeadWheelX = 0;
    private double DeadWheelY = 0;
    private double Theta = getTheta();
    public double DeltaRealX;
    public double DeltaRealY;
    public double getDeadWheelX(){
        return XDeadWheelEnc.getCurrentPosition()* DeadWheelInchPerTick;
    }
    public double getDeadWheelY(){
        return YDeadWheelEnc.getCurrentPosition()* DeadWheelInchPerTick;
    }
    public double getDeadWheelDeltaX(){
        return getDeadWheelX()- DeadWheelX;
    }
    public double getDeadWheelDeltaY(){
        return getDeadWheelY()- DeadWheelY;
    }
    public double getDeltaTheta(){
        return getTheta()- Theta;
    }
    public double getTheta(){
        if (imu != null) {
            return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);//Im a mathemetician. What can i say.
        }
        else
            return 0;
    }
    public double getAngularVel(){
        return imu.getRobotAngularVelocity(AngleUnit.RADIANS).zRotationRate;
    }


    /**
     * assumes the path is made of line segments :)
     * apparently that really doesnt work lol
     */
    public void updatePosAssumeLines(){
        double Dx = getDeadWheelDeltaX();
        double Dy = getDeadWheelDeltaY();
        double theta = getTheta();
        realOffCenterLinesY +=Dx*Math.sin(theta)+Dy*Math.cos(theta);
        realOffCenterLinesX +=Dx*Math.cos(theta)-Dy*Math.sin(theta);
        DeadWheelX = getDeadWheelX();
        DeadWheelY = getDeadWheelY();
        Theta = getTheta();

    }
    public void updatePosAssumeArcs(){
        double DeltaDeadWheelx = getDeadWheelDeltaX();
        double DeltaDeadWheely = getDeadWheelDeltaY();
        double Dtheta = getDeltaTheta();//the amount theta has changed by
        double oldAngle = getTheta()-Dtheta;//the angle it was at before the change
        double f=Dtheta-oldAngle;
        double cosOldAngle = Math.cos(oldAngle);
        double sinOldAngle = Math.sin(oldAngle);
        double sinTheta = Math.sin(Dtheta);
        double cosTheta = Math.cos(Dtheta);
        double ly=DeltaDeadWheely*sinOldAngle+DeltaDeadWheelx*cosOldAngle;
        double lx=DeltaDeadWheely*cosOldAngle-DeltaDeadWheelx*sinOldAngle;
        DeltaRealX = (-(ly) * sinTheta - (lx) * cosTheta + lx) / Dtheta;
        DeltaRealY = ((lx) * sinTheta - (ly) * cosTheta + ly) / Dtheta;
        if(Dtheta != 0) {
            realOffCenterArcsX += DeltaRealX;
            realOffCenterArcsY += DeltaRealY;
        }
        else{
            realOffCenterArcsX += DeltaDeadWheelx*Math.sin(oldAngle)+DeltaDeadWheely*Math.cos(oldAngle);
            realOffCenterArcsY += DeltaDeadWheelx*Math.cos(oldAngle)-DeltaDeadWheely*Math.sin(oldAngle);
        }
        DeadWheelX = getDeadWheelX();
        DeadWheelY = getDeadWheelY();
        Theta = getTheta();
    }
    public double getArcsX(){
        return realOffCenterArcsX +OdometryCenterYDifference*Math.sin(getTheta())+OdometryCenterXDifference*Math.cos(getTheta());
    }
    public double getArcsY(){
        return realOffCenterArcsY +OdometryCenterYDifference*Math.cos(getTheta())-OdometryCenterXDifference*Math.sin(getTheta());
    }
    public double getLinesX(){
        return realOffCenterLinesX +OdometryCenterYDifference*Math.sin(getTheta())+OdometryCenterXDifference*Math.cos(getTheta());
    }
    public double getLinesY(){
        return realOffCenterLinesY +OdometryCenterYDifference*Math.cos(getTheta())-OdometryCenterXDifference*Math.sin(getTheta());
    }
//    Thread handlePositionUpdater = new Thread(()->{
//        while(opModeIsActive()) {
//
//        }
//    });
    public void initHardware(){
        XDeadWheelEnc = hardwareMap.get(DcMotor.class, "lift");
        YDeadWheelEnc = hardwareMap.get(DcMotor.class, "y encoder");
        imu = hardwareMap.get(IMU.class, "imu");
    }


}
