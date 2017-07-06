package org.pattonvillerobotics.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

/**
 * Created by developer on 8/6/16.
 */
@TeleOp(name = "ServoValueFinder", group = "Test")
public class ServoValueFinder extends LinearOpMode {

    private final static String SERVO_NAME = "servo";
    private final static double SERVO_DEFAULT_POSITION = 0.5;

    private Servo servo;

    private void setUp() {

        servo = hardwareMap.servo.get(SERVO_NAME);
        servo.setPosition(SERVO_DEFAULT_POSITION);

    }

    @Override
    public void runOpMode() throws InterruptedException {

        setUp();
        waitForStart();

        while (opModeIsActive()) {

            double position = servo.getPosition();

            if (gamepad1.a) {
                position += 0.1;
            } else if (gamepad1.b) {
                position -= 0.1;
            } else if (gamepad1.x) {
                position = 0;
            } else if (gamepad1.y) {
                position = 1;
            }

            servo.setPosition(position);

            telemetry.addData("Servo Position", "Position" + position);

            Thread.sleep(250);

        }


    }


}
