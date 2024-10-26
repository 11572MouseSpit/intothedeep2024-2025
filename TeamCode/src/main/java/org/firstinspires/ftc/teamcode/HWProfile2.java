package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class HWProfile2 {


    /*
     * Constants
     */

    public final double PIVOT_SPEED = 0.5;
    public final double COUNTS_PER_ROTATION = 28;
    public final double GB_COUNTS_PER_ROTATION = 28;    // goBilda encoder value
    public final double MIN_PIDROTATE_POWER = 0.10;


    /*
     *  Constants & variables for wheel parameters
     */

    public final double DRIVE_TICKS_PER_INCH = 32;

    public final double STRAFE_FACTOR = 0.9;

    public final double RIGHT_DRIVE_CORRECTION_MULTIPLIER = 1.4;
    public final double LEFT_DRIVE_CORRECTION_MULTIPLIER = 1.2;

    public final double MAX_DRIVING_POWER = 1;

    public double MIN_STRAFE_POWER = 0.35;

    public final double PID_Kp = 0.08;
    public final double PID_Ki = 0.01;
    public final double PID_Kd = 0.000001;
    public final double PID_MIN_SPEED = 0.05;
    public final double PID_ROTATE_ERROR = 1;

    public final double DRIVE_Kp = 0.05;
    public final double DRIVE_Ki = 0.01;
    public final double DRIVE_Kd = 0.31;

    /*
     * Hardware devices
     */

    public RevIMU imu = null;

    public DcMotorEx motorLF;
    public DcMotorEx motorLR;
    public DcMotorEx motorRF;
    public DcMotorEx motorRR;

    public DcMotorEx motorLift;

    public CRServo servoIntake;
    public Servo servoWrist;
    public Servo servoBar;
    public Servo servoExtend;
    public Servo servoBucket;
    public Servo servoExtendRight;




//    public MecanumDrive mecanum = null;

    HardwareMap hwMap;

    /*
     * Declare Odometry hardware
     */

    /* Constructor */
    public HWProfile2() {
    }

    public void init(HardwareMap ahwMap) {

        hwMap = ahwMap;

        motorLF = ahwMap.get(DcMotorEx.class,"motorLF");
        motorLF.setDirection(DcMotor.Direction.REVERSE);
        motorLF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLF.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        motorLF.setPower(0);
        motorLF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        motorLR = ahwMap.get(DcMotorEx.class,"motorLR");
        motorLR.setDirection(DcMotor.Direction.REVERSE);
        motorLR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLR.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        motorLR.setPower(0);
        motorLR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        motorRF = ahwMap.get(DcMotorEx.class,"motorRF");
        motorRF.setDirection(DcMotor.Direction.FORWARD);
        motorRF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRF.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        motorRF.setPower(0);
        motorRF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        motorRR = ahwMap.get(DcMotorEx.class,"motorRR");
        motorRR.setDirection(DcMotor.Direction.FORWARD);
        motorRR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRR.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        motorRR.setPower(0);
        motorRR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //drivebase init
//        mecanum = new MecanumDrive(motorLF, motorRF, motorLR, motorRR);

        motorLift = ahwMap.get(DcMotorEx.class, "motorLift");
        motorLift.setDirection(DcMotor.Direction.REVERSE);
        motorLift.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        motorLift.setTargetPosition(0);
        motorLift.setPower(0);
        motorLift.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        motorLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        /**
         * Initialize Servos
         **/
        //servoGrabber = hwMap.servo.get("servoGrabber");
        // servoGrabber2 = hwMap.servo.get("servoGrabber2");
        servoIntake = ahwMap.crservo.get("servoIntake");
        servoWrist = ahwMap.servo.get("servoWrist");
        servoBar = ahwMap.servo.get("servoBar");
        servoExtend = ahwMap.servo.get("servoExtend");
        servoBucket = ahwMap.servo.get("servoBucket");
        servoExtendRight = ahwMap.servo.get("servoExtendRight");

        // Zeroing Servos
        //servoIntake.setPower(0.5);
        //servoWrist.setPosition(0);
        //servoBar.setPosition(1);
        //servoExtend.setPosition(0);
        //servoBucket.setPosition(0.5);
        //servoExtendRight.setPosition(0);


        imu = new RevIMU(ahwMap);
        imu.init();


        /* Webcam device will go here */
//        webcam = hwMap.get(WebcamName.class, "Webcam 1");
    }


    /**
     * The RunMode of the motor.
     */
    public enum RunMode {
        VelocityControl, PositionControl, RawPower
    }
}
