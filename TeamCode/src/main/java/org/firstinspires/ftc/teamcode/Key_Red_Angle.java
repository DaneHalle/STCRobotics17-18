
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

@Autonomous(name = "Red Angle", group = "Vuforia")
@Disabled
public class Key_Red_Angle extends LinearOpMode
{
    OpenGLMatrix lastLocation = null; // WARNING: VERY INACCURATE, USE ONLY TO ADJUST TO FIND IMAGE AGAIN! DO NOT BASE MAJOR MOVEMENTS OFF OF THIS!!
    double tX; // X value extracted from our the offset of the traget relative to the robot.
    double tZ; // Same as above but for Z
    double tY; // Same as above but for Y
    // -----------------------------------
    double rX; // X value extracted from the rotational components of the tartget relitive to the robot
    double rY; // Same as above but for Y
    double rZ; // Same as above but for Z

    DcMotor right; // Random Motor
    DcMotor left; // Random Motor

    VuforiaLocalizer vuforia;

    private ElapsedTime runtime = new ElapsedTime();
    HardwareMap_Mechanum robot = new HardwareMap_Mechanum();

    public void runOpMode()
    {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "Aafrxcb/////AAAAmU5O6jkL20EzvU3JOVtRlf80SeWDfT1t27nX2NwXJHP4MJrXd73E8rWt0KcoBm6XEM9IMmtVW8XCcSDjT5GgxAB7n57ePEwux95knT6fL4jvZYCQCppQ5ryzsn/H9IZKefU6PAJNMU+IvuXBBpKnRG/uQ2KzWZEqWPfwdan7aWGQ/SSbPjo7JTiIMDzfYD7UZfSCLF/V5+W4ThlY/fTBvjEPSDiIgNrJkP4wGm2yVwVRbYM6XGg67oiLQ3Gyk0XSeJon379NpcSd1Ff1vLcnEiXNKC41QM05EZ/h8K3WdKvSfSvePOmfNvV6waMq3Ht9Oxd6zgmCMwO0Ra5c5av/6sRz3ikjtYHeNGULYdKqSAIa";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK; // Use FRONT Camera (Change to BACK if you want to use that one)
        parameters.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES; // Display Axes

        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);

        robot.init(hardwareMap);
        waitForStart();
        runtime.reset();

        robot.rightExtend.setPosition(0);
        robot.leftExtend.setPosition(1);
        robot.flicker.setPosition(0);

        relicTrackables.activate(); // Activate Vuforia

