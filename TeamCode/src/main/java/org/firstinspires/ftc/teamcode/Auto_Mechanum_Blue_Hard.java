/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a PushBot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous

public class Auto_Mechanum_Blue_Hard extends LinearOpMode {

    /**
     * Make some objects
     */
    private ElapsedTime runtime = new ElapsedTime();
    HardwareMap_Mechanum robot = new HardwareMap_Mechanum();

    /**
     * Make some variables
     */
    static double COUNTS_PER_INCH = 28.64834619782014;
    static double DRIVE_SPEED = 0.4;

    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);

        robot.colorSensor.enableLed(true);
        // robot.botSense.enableLed(true);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        glyph(2.5, 1);
        glyph(-1, 1.5);
        doNothing();
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
            doNothing();
            go(.5,1);
        } else {
            go(.5,1);
            robot.flicker.setPosition(0);
            doNothing();
            go(-.5,1);
        }
        doNothing();
        go(.5, 1.25);
        strafeRight(1, 1);
        go(.5,1.25);
        robot.rightExtend.setPosition(0);
        robot.leftExtend.setPosition(1);
        glyph(.6, 1);
        shimy(.5,2);
    }

    /**
     * Driving helper methods
     */
    private void doNothing() {
        final double x = getRuntime();
        while (getRuntime()<=x+2) {
            //do nothing
        }
    }

    private void pRight(double speed, double secs) {
        double currentTime = getRuntime();
        do{
            robot.backLeft.setPower(speed);
            robot.backRight.setPower(speed);
            robot.frontLeft.setPower(-speed);
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

    private void strafeLeft(double speed, double time) throws InterruptedException {
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

    private void strafeRight(double speed, double distance) throws InterruptedException {
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

    public void glyph(double pow, double time) throws InterruptedException {
        double currentTime = getRuntime();
        do{
            robot.extender.setPower(pow);
        }while(getRuntime()<=currentTime+time);
            robot.extender.setPower(0);
    }

    public void shimy(double pow, double time) throws InterruptedException {
        double currentTime = getRuntime();
        do{
            strafeRight(pow, time/4);
            go(pow, time/4);
            strafeLeft(pow, time/4);
            go(pow, time/4);
        }while(getRuntime()<=currentTime+time);
    }

    private void encoderDrive(double speed, double leftInches, double rightInches,
                              double bLeftInches, double bRightInches) throws InterruptedException {
        int newFrontLeftTarget=0;
        int newFrontRightTarget=0;
        int newBackLeftTarget=0;
        int newBackRightTarget=0;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newFrontLeftTarget = robot.frontLeft.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newFrontRightTarget = robot.frontRight.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            newBackLeftTarget = robot.backLeft.getCurrentPosition() + (int)(bLeftInches * COUNTS_PER_INCH);
            newBackRightTarget = robot.backRight.getCurrentPosition() + (int)(bRightInches * COUNTS_PER_INCH);

            robot.frontRight.setTargetPosition(newFrontRightTarget);
            robot.frontLeft.setTargetPosition(newFrontLeftTarget);
            robot.backRight.setTargetPosition(newBackRightTarget);
            robot.backLeft.setTargetPosition(newBackLeftTarget);

            // Turn On RUN_TO_POSITION
            robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            // reset the timeout time and start motion.
            runtime.reset();
            robot.frontLeft.setPower(Math.abs(speed));
            robot.frontRight.setPower(Math.abs(speed));
            robot.backLeft.setPower(Math.abs(speed));
            robot.backRight.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (robot.backRight.isBusy() && robot.backLeft.isBusy()
                            && robot.frontLeft.isBusy() && robot.frontRight.isBusy())) {
                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d",
                        robot.backRight.getCurrentPosition(),  newBackRightTarget);
                telemetry.update();

                // Allow time for other processes to run.
                idle();
            }

            // Stop all motion;
            robot.frontRight.setPower(0);
            robot.frontLeft.setPower(0);
            robot.backRight.setPower(0);
            robot.backLeft.setPower(0);

            robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250);
        }
    }
}