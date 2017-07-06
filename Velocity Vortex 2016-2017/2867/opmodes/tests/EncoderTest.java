package org.pattonvillerobotics.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.opmodes.CustomRobotParameters;

/**
 * Created by developer on 10/15/16.
 */

@Autonomous(name = "EncoderTest", group = "Tests")
public class EncoderTest extends LinearOpMode {

    private EncoderDrive drive;

    @Override
    public void runOpMode() throws InterruptedException {

        initialize();
        waitForStart();

        /*drive.moveInches(Direction.FORWARD, 50, 0.3);
        sleep(5000);*/

        drive.moveInches(Direction.FORWARD, 50, 1.0);
        drive.stop();
        sleep(1000);

        drive.moveInches(Direction.BACKWARD, 25, 1.0);
        drive.stop();
        sleep(1000);
        /*drive.moveInches(Direction.FORWARD, 50, 0.25);
        drive.stop();
        sleep(1000);
        drive.moveInches(Direction.BACKWARD, 25, 0.25);
        drive.stop();
        sleep(1000);*/

        //drive.rotateDegrees(Direction.LEFT, 45, 0.25);

        drive.rotateDegrees(Direction.LEFT, 90, 1.0);
        drive.stop();
        sleep(5000);

        drive.rotateDegrees(Direction.RIGHT, 90, 0.25);
        drive.stop();
        sleep(5000);
        /*drive.rotateDegrees(Direction.LEFT, 180, 0.25);
        sleep(5000);
        drive.rotateDegrees(Direction.RIGHT, 180, 0.25);
        sleep(5000);*/

    }

    private void initialize(){

        drive = new EncoderDrive(hardwareMap, this, CustomRobotParameters.AUTONOMOUS_ROBOT_PARAMETERS);

    }

}
