package org.pattonvillerobotics.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.opmodes.CustomRobotParameters;
import org.pattonvillerobotics.robotclasses.CommonAutonomous;
import org.pattonvillerobotics.robotclasses.LineFollowerDrive;

/**
 * Created by mostafay on 10/4/16.
 */

@Autonomous(name = "Beacon One BLUE", group = "Autonomous")
public class FirstBeacon_1_B extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        //60 inches forward
        //Push Cap ball
        //53 right
        //60 inches forward
        //63 degrees right
        //20 inches forward
        //read beacon color
        //4 inches forward
        //press beacon
        CommonAutonomous commonAutonomous = new CommonAutonomous(AllianceColor.BLUE, hardwareMap, this);

        waitForStart();

        commonAutonomous.wallPos1ToBeacon1();
        commonAutonomous.pressBeacon();
    }

}
