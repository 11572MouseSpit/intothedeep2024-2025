/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Libs.DriveMecanumFTCLib;

/*
 * This OpMode executes a POV Game style Teleop for a direct drive robot
 * The code is structured as a LinearOpMode
 *
 * In this mode the left stick moves the robot FWD and back, the Right stick turns left and right.
 * It raises and lowers the arm using the Gamepad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */

@TeleOp(name="Robot: Teleop POV", group="Robot")
public class RobotTeleOp extends LinearOpMode {

        private final static HWProfile2 robot = new HWProfile2();
        private final LinearOpMode opMode = this;
        public DriveMecanumFTCLib drive = new DriveMecanumFTCLib(robot, opMode);
        public final static MSParams params = new MSParams();
        private DistanceSensor sensorColorRange;
 //       private Servo servoTest;
        private final boolean pad2input = true;

        private double DriveSpeed = 1;
        private double TurnSpeed = 1;
        private double StrafeSpeed = 1;

        private boolean IsOverrideActivated = false;

        public void runOpMode()
        {
            robot.init(hardwareMap);
            telemetry.addData("Status:", "Initialized");
            telemetry.update();

            robot.servoIntake.setPower(0);
            robot.servoWrist.setPosition(0);
            robot.servoBar.setPosition(0);
            robot.servoExtend.setPosition(params.Extend_IN);
            robot.servoExtendRight.setPosition(params. ExtendRight_IN);
            robot.servoBucket.setPosition(0);

            // Wait for the game to start (driver presses PLAY)
            waitForStart();
//        drive.haltandresetencoders();
            //runtime.reset();

            // run until the end of the match (driver presses STOP)
            double stickDrive = 0;
            double turn = 0;
            double strafe = 0;
            double leftPower = 0;
            double rightPower = 0;
//        double armUpDown;
            int armPosition = 0;
            int hangPosition = 0;
            int mBase = params.LIFT_RESET;
            while (opModeIsActive()) {
                stickDrive = this.gamepad1.left_stick_x * DriveSpeed;
                turn = this.gamepad1.right_stick_x * TurnSpeed;
                strafe = this.gamepad1.left_stick_y * StrafeSpeed;

                drive.StrafeDrive(stickDrive, strafe, turn);


                if (gamepad1.left_bumper) {
                    DriveSpeed = -1;
                    StrafeSpeed = -1;
                    TurnSpeed = -1;
                } else {
                    DriveSpeed = -0.5;
                    StrafeSpeed = -0.5;
                    TurnSpeed = -0.5;
                }

                if(gamepad1.y){
                    // What Happens when we hit Y - Dump to transfer

                    robot.servoExtendRight.setPosition(params.ExtendRight_CATCH);
                    robot.servoExtend.setPosition(params.Extend_Catch);
                    robot.servoBucket.setPosition(params.Bucket_Catch);
                    robot.servoBar.setPosition(params.Bar_Up);
                    robot.servoWrist.setPosition(params.Wrist_Up);
                    mBase = params.LIFT_RESET;
                }   // end of if(gamepad1.y)

                if(gamepad1.x){
                    // Intake Samples

                    robot.servoExtendRight.setPosition(params.ExtendRight_OUT);
                    robot.servoExtend.setPosition(params.Extend_OUT);
                    robot.servoBar.setPosition(params.Bar_Down);
                    robot.servoWrist.setPosition(params.Wrist_Down);
                    robot.servoBucket.setPosition(params.Bucket_Down);

                }   // end of if(gamepad1.x)

                if(gamepad1.a) {
                    robot.servoBucket.setPosition(params.Bucket_Dump);

                }
                if(gamepad1.b) {
                    robot.servoBucket.setPosition(params.Bucket_Down);
                    mBase = params.LIFT_RESET;
                }
                if(gamepad1.dpad_up) {
                    mBase = params.LIFT_Top_B;
                }
                if(gamepad1.dpad_down) {
                    mBase = params.LIFT_Bottom_B;
                }
                if(gamepad1.right_bumper) {
                    robot.servoIntake.setPower(.25);
                }
                if(gamepad1.left_bumper) {
                    robot.servoIntake.setPower(-.25);
                }
                if (!gamepad1.right_bumper && !gamepad1.left_bumper) {
                    robot.servoIntake.setPower(0);
                }

                    // limit the max and min value of mBase
                mBase = Range.clip(mBase,params.LIFT_MIN_LOW,params.LIFT_MAX_HIGH);
                drive.liftPosition(mBase);


                telemetry.addData("Status", "Running");
                telemetry.addData("Left Power", leftPower);
                telemetry.addData("Right Power", rightPower);
                telemetry.addData("Lift set point", mBase);

                telemetry.addData("Eli Pink Shirt", "yes");
                telemetry.update();

            }
        }
}
