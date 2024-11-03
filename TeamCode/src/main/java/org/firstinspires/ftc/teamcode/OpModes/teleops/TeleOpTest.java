package org.firstinspires.ftc.teamcode.OpModes.teleops;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.button.Button;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "\uD83C\uDF56  \uD83C\uDF56 TeleOpTest \uD83C\uDF56  \uD83C\uDF56")
public class TeleOpTest extends LinearOpMode {
    Button b = new Button() {
        @Override
        public Button whileHeld(Command command) {
            return super.whileHeld(command);
        }
    };
    @Override
    public void runOpMode(){
        while (opModeIsActive()){
            
        }

    }
}
