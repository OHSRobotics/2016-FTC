package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

/**
 * This OpMode uses the common HardwareK9bot class to define the devices on the robot.
 * All device access is managed through the HardwareK9bot class. (See this class for device names)
 * The code is structured as a LinearOpModeS
 *
 * This particular OpMode executes a basic Tank Drive Teleop for the K9 bot
 * It raises and lowers the arm using the Gampad Y and A buttons respectively.
 * It also opens and closes the claw slowly using the X and B buttons.
 *
 * Note: the configuration of the servos is such that
 * as the arm servo approaches 0, the arm position moves up (away from the floor).
 * Also, as the claw servo approaches 0, the claw opens up (drops the game element).
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="K9bot: Telop Tank", group="K9bot")
public class K9botTeleopTank_Linear extends LinearOpMode {

    /* Declare OpMode members. */
    public HardwareK9bot robot = new HardwareK9bot();

    @Override
    public void runOpMode() throws InterruptedException {
        float LSY;
        float RSX;
        float LSX;
        float ballKick;
        float ballCollect;
        float divide = 1.0f;
        float buttonPusherPosition = 0.2f, buttonPusherDelta = 0.05f;

        /* Initialize the hardware variables.
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        robot.buttonPusher.setPosition(1.0);
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            LSY = gamepad1.left_stick_y;
            RSX = gamepad1.right_stick_x;
            LSX = gamepad1.left_stick_x;

            setPower((-LSY + RSX + LSX) / divide, (-LSY + RSX - LSX) / divide, (-LSY - RSX - LSX) / divide, (-LSY - RSX + LSX) / divide);

            ballCollect = gamepad2.left_trigger;
            ballKick = gamepad2.right_trigger;

            robot.ballKicker.setPower(ballKick - Range.clip(ballCollect, 0.0f, 0.5f));
            if (gamepad1.left_bumper){
                divide = 4.0f;
            }else if (gamepad1.right_bumper){
                divide = 1.0f;
            }
            telemetry.addData("Current servo posirtion ", "" + robot.buttonPusher.getPosition());
            if (gamepad2.x) {
                buttonPusherPosition += buttonPusherDelta;
            }
            if (gamepad2.b) {
                buttonPusherPosition -= buttonPusherDelta;
            }
            buttonPusherPosition = Range.clip(buttonPusherPosition, 0.0f, 1.0f);
            robot.buttonPusher.setPosition(buttonPusherPosition);


            telemetry.update();

            // Pause for metronome tick.  40 mS each cycle = update 25 times a second.
            robot.waitForTick(40);
            idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
        }
    }

    public void move(Direction direction, double power){
        if (direction == Direction.FORWARD){
            setPower(power, power, power, power);
        } else if (direction == Direction.BACKWARD) {
            setPower(-power, -power, -power, -power);
        } else if (direction == Direction.LEFT) {
            setPower(-power, power, power, -power);
        } else if (direction == Direction.RIGHT) {
            setPower(power, -power, -power, power);
        }
    }

    public void zero(){
        robot.leftfrontMotor.setPower(0);
        robot.leftbackMotor.setPower(0);
        robot.rightfrontMotor.setPower(0);
        robot.rightbackMotor.setPower(0);
    }

    public static void moveForeward(K9botTeleopTank_Linear bot, double power){
        bot.robot.leftfrontMotor.setPower(power);
        bot.robot.leftbackMotor.setPower(power);
        bot.robot.rightfrontMotor.setPower(-power);
        bot.robot.rightbackMotor.setPower(-power);
    }

    public void setPower(double leftFront, double leftBack, double rightFront, double rightBack){
        robot.leftfrontMotor.setPower(leftFront);
        robot.leftbackMotor.setPower(leftBack);
        robot.rightfrontMotor.setPower(-rightFront);
        robot.rightbackMotor.setPower(-rightBack);
    }

    public void setPowerAbsloute(double leftFront, double leftBack, double rightFront, double rightBack){
        robot.leftfrontMotor.setPower(leftFront);
        robot.leftbackMotor.setPower(leftBack);
        robot.rightfrontMotor.setPower(rightFront);
        robot.rightbackMotor.setPower(rightBack);
    }

    public enum Direction {
        FORWARD, BACKWARD, LEFT, RIGHT
    }
}


