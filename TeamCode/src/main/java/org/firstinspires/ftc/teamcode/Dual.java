package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.lang.Math;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@TeleOp(name="Template: Linear OpMode", group="Linear Opmode")  // @Autonomous(...) is the other common choice
@Disabled
public class Dual extends LinearOpMode {

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

        double right = 0;
        double left  = 0;
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

            //Arm motors/servos controlled by gamepad2
            //Aidan's Arm o' Death
            if (gamepad2.right_stick_y > 0) {
                robot.moveArm.setPower(-.6);

            } else if(gamepad2.right_stick_y < 0) {
                robot.moveArm.setPower(.6);
            }else{
                robot.moveArm.setPower(0);
                robot.moveArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }
            if (gamepad2.left_stick_y > 0 || gamepad2.left_stick_y < 0) {
                robot.armMotor.setPower(gamepad2.left_stick_y);
            } else {
                robot.armMotor.setPower(0);
            }

            //The claw o' Death
            if (gamepad2.right_bumper) {
                robot.theClaw.setPosition(.944444);
            } else {
                robot.theClaw.setPosition(0);
            }

            //Linear gear arm!!!!!!!
            if (gamepad2.x) {
                robot.extender.setPower(.4);
            } else if(gamepad2.b) {
                robot.extender.setPower(-.4);
            }else{
                robot.extender.setPower(0);
            }

            //Paddles on Ben's shitty Scissor Arm
            if (gamepad2.left_bumper) {
                robot.leftExtend.setPosition(1);
                robot.rightExtend.setPosition(0);
            } else {
                robot.leftExtend.setPosition(0);
                robot.rightExtend.setPosition(1);
            }

            //height control of the Linear gear arm
            if (gamepad2.a) {
                robot.ben.setPower(1);
            } else if (gamepad2.y) {
                robot.ben.setPower(-1);
            }else {
                robot.ben.setPower(0);
            }

        idle();
        }
    }
}