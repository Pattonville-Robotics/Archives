package org.pattonvillerobotics.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.opmodes.CustomRobotParameters;
import org.pattonvillerobotics.robotclasses.CommonAutonomous;
import org.pattonvillerobotics.robotclasses.LineFollowerDrive;

/**
 * Created by developer on 11/18/16.
 */
@Autonomous(name = "Both Beacons BLUE", group = "Autonomous")
public class BothBeacons_1_B extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        CommonAutonomous commonAutonomous = new CommonAutonomous(AllianceColor.BLUE, hardwareMap, this);

        waitForStart();

        commonAutonomous.wallPos1ToBeacon1();
        commonAutonomous.pressBeacon();
        commonAutonomous.beacon1ToBeacon2();
        commonAutonomous.pressBeacon();

    }

}
