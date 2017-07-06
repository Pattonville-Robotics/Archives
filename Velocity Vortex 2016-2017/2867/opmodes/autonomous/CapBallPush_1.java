package org.pattonvillerobotics.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.drive.SimpleDrive;
import org.pattonvillerobotics.opmodes.CustomRobotParameters;
import org.pattonvillerobotics.robotclasses.CommonAutonomous;
import org.pattonvillerobotics.robotclasses.LineFollowerDrive;

/**
 * Created by mostafay on 10/4/16.
 */

@Autonomous(name = "Capball Push", group = "Autonomous")
public class CapBallPush_1 extends LinearOpMode {

    /**
     * <p>
     *     Initalizes robot and waits for start.
     *     After Start sets the direction to forward at .5 speed
     *     sleeps for 5 seconds
     * </p>
    */
    @Override
    public void runOpMode() throws InterruptedException {
        //drive forward 60in
        //Push Cap Ball
        //5 inches forward
        //Park on center vortex

        CommonAutonomous commonAutonomous = new CommonAutonomous(AllianceColor.RED, hardwareMap, this);

        waitForStart();

        commonAutonomous.wallToBall();

    }


}