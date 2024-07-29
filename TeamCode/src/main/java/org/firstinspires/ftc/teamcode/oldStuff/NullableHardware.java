package org.firstinspires.ftc.teamcode.oldStuff;


import android.renderscript.Element;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.lang.reflect.Method;


public class NullableHardware {

    public HardwareDevice device;

    private boolean connected = false;

    public boolean isConnected(){
        return connected;
    }
    /**
     *
     *this tries to find a part, but doesnt stop the entire program if it cannot find a part.
     *that way, we can take off parts and not worry about it.
     *It might not be possible to get this to work fyi.
     *
     * @param name
     * @param hardwareMap
     */
    public void InitializeHardwareCatchError( String name, HardwareMap hardwareMap) {

        try {
            device =  hardwareMap.get(device.getClass(), name);
            connected = true;
        } catch (Exception e) {
            //System.out.print("could not find device with name name" + name + ".");
            connected = false;

        }
    }

    /**
     * comming soon
     * @param methodName
     */
    public void Run(String methodName, Object... params){
        if(connected){
            try{
                Method method = device.getClass().getMethod(methodName,params.getClass());
                method.invoke(params);
            }catch (Throwable e){

            }
        }
    }
    /**
     * jk
     * @param d
     */
    public  <T extends HardwareDevice> NullableHardware(HardwareDevice d){
        device = d;

    }
}
