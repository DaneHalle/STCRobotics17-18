package org.firstinspires.ftc.teamcode.Old18;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegistrar;

//import org.firstinspires.ftc.teamcode.Auto_Blue_Angle;
//import org.firstinspires.ftc.teamcode.Auto_Blue_Straight;
//import org.firstinspires.ftc.teamcode.Auto_Red_Angle;
//import org.firstinspires.ftc.teamcode.Red_Straight_Center;
import org.firstinspires.ftc.teamcode.Dual;

//import org.firstinspires.ftc.robotcontroller.external.samples.TestColor;
//import org.firstinspires.ftc.teamcode.Crap.K9botTeleopTank_Linear;
//import org.firstinspires.ftc.teamcode.Crap.SensorMRColor;

public class RegisterOpModes {
    @OpModeRegistrar
    public static void registerMyOpModes(OpModeManager manager) {
        //manager.register("Straight Red", Red_Straight_Center.class);
        //manager.register("Straight Blue", Auto_Blue_Straight.class);
        //manager.register("Angle Red", Auto_Red_Angle.class);
        //manager.register("Angle Blue", Auto_Blue_Angle.class);
        manager.register("Dual Driver", Dual.class);

        //manager.register("TestWheels",TestWheels.class);
        //manager.register("Test Color",TestColor.class);
        //manager.register("test",TestWheels.class);
        //manager.register("Wheels", New_Wheels.class);
        //manager.register("TestColor", TestColor.class);
        //manager.register("Driver Controlled", Driver_Mechanum_TankMode.class);
        //manager.register("Single Driver", Single.class);
        //manager.register("color sensor", SensorMRColor.class);
    }
}
