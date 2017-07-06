package org.pattonvillerobotics.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.opmodes.CustomRobotParameters;
import org.pattonvillerobotics.robotclasses.CommonAutonomous;
import org.pattonvillerobotics.robotclasses.LineFollowerDrive;

/**
 * Created by joshua on 11/30/16.
 */
@TeleOp(name = "Judge Code", group = "TeleOp")
public class JudgeCode extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        CommonAutonomous commonAutonomous = new CommonAutonomous(AllianceColor.RED, hardwareMap, this);
        waitForStart();
        commonAutonomous.judgeCode();
    }
}
