package org.pattonvillerobotics.opmodes;

import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.pattonvillerobotics.commoncode.robotclasses.drive.RobotParameters;

/**
 * Created by developer on 10/4/16.
 */

public class CustomRobotParameters {

    public static final RobotParameters AUTONOMOUS_ROBOT_PARAMETERS;

    static {
        AUTONOMOUS_ROBOT_PARAMETERS = new RobotParameters.Builder()
                .leftDriveMotorDirection(DcMotorSimple.Direction.FORWARD)
                .rightDriveMotorDirection(DcMotorSimple.Direction.REVERSE)
                .encodersEnabled(true)
                .gyroEnabled(true)
                .driveGearRatio(3) //Needs Edit
                .wheelBaseRadius(8.25 * 180 / 170) //Needs Edit
                .wheelRadius(2 * (45.0 / 50.0) * (98.0 / 100.0) * (16.0 / 50.0))
                .dcMotorMaxSpeed((int) (1440 * 1.35))
                .build();
    }

    public static final RobotParameters TELEOP_ROBOT_PARAMETERS;

    static{
        TELEOP_ROBOT_PARAMETERS = new RobotParameters.Builder()
                .leftDriveMotorDirection(DcMotorSimple.Direction.FORWARD)
                .rightDriveMotorDirection(DcMotorSimple.Direction.REVERSE)
                .encodersEnabled(true)
                .gyroEnabled(false)
                .driveGearRatio(3) //Needs Edit
                .wheelBaseRadius(8.25 * 180 / 170) //Needs Edit
                .wheelRadius(2 * (45.0 / 50.0) * (98.0 / 100.0) * (16.0 / 50.0))
                .dcMotorMaxSpeed((int) (1440 * 1.9))
                .build();
    }

}
