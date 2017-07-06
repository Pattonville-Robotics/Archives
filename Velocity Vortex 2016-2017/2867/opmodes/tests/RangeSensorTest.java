package org.pattonvillerobotics.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.opmodes.CustomRobotParameters;
import org.pattonvillerobotics.robotclasses.LineFollowerDrive;

/**
 * Created by joshua on 1/12/17.
 */

@Autonomous(name = "RangeTest", group = "Autonomous")
public class RangeSensorTest extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {

        LineFollowerDrive drive = new LineFollowerDrive(hardwareMap, this, CustomRobotParameters.AUTONOMOUS_ROBOT_PARAMETERS);

        waitForStart();

        drive.driveUntilDistance(3, 0.3);

    }

}
