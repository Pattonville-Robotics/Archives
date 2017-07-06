package org.pattonvillerobotics.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.robotclasses.CommonAutonomous;

/**
 * Created by joshua on 3/3/17.
 */

@Autonomous(name="FireParticle", group="Autonomous")
public class FireParticle extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        CommonAutonomous autonomous = new CommonAutonomous(AllianceColor.BLUE, hardwareMap, this);

        waitForStart();

        autonomous.fireParticle();

    }

}
