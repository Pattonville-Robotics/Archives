package org.pattonvillerobotics.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.opmodes.CustomRobotParameters;
import org.pattonvillerobotics.robotclasses.CommonAutonomous;
import org.pattonvillerobotics.robotclasses.LineFollowerDrive;

/**
 * Created by developer on 11/1/16.
 */

@Autonomous(name = "LineDetectorTest", group = "Tests")
public class LineDetectorTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        LineFollowerDrive drive = new LineFollowerDrive(hardwareMap, this, CustomRobotParameters.AUTONOMOUS_ROBOT_PARAMETERS);
        CommonAutonomous commonAutonomous = new CommonAutonomous(AllianceColor.RED, hardwareMap, this);

        waitForStart();

        /*commonAutonomous.wallToBeacon1WithLine();
        commonAutonomous.pressBeacon();
        commonAutonomous.beacon1ToBeacon2WithLine();
        commonAutonomous.pressBeacon();*/


    }

}
