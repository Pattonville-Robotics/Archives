package org.pattonvillerobotics.robotclasses;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.apache.commons.math3.geometry.euclidean.threed.Line;

/**
 * Created by developer on 10/15/16.
 */

public class GuideRail {

    public Servo guideRail;
    public LinearOpMode linearOpMode;

    /**
     * sets up a GuideRail object
     * @param hardwareMap the robot's hardwaremap
     * @param linearOpMode a lineaopmode object (for telemetry only)
     */
    public GuideRail(HardwareMap hardwareMap, LinearOpMode linearOpMode){
        this.linearOpMode = linearOpMode;
        guideRail = hardwareMap.servo.get("rail");
        guideRail.setPosition(0.7);
    }

    /**
     * Explicitly sets the position of the guide rail servo
     *
     * @param position position to set servo to
     */
    public void setPosition(double position){
        guideRail.setPosition(position);
        linearOpMode.telemetry.addData("Position", position);
    }

}
