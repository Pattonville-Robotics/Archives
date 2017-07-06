package org.pattonvillerobotics.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.BeaconColorSensor;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.opmodes.CustomRobotParameters;
import org.pattonvillerobotics.robotclasses.ButtonPresser;

/**
 * Created by developer on 9/27/16.
 */

@TeleOp(name = "Full Color Sensor Test", group = "Common")
public class FullColorSensorTest extends LinearOpMode {

    BeaconColorSensor beaconColorSensor;
    ButtonPresser buttonPresser;
    EncoderDrive drive;

    @Override
    public void runOpMode() throws InterruptedException {

        initialize();
        waitForStart();

        drive.moveInches(Direction.FORWARD, 21, 0.25);

        pressButton();

        drive.moveInches(Direction.FORWARD, 3, 0.25);

        drive.stop();

    }


    public void initialize(){

        ColorSensor cs = hardwareMap.colorSensor.get("color_sensor");
        beaconColorSensor = new BeaconColorSensor(cs);

        buttonPresser = new ButtonPresser(hardwareMap);

        drive = new EncoderDrive(hardwareMap, this, CustomRobotParameters.AUTONOMOUS_ROBOT_PARAMETERS);

    }

    private void pressButton(){
        telemetry.addData("Red:", beaconColorSensor.red());
        telemetry.addData("Blue:", beaconColorSensor.blue());

        telemetry.update();
        beaconColorSensor.colorSensor.enableLed(false);

        beaconColorSensor.determineColor(AllianceColor.BLUE, new Runnable() {
            @Override
            public void run() {
                buttonPresser.presserRight();
            }
        }, new Runnable() {
            @Override
            public void run() {
                buttonPresser.presserLeft();
            }
        }, new Runnable() {
            @Override
            public void run() {
                drive.moveInches(Direction.FORWARD, 1, 0.2);
                pressButton();
            }
        });

    }

}
