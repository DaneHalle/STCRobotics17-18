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
    }
}
