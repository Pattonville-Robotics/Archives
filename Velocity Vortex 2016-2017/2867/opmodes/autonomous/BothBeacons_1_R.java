package org.pattonvillerobotics.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.opmodes.CustomRobotParameters;
import org.pattonvillerobotics.robotclasses.CommonAutonomous;
import org.pattonvillerobotics.robotclasses.LineFollowerDrive;

/**
 * Created by mostafay on 10/4/16.
 */

@Autonomous(name = "Both Beacons RED", group = "Autonomous")
public class BothBeacons_1_R extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        CommonAutonomous commonAutonomous = new CommonAutonomous(AllianceColor.RED, hardwareMap, this);

        waitForStart();

        commonAutonomous.wallPos1ToBeacon1();
        commonAutonomous.pressBeacon();
        commonAutonomous.beacon1ToBeacon2();
        commonAutonomous.pressBeacon();

    }
}
