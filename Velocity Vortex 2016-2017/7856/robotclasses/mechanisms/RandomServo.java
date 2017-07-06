package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by greg on 3/11/2017.
 */

public class RandomServo extends AbstractMechanism {

    private Servo servo;

    public RandomServo(HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        super(hardwareMap, linearOpMode);

        servo = hardwareMap.servo.get("servo");
    }

    public void setOut() {
        servo.setPosition(0);
    }

    public void setIn() {
        servo.setPosition(1);
    }
}
