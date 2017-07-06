package org.pattonvillerobotics.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.opmodes.CustomizedRobotParameters;

/**
 * Created by greg on 3/10/2017.
 */
@Autonomous(name = "TestAuto", group = OpModeGroups.TESTING)
public class TestAuto extends LinearOpMode {

    private EncoderDrive drive;

    @Override
    public void runOpMode() throws InterruptedException {
        drive = new EncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS);

        drive.moveInches(Direction.BACKWARD, 5, .1);
        drive.rotateDegrees(Direction.RIGHT, 25, .2);
    }
}
