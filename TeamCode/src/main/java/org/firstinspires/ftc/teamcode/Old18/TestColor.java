package org.firstinspires.ftc.teamcode.Old18;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.lang.Math;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@TeleOp(name="Template: Linear OpMode", group="Linear Opmode")  // @Autonomous(...) is the other common choice
@Disabled
public class TestColor extends LinearOpMode {

    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    HardwareMap_Mechanum robot = new HardwareMap_Mechanum();

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        robot.init(hardwareMap);
        robot.colorSensor.enableLed(true);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while(opModeIsActive()) {
            telemetry.addData("Red",robot.colorSensor.red());
            telemetry.addData("Blue",robot.colorSensor.blue());
            telemetry.addData("Green",robot.colorSensor.green());
            telemetry.update();

            idle();
        }
    }
}