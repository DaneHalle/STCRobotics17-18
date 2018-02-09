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
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Red Angle Right", group ="Concept")
@Disabled
public class Red_Angle_Right extends LinearOpMode {

    /**
     * Make some objects
     */
    private ElapsedTime runtime = new ElapsedTime();
    HardwareMap_Mechanum robot = new HardwareMap_Mechanum();

    @Override
    public void runOpMode()  {
        robot.init(hardwareMap);

        robot.colorSensor.enableLed(true);

        // Wait for the game to start (driver presses PLAY)
        runtime.reset();

        robot.rightExtend.setPosition(0);
        robot.leftExtend.setPosition(1);
        robot.flicker.setPosition(0);

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

    /**
     * Driving helper methods
     */
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