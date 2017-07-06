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

@Autonomous(name = "Beacon One RED", group = "Autonomous")
public class FirstBeacon_1_R extends LinearOpMode {

    @Override
    public void runOpMode(){
        //drive 60 in forward
        //push cap ball
        //turn 90 left
        //55 inches forward
        //Read beacon color
        //5 Back
        //rotate presser mech.
        //10 inches forward
        //Press beacon

        CommonAutonomous commonAutonomous = new CommonAutonomous(AllianceColor.RED, hardwareMap, this);

        waitForStart();
        commonAutonomous.wallPos1ToBeacon1();
        commonAutonomous.pressBeacon();

    }
}