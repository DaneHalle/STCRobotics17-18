package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegistrar;

//import org.firstinspires.ftc.robotcontroller.external.samples.TestColor;
//import org.firstinspires.ftc.teamcode.Crap.K9botTeleopTank_Linear;
//import org.firstinspires.ftc.teamcode.Crap.SensorMRColor;

public class RegisterOpModes {
    @OpModeRegistrar
    public static void registerMyOpModes(OpModeManager manager) {
        manager.register("Straight Red", Auto_Red_Straight.class);
        manager.register("Straight Blue", Auto_Blue_Straight.class);
        manager.register("Angle Red", Auto_Red_Angle.class);
        manager.register("Angle Blue", Auto_Blue_Angle.class);
        manager.register("Dual Driver", Dual.class);
    }
}
