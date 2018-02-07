package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegistrar;

import org.firstinspires.ftc.teamcode.Old18.TestColor;

//import org.firstinspires.ftc.robotcontroller.external.samples.TestColor;
//import org.firstinspires.ftc.teamcode.Crap.K9botTeleopTank_Linear;
//import org.firstinspires.ftc.teamcode.Crap.SensorMRColor;

public class RegisterOpModes {
    @OpModeRegistrar
    public static void registerMyOpModes(OpModeManager manager) {
        //manager.register("TestWheels",TestWheels.class);
        manager.register("Straight Red", Auto_Mechanum_Red_Easy.class);
        manager.register("Straight Blue", Auto_Mechanum_Blue_Easy.class);
        manager.register("Angle Red", Auto_Mechanum_Red_Hard.class);
        manager.register("Angle Blue", Auto_Mechanum_Blue_Hard.class);
        manager.register("Dual Driver", Dual.class);
        manager.register("Test Color",TestColor.class);

        //manager.register("test",TestWheels.class);
        //manager.register("Wheels", New_Wheels.class);
        //manager.register("TestColor", TestColor.class);
        //manager.register("Driver Controlled", Driver_Mechanum_TankMode.class);
        //manager.register("Single Driver", Single.class);
        //manager.register("color sensor", SensorMRColor.class);
    }
}