        while (opModeIsActive())
        {
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) { // Test to see if image is visable
                OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) relicTemplate.getListener()).getPose(); // Get Positional value to use later
                telemetry.addData("Pose", format(pose));
                if (pose != null)
                {
                    VectorF trans = pose.getTranslation();
                    Orientation rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

                    // Extract the X, Y, and Z components of the offset of the target relative to the robot
                    tX = trans.get(0);
                    tY = trans.get(1);
                    tZ = trans.get(2);

                    // Extract the rotational components of the target relative to the robot. NOTE: VERY IMPORTANT IF BASING MOVEMENT OFF OF THE IMAGE!!!!
                    rX = rot.firstAngle;
                    rY = rot.secondAngle;
                    rZ = rot.thirdAngle;
                }
                if (vuMark == RelicRecoveryVuMark.LEFT)
                { // Test to see if Image is the "LEFT" image and display value.
                    telemetry.addData("Key is", "Left");
                    goLeft();
                } else if (vuMark == RelicRecoveryVuMark.RIGHT)
                { // Test to see if Image is the "RIGHT" image and display values.
                    telemetry.addData("Key is", "Right");
                    goRight();
                } else if (vuMark == RelicRecoveryVuMark.CENTER)
                { // Test to see if Image is the "CENTER" image and display values.
                    telemetry.addData("Key is", "Center");
                    goCenter();
                }
            } else
            {
                telemetry.addData("Key is", "not visible");
                goCenter();
            }
            telemetry.update();
        }
    }

    String format(OpenGLMatrix transformationMatrix)
    {
        return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
    }

    private void goLeft(){
        glyph(2.5, 1);
        glyph(-1, 1.5);
        hold();
        robot.rightExtend.setPosition(1);
        robot.leftExtend.setPosition(0);
        glyph(1, 1);
        robot.flicker.setPosition(1);

        //detect ball color and flick correct ball
        telemetry.addData("Red", robot.colorSensor.red());
        telemetry.addData("Blue", robot.colorSensor.blue());
        telemetry.update();

        doNothing();
        if (robot.colorSensor.red() > robot.colorSensor.blue()) {
            go(-.5,1);
            robot.flicker.setPosition(0);
            hold();
            go(.5,1);
        } else {
            go(.5,1);
            robot.flicker.setPosition(0);
            hold();
            go(-.5,1);
        }
        go(.5, 1.25);
        strafeLeft(1, .75);
        go(.5,1.25);
        robot.rightExtend.setPosition(0);
        robot.leftExtend.setPosition(1);
        glyph(.6, 1);
    }

    private void goRight(){
        glyph(2.5, 1);
        glyph(-1, 1.5);
        hold();
        robot.rightExtend.setPosition(1);
        robot.leftExtend.setPosition(0);
        glyph(1, 1);
        robot.flicker.setPosition(1);

        //detect ball color and flick correct ball
        telemetry.addData("Red", robot.colorSensor.red());
        telemetry.addData("Blue", robot.colorSensor.blue());
        telemetry.update();

        doNothing();
        if (robot.colorSensor.red() > robot.colorSensor.blue()) {
            go(-.5,1);
            robot.flicker.setPosition(0);
            hold();
            go(.5,1);
        } else {
            go(.5,1);
            robot.flicker.setPosition(0);
            hold();
            go(-.5,1);
        }
        go(.5, 1.25);
        strafeLeft(1, 1.25);
        go(.5,1.25);
        robot.rightExtend.setPosition(0);
        robot.leftExtend.setPosition(1);
        glyph(.6, 1);
    }

    private void goCenter(){
        glyph(2.5, 1);
        glyph(-1, 1.5);
        hold();
        robot.rightExtend.setPosition(1);
        robot.leftExtend.setPosition(0);
        glyph(1, 1);
        robot.flicker.setPosition(1);

        //detect ball color and flick correct ball
        telemetry.addData("Red", robot.colorSensor.red());
        telemetry.addData("Blue", robot.colorSensor.blue());
        telemetry.update();

        doNothing();
        if (robot.colorSensor.red() > robot.colorSensor.blue()) {
            go(-.5,1);
            robot.flicker.setPosition(0);
            hold();
            go(.5,1);
        } else {
            go(.5,1);
            robot.flicker.setPosition(0);
            hold();
            go(-.5,1);
        }
        go(.5, 1.25);
        strafeLeft(1, 1);
        go(.5,1.25);
        robot.rightExtend.setPosition(0);
        robot.leftExtend.setPosition(1);
        glyph(.6, 1);
    }

    private void doNothing() {
        final double x = getRuntime();
        while (getRuntime()<=x+2) {
            //do nothing
        }
    }

    private void hold() {
        final double x=getRuntime()+.5;
        while(getRuntime()<=x){
            //hold
        }
    }

    private void turn(double speed, double secs) {
        double currentTime = getRuntime();
        do{
            robot.backLeft.setPower(speed);
            robot.backRight.setPower(-speed);
            robot.frontLeft.setPower(speed);
            robot.frontRight.setPower(-speed);
        }while(getRuntime()<=currentTime+secs);
        robot.frontLeft.setPower(0);
        robot.frontRight.setPower(0);
        robot.backLeft.setPower(0);
        robot.backRight.setPower(0);
    }

    private void go(double speed, double secs) {
        double currentTime = getRuntime();
        do {
            robot.frontLeft.setPower(speed);
            robot.frontRight.setPower(speed);
            robot.backLeft.setPower(speed);
            robot.backRight.setPower(speed);
        }while(getRuntime()<=currentTime+secs);
        robot.frontLeft.setPower(0);
        robot.frontRight.setPower(0);
        robot.backLeft.setPower(0);
        robot.backRight.setPower(0);
    }

    private void strafeLeft(double speed, double time)  {
        double currentTime = getRuntime();
        do{
            robot.backLeft.setPower(-speed);
            robot.backRight.setPower(speed);
            robot.frontLeft.setPower(speed);
            robot.frontRight.setPower(-speed);
        }while(getRuntime()<=currentTime+time);
        robot.backLeft.setPower(0);
        robot.backRight.setPower(0);
        robot.frontLeft.setPower(0);
        robot.frontRight.setPower(0);
    }

    private void strafeRight(double speed, double distance)  {
        double currentTime = getRuntime();
        do{
            robot.backLeft.setPower(speed);
            robot.backRight.setPower(-speed);
            robot.frontLeft.setPower(-speed);
            robot.frontRight.setPower(speed);
        }while(getRuntime()<=currentTime+distance);
        robot.frontLeft.setPower(0);
        robot.frontRight.setPower(0);
        robot.backLeft.setPower(0);
        robot.backRight.setPower(0);
    }

    private void glyph(double pow, double time)  {
        double currentTime = getRuntime();
        do{
            robot.extender.setPower(pow);
        }while(getRuntime()<=currentTime+time);
        robot.extender.setPower(0);
    }

    private void shimmy(double pow, double time)  {
        double currentTime = getRuntime();
        do{
            strafeRight(pow, time/4);
            go(pow, time/4);
            strafeLeft(pow, time/4);
            go(pow, time/4);
        }while(getRuntime()<=currentTime+time);
    }
}