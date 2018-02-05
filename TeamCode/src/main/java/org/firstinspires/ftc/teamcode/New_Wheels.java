package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.lang.Math;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@TeleOp(name="Template: Linear OpMode", group="Linear Opmode")  // @Autonomous(...) is the other common choice
@Disabled
public class New_Wheels extends LinearOpMode {
    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    HardwareMap_Mechanum robot = new HardwareMap_Mechanum();

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        robot.init(hardwareMap);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        double right;
        double left;
        double mult = 1.0;

        //sets initial position for servos
        robot.theClaw.setPosition(.25);
        robot.rightExtend.setPosition(1);
        robot.leftExtend.setPosition(1);
        robot.ben.setPower(0);
        robot.flicker.setPosition(0);

        // run until the end of the match (driver presses STOP)
        while(opModeIsActive()) {
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();

            //Wheels
            if(gamepad1.right_stick_y>0||gamepad1.right_stick_y<0||gamepad1.left_stick_y>0||gamepad1.left_stick_y<0){
                right = -(gamepad1.right_stick_y) * mult;
                left = -(gamepad1.left_stick_y) * mult;
                robot.frontRight.setPower(right*2);
                robot.backRight.setPower(right);
                robot.frontLeft.setPower(left*2);
                robot.backLeft.setPower(left);

            //strafe
            }else if(gamepad1.left_bumper){
                robot.backLeft.setPower(-4);
                robot.backRight.setPower(4);
                robot.frontLeft.setPower(1);
                robot.frontRight.setPower(-1);
            }else if(gamepad1.right_bumper){
                robot.backLeft.setPower(4);
                robot.backRight.setPower(-4);
                robot.frontLeft.setPower(-1);
                robot.frontRight.setPower(1);

            //slow
            }else if(gamepad1.y){
                robot.backLeft.setPower(.25);
                robot.backRight.setPower(.25);
                robot.frontLeft.setPower(.4);
                robot.frontRight.setPower(.4);
            }else if(gamepad1.a){
                robot.backLeft.setPower(-.25);
                robot.backRight.setPower(-.25);
                robot.frontLeft.setPower(-.4);
                robot.frontRight.setPower(-.4);
            }else if(gamepad1.x){
                robot.backLeft.setPower(-.25);
                robot.backRight.setPower(.25);
                robot.frontLeft.setPower(.4);
                robot.frontRight.setPower(-.4);
            }else if(gamepad1.b){
                robot.backLeft.setPower(.25);
                robot.backRight.setPower(-.25);
                robot.frontLeft.setPower(-.4);
                robot.frontRight.setPower(.4);

            //stop dat shit
            }else{
                robot.backLeft.setPower(0);
                robot.backRight.setPower(0);
                robot.frontLeft.setPower(0);
                robot.frontRight.setPower(0);
            }

            //nyoom
            if(gamepad1.right_stick_button) {
                mult += .1;
            }
            if(gamepad1.left_stick_button) {
                mult -= .1;
            }


            idle();
        }
    }
}