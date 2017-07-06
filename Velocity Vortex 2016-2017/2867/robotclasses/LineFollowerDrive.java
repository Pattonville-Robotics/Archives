package org.pattonvillerobotics.robotclasses;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorMRRangeSensor;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.drive.AbstractDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.RobotParameters;

/**
 * Created by developer on 11/1/16.
 */

public class LineFollowerDrive extends EncoderDrive {

    /**
     * the reflectivity value of the white tape as determined
     * in testing.
     */
    private static final double WHITE_TAPE_REFLECTIVITY_MIN = 0.01;
    private static final double WHITE_TAPE_REFLECTIVITY_MAX = 0.02;
    private static final double DISTANCE_OFFSET = 2;

    private OpticalDistanceSensor ods;
    private ModernRoboticsI2cRangeSensor range_sensor;

    /**
     * expands upon the EncoderDrive functionality by adding
     * support for ths Optical Distance Sensor. This allows
     * for driving the robot until it senses a certain color
     * beneath it and allows for certain actions to be taken
     * under such conditions.
     *
     * @param hardwareMap     the robot's current hardwareMap
     * @param linearOpMode    a linearOpMode object
     * @param robotParameters our custom RobotParameters Class
     *
     * @see EncoderDrive
     * @see org.pattonvillerobotics.opmodes.CustomRobotParameters
     * @see HardwareMap
     * @see LinearOpMode
     * @see OpticalDistanceSensor
     * @see ModernRoboticsI2cRangeSensor
     */
    public LineFollowerDrive(HardwareMap hardwareMap, LinearOpMode linearOpMode, RobotParameters robotParameters) {
        super(hardwareMap, linearOpMode, robotParameters);

        ods = hardwareMap.opticalDistanceSensor.get("ods");

        range_sensor = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "range_sensor");

    }

    /**
     * drives forward until the the ODS sensor finds
     * the white tape line that signifies the center
     * of the beacon
     *
     * @param direction the direction to travel
     * @param power the motor power to drive at
     *
     * @see LineFollowerDrive#foundLine()
     * @see Direction
     */
    public void driveUntilLine(Direction direction, double power) {

        leftDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        move(direction, power);
        while (!foundLine() && opmodeReady()) {
            Thread.yield();
        }
        stop();

    }

    /**
     * drive robot forwards until it is a defined distance
     * from an object (usually the wall). Used in autonomous
     * to stop robot before it crashes into wall when hitting
     * the beacon.
     *
     * @param distance the distance we want to be from a certain object
     *
     * @see LineFollowerDrive#DISTANCE_OFFSET
     */
    public void driveUntilDistance(double distance, double power) {

        Direction direction;
        double buffer = 0.5;
        double currentDistance = range_sensor.getDistance(DistanceUnit.INCH);

        leftDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        if(currentDistance < distance){
            direction = Direction.BACKWARD;
        }else{
            direction = Direction.FORWARD;
        }

        move(direction, power);
        while (!rangeSensorInRange(distance, buffer) && opmodeReady()) {
            Thread.yield();
        }

        stop();
    }

    /**
     * turns until the ODS sensor detects the white line, signifying
     * that the robot is perpendicular (or very near so) to the field
     * wall
     *
     * @param direction the direction to turn
     * @param power the power to turn with
     */
    public void turnUntilLine(Direction direction, double power){

        leftDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        turn(direction, power);
        while(!foundLine() && opmodeReady()){
            Thread.yield();
        }
        stop();

    }

    /**
     * adjusts the robot's position on field manually to account for the
     * position of the ODS sensor versus the turning axis of the robot
     *
     * @param direction the direction to drive
     * @param distance the distance to drive
     * @param power the power to drive with
     */
    public void adjustDistance(Direction direction, double distance, double power){
        moveInches(direction, distance, power);
        stop();
    }

    /**
     * adjusts the robot's position on the field manually to account for the
     * the width of the tape line versus the turning axis of teh robot
     *
     * @param direction the direction to turn
     * @param angle the angle to turn at
     * @param power the power to turn with
     */
    public void adjustTurn(Direction direction, double angle, double power){
        rotateDegrees(direction, angle, power);
        stop();
    }

    /**
     * Determine whether or not the ods sensor is locating a
     * white colored tape (the white tape line).
     *
     * @return boolean dictating whether or not the sensor is
     * detecting the white line
     *
     * @see LineFollowerDrive#WHITE_TAPE_REFLECTIVITY_MIN
     * @see LineFollowerDrive#WHITE_TAPE_REFLECTIVITY_MAX
     */
    private boolean foundLine() {
        telemetry("ODS: ", String.valueOf(ods.getRawLightDetected()));
        return inRange(ods.getRawLightDetected(), WHITE_TAPE_REFLECTIVITY_MAX, WHITE_TAPE_REFLECTIVITY_MIN);
    }

    /**
     * checks if the range sensor distance reading is within the specified bounds
     *
     * @param distance the distance we want to be away from an object
     * @param buffer the size of our buffer zone
     * @return whether or not the sensor is within the given range
     *
     * @see LineFollowerDrive#inRange(double, double, double)
     * @see ModernRoboticsI2cRangeSensor#getDistance(DistanceUnit)
     */
    private boolean rangeSensorInRange(double distance, double buffer){
        return inRange(range_sensor.getDistance(DistanceUnit.INCH), (distance - DISTANCE_OFFSET) + buffer, (distance - DISTANCE_OFFSET) - buffer);
    }

    /**
     * checks to see if the supplied value is between an upper and
     * lower limit
     *
     * @param value the value being analyzed
     * @param upperBound the upper limit to check
     * @param lowerBound the lower limit to check
     * @return whether or not the value is less than the upperBound
     *         and greater than the lowerBound
     */
    private boolean inRange(double value, double upperBound, double lowerBound){
        return value <= upperBound && value >= lowerBound;
    }

    /**
     * determines if the opmode is both active and not requesting to stop
     * @return whether ot not the opmode is active and not requesting stop
     *
     * @see LinearOpMode
     * @see LinearOpMode#opModeIsActive()
     * @see LinearOpMode#isStopRequested()
     */
    private boolean opmodeReady(){
        return linearOpMode.opModeIsActive()  && !linearOpMode.isStopRequested();
    }

}

