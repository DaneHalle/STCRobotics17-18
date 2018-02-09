package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegistrar;

//import org.firstinspires.ftc.robotcontroller.external.samples.TestColor;
//import org.firstinspires.ftc.teamcode.Crap.K9botTeleopTank_Linear;
//import org.firstinspires.ftc.teamcode.Crap.SensorMRColor;

public class RegisterOpModes {
    @OpModeRegistrar
    public static void registerMyOpModes(OpModeManager manager) {
        manager.register("Dual Driver", Dual.class);

        manager.register("Blue Straight", Key_Blue_Straight.class);
        manager.register("Blue Angle", Key_Blue_Angle.class);
        manager.register("Red Straight", Key_Red_Straight.class);
        manager.register("Red Angle", Key_Red_Angle.class);
    }
}
