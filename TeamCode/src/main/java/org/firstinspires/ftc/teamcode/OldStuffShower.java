package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegistrar;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta;

import java.util.Objects;

/**
 * set ShowOldStuff to true to show the old stuff on the opmode list,
 * I made this to keep the opmode list uncluttered with unnecessary stuff
 * it doesnt really work like i thought, ill get to this later
 */
@Disabled
public final class OldStuffShower {
    public static final boolean ShowOldStuff = true;
    public static final String GROUP = "OldStuff";
    private static OpModeMeta metaForAutoClass(Class<? extends LinearOpMode> cls) {
        return new OpModeMeta.Builder()
                .setName(cls.getSimpleName())
                .setGroup(GROUP)
                .setFlavor(OpModeMeta.Flavor.TELEOP)
                .build();
    }
    @OpModeRegistrar
    public static void register(OpModeManager manager) {
        if (!ShowOldStuff) return;


    }
}
