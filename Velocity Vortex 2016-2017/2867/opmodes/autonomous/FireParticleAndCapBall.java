package org.pattonvillerobotics.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.robotclasses.CommonAutonomous;

/**
 * Created by joshua on 3/29/17.
 */

@Autonomous(name = "Particle + Cap Ball", group = "Autonomous")
public class FireParticleAndCapBall extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        CommonAutonomous autonomous = new CommonAutonomous(AllianceColor.BLUE, hardwareMap, this);

        waitForStart();

        sleep(10000);

        autonomous.fireParticle();
        autonomous.drive.moveInches(Direction.BACKWARD, 25, 0.6);
        autonomous.drive.stop();


    }

}
