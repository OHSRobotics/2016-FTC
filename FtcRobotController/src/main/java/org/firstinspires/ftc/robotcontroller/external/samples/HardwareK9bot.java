package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is a K9 robot.
 *
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case and some have single spaces between words.
 *
 * Motor channel:  Left  drive motor:        "left motor"
 * Motor channel:  Right drive motor:        "right motor"
 * Servo channel:  Servo to raise/lower arm: "arm"
 * Servo channel:  Servo to open/close claw: "claw"
 *
 * Note: the configuration of the servos is such that:
 *   As the arm servo approaches 0, the arm position moves up (away from the floor).
 *   As the claw servo approaches 0, the claw opens up (drops the game element).
 */
public class HardwareK9bot
{
    /* Public OpMode members. */
    public DcMotor  leftfrontMotor;
    public DcMotor  rightfrontMotor;
    public DcMotor  leftbackMotor;
    public DcMotor  rightbackMotor;
    public DcMotor  ballKicker;
    public Servo    buttonPusher;

    public final static double ARM_HOME = 0.5;

    /* Local OpMode members. */
    HardwareMap hwMap  = null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public HardwareK9bot() {
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // save reference to HW Map
        hwMap = ahwMap;

        // Define and Initialize Motors
        leftfrontMotor   = hwMap.dcMotor.get("leftfront");
        rightfrontMotor  = hwMap.dcMotor.get("rightfront");
        leftbackMotor   = hwMap.dcMotor.get("leftback");
        rightbackMotor  = hwMap.dcMotor.get("rightback");
        ballKicker  = hwMap.dcMotor.get("ballkick");
        buttonPusher = hwMap.servo.get("arm");

        // Set all motors to zero power
        leftfrontMotor.setPower(0);
        rightfrontMotor.setPower(0);
        leftbackMotor.setPower(0);
        rightbackMotor.setPower(0);
        ballKicker.setPower(0);

        buttonPusher.setPosition(ARM_HOME);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        leftfrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightfrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftbackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightbackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ballKicker.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Define and initialize ALL installed servos.
        /*
        arm = hwMap.servo.get("arm");
        claw = hwMap.servo.get("claw");
        arm.setPosition(ARM_HOME);
        claw.setPosition(CLAW_HOME);
        */
    }

    /***
     *
     * waitForTick implements a periodic delay. However, this acts like a metronome with a regular
     * periodic tick.  This is used to compensate for varying processing times for each cycle.
     * The function looks at the elapsed cycle time, and sleeps for the remaining time interval.
     *
     * @param periodMs  Length of wait cycle in mSec.
     * @throws InterruptedException
     */
    public void waitForTick(long periodMs)  throws InterruptedException {

        long  remaining = periodMs - (long)period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0)
            Thread.sleep(remaining);

        // Reset the cycle clock for the next pass.
        period.reset();
    }
}