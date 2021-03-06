package org.pattonvillerobotics.robotclasses;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.Range;

import org.apache.commons.math3.util.FastMath;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.BeaconColorSensor;
import org.pattonvillerobotics.commoncode.robotclasses.colordetection.BeaconColorDetection;
import org.pattonvillerobotics.commoncode.robotclasses.drive.AbstractComplexDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.trailblazer.vuforia.VuforiaNav;
import org.pattonvillerobotics.commoncode.vision.ftc.resq.Beacon;
import org.pattonvillerobotics.commoncode.vision.util.ScreenOrientation;

import java.util.Arrays;

/**
 * Created by skaggsm on 11/15/16.
 */

public final class CommonAutonomous {

    public static final double
            WALL_TO_BALL_DISTANCE = 56,
            TILE_SIZE = 22,
            COS_45_I = 1 / FastMath.cos(FastMath.toRadians(45)),
            BACKUP_DISTANCE = 12,
            ROBOT_CENTER_OFFSET = 6.5,//7.6,
            ROTATE_SPEED = .2,
            MOVE_SPEED = .5;
    private static final String TAG = "CommonAutonomous";

    @Deprecated
    public static void tile1ToBeacon1(VuforiaNav vuforiaNav, BeaconColorDetection beaconColorDetection, BeaconPresser beaconPresser, AbstractComplexDrive drive, LinearOpMode linearOpMode, AllianceColor allianceColor) {
        tile1ToBeacon1(vuforiaNav, beaconColorDetection, beaconPresser, drive, linearOpMode, allianceColor, 0L);
    }

    @Deprecated
    public static void tile1ToBeacon1TEST(VuforiaNav vuforiaNav, BeaconColorDetection beaconColorDetection, BeaconPresser beaconPresser, AbstractComplexDrive drive, LinearOpMode linearOpMode, AllianceColor allianceColor, long delayMS) {
        if (delayMS > 0)
            linearOpMode.sleep(delayMS);


        Direction direction1 = null, direction2 = null;

        switch (allianceColor) {
            case RED:
                direction1 = Direction.LEFT;
                direction2 = Direction.LEFT;
                break;
            case BLUE:
                direction1 = Direction.RIGHT;
                direction2 = Direction.RIGHT;
                break;
        }

        drive.moveInches(Direction.FORWARD, 22, .5);
        drive.rotateDegrees(direction1, 45, 0.25);
        drive.moveInches(Direction.FORWARD, COS_45_I * 22, .5);
        drive.rotateDegrees(direction2, 45, 0.25);
        //drive.moveInches(Direction.FORWARD, 10, .5);

        beaconPresser.setLeftServoUp();
        beaconPresser.setRightServoUp();
        vuforiaNav.activate();

        linearOpMode.sleep(1000);

        beaconColorDetection.setAnalysisMethod(Beacon.AnalysisMethod.REALTIME);
        Beacon.BeaconAnalysis beaconAnalysis = beaconColorDetection.analyzeFrame(vuforiaNav.getImage(), ScreenOrientation.PORTRAIT_REVERSE);
        Telemetry.Item analysisTelemetry = linearOpMode.telemetry.addData("Analysis", beaconAnalysis.toString()).setRetained(true);
        linearOpMode.telemetry.update();

        linearOpMode.sleep(1000);

        if ((beaconAnalysis.isLeftRed() && allianceColor == AllianceColor.RED) ||
                (beaconAnalysis.isLeftBlue() && allianceColor == AllianceColor.BLUE)) {
            linearOpMode.telemetry.addData("CA", "Lowering left arm").setRetained(true);
            beaconPresser.setLeftServoDown();
        }

        if ((beaconAnalysis.isRightRed() && allianceColor == AllianceColor.RED) ||
                (beaconAnalysis.isRightBlue() && allianceColor == AllianceColor.BLUE)) {
            linearOpMode.telemetry.addData("CA", "Lowering right arm").setRetained(true);
            beaconPresser.setRightServoDown();
        }

        linearOpMode.sleep(1000);

        float[] location;
        Telemetry.Item locationTelemetry = linearOpMode.telemetry.addData("Location", "N/A").setRetained(true);
        Telemetry.Item powerTelemetry = linearOpMode.telemetry.addData("Power", "N/A").setRetained(true);
        linearOpMode.telemetry.update();
        final double approachSpeed = .05;

        while (linearOpMode.opModeIsActive()) {
            vuforiaNav.getNearestBeaconLocation();
            location = vuforiaNav.getLocation();
            locationTelemetry.setValue(Arrays.toString(location));

            double distanceToBeacon = location[1] / VuforiaNav.MM_PER_INCH, distanceFromCenter = location[0] / VuforiaNav.MM_PER_INCH;

            if (FastMath.abs(distanceToBeacon) < 4)
                break;

            if (distanceToBeacon > 14) {
                beaconAnalysis = beaconColorDetection.analyzeFrame(vuforiaNav.getImage(), ScreenOrientation.PORTRAIT_REVERSE);
                if ((beaconAnalysis.isLeftRed() && allianceColor == AllianceColor.RED) ||
                        (beaconAnalysis.isLeftBlue() && allianceColor == AllianceColor.BLUE)) {
                    beaconPresser.setLeftServoDown();
                } else {
                    beaconPresser.setLeftServoUp();
                }

                if ((beaconAnalysis.isRightRed() && allianceColor == AllianceColor.RED) ||
                        (beaconAnalysis.isRightBlue() && allianceColor == AllianceColor.BLUE)) {
                    beaconPresser.setRightServoDown();
                } else {
                    beaconPresser.setRightServoUp();
                }
                analysisTelemetry.setValue(beaconAnalysis.toString());
            }

            Orientation orientation = Orientation.getOrientation(vuforiaNav.getLastLocation(), AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

            double correction = (-distanceFromCenter / 75) + (orientation.thirdAngle / 90);
            Log.e("ANGLE", "Angle: " + orientation.thirdAngle);

            double leftPower = approachSpeed + correction;
            double rightPower = approachSpeed - correction;

            powerTelemetry.setValue("Left: " + leftPower + " Right: " + rightPower);

            drive.moveFreely(leftPower, rightPower);
            linearOpMode.telemetry.update();
        }


        drive.stop();
        vuforiaNav.deactivate();

        linearOpMode.sleep(3000);
    }

    @Deprecated
    public static void tile1ToBeacon1(VuforiaNav vuforiaNav, BeaconColorDetection beaconColorDetection, BeaconPresser beaconPresser, AbstractComplexDrive drive, LinearOpMode linearOpMode, AllianceColor allianceColor, long delayMS) {
        if (delayMS > 0)
            linearOpMode.sleep(delayMS);


        Direction direction1 = null, direction2 = null;

        switch (allianceColor) {
            case RED:
                direction1 = Direction.LEFT;
                direction2 = Direction.LEFT;
                break;
            case BLUE:
                direction1 = Direction.RIGHT;
                direction2 = Direction.RIGHT;
                break;
        }

        drive.moveInches(Direction.FORWARD, 22, .5);
        drive.rotateDegrees(direction1, 45, 0.25);
        drive.moveInches(Direction.FORWARD, COS_45_I * 22, .5);
        drive.rotateDegrees(direction2, 45, 0.25);
        drive.moveInches(Direction.FORWARD, 10, .5);

        beaconPresser.setLeftServoUp();
        beaconPresser.setRightServoUp();
        vuforiaNav.activate();

        linearOpMode.sleep(1000);

        beaconColorDetection.setAnalysisMethod(Beacon.AnalysisMethod.COMPLEX);
        Beacon.BeaconAnalysis beaconAnalysis = beaconColorDetection.analyzeFrame(vuforiaNav.getImage(), ScreenOrientation.PORTRAIT_REVERSE);
        linearOpMode.telemetry.update();

        linearOpMode.sleep(1000);

        if ((beaconAnalysis.isLeftRed() && allianceColor == AllianceColor.RED) ||
                (beaconAnalysis.isLeftBlue() && allianceColor == AllianceColor.BLUE)) {
            linearOpMode.telemetry.addData("CA", "Lowering left arm").setRetained(true);
            beaconPresser.setLeftServoDown();
        }

        if ((beaconAnalysis.isRightRed() && allianceColor == AllianceColor.RED) ||
                (beaconAnalysis.isRightBlue() && allianceColor == AllianceColor.BLUE)) {
            linearOpMode.telemetry.addData("CA", "Lowering right arm").setRetained(true);
            beaconPresser.setRightServoDown();
        }

        linearOpMode.sleep(1000);

        float[] location;
        Telemetry.Item locationTelemetry = linearOpMode.telemetry.addData("Location", "N/A").setRetained(true);
        Telemetry.Item powerTelemetry = linearOpMode.telemetry.addData("Power", "N/A").setRetained(true);
        linearOpMode.telemetry.update();
        final double approachSpeed = .1;

        while (linearOpMode.opModeIsActive()) {
            vuforiaNav.getNearestBeaconLocation();
            location = vuforiaNav.getLocation();
            locationTelemetry.setValue(Arrays.toString(location));

            double distanceToBeacon = location[1] / VuforiaNav.MM_PER_INCH, distanceFromCenter = location[0] / VuforiaNav.MM_PER_INCH;

            if (FastMath.abs(distanceToBeacon) < 4)
                break;

            Orientation orientation = Orientation.getOrientation(vuforiaNav.getLastLocation(), AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

            double correction = (-distanceFromCenter / 75) + (orientation.thirdAngle / 90);
            Log.e("ANGLE", "Angle: " + orientation.thirdAngle);

            double leftPower = approachSpeed + correction;
            double rightPower = approachSpeed - correction;

            powerTelemetry.setValue("Left: " + leftPower + " Right: " + rightPower);

            drive.moveFreely(leftPower, rightPower);
            linearOpMode.telemetry.update();
        }


        drive.stop();
        vuforiaNav.deactivate();

        linearOpMode.sleep(3000);
    }

    public static void tile1ToBall(AbstractComplexDrive drive, LinearOpMode linearOpMode, AllianceColor allianceColor) {
        tile1ToBall(drive, linearOpMode, allianceColor, 0L);
    }

    public static void tile1ToBall(AbstractComplexDrive drive, LinearOpMode linearOpMode, AllianceColor allianceColor, long delayMS) {
        if (delayMS > 0)
            linearOpMode.sleep(delayMS);

        Direction direction1 = null, direction2 = null;
        final double diagonalDistance = 16 * COS_45_I, straightDistance = 5;

        switch (allianceColor) {
            case RED:
                direction1 = Direction.RIGHT;
                direction2 = Direction.LEFT;
                break;
            case BLUE:
                direction1 = Direction.LEFT;
                direction2 = Direction.RIGHT;
                break;
        }
        drive.moveInches(Direction.FORWARD, straightDistance, .5);
        drive.rotateDegrees(direction1, 45, .25);
        drive.moveInches(Direction.FORWARD, diagonalDistance, .5);
        drive.rotateDegrees(direction2, 45, .25);
        drive.moveInches(Direction.FORWARD, WALL_TO_BALL_DISTANCE - straightDistance - diagonalDistance, .75);
        drive.moveInches(Direction.BACKWARD, BACKUP_DISTANCE, .75);
    }

    public static void tile2ToBall(AbstractComplexDrive drive, LinearOpMode linearOpMode, AllianceColor allianceColor) {
        tile2ToBall(drive, linearOpMode, allianceColor, 0L);
    }

    public static void tile2ToBall(AbstractComplexDrive drive, LinearOpMode linearOpMode, AllianceColor allianceColor, long delayMS) {

        if (delayMS > 0)
            linearOpMode.sleep(delayMS);

        Direction direction1 = null, direction2 = null;
        final double diagonalDistance = 10 * COS_45_I, straightDistance = 5;

        switch (allianceColor) {
            case RED:
                direction1 = Direction.LEFT;
                direction2 = Direction.RIGHT;
                break;
            case BLUE:
                direction1 = Direction.RIGHT;
                direction2 = Direction.LEFT;
                break;
        }
        drive.moveInches(Direction.FORWARD, straightDistance, MOVE_SPEED);
        drive.rotateDegrees(direction1, 45, ROTATE_SPEED);
        drive.moveInches(Direction.FORWARD, diagonalDistance, MOVE_SPEED);
        drive.rotateDegrees(direction2, 45, ROTATE_SPEED);
        drive.moveInches(Direction.FORWARD, WALL_TO_BALL_DISTANCE - straightDistance - diagonalDistance - 8, MOVE_SPEED + .25);
        drive.moveInches(Direction.BACKWARD, BACKUP_DISTANCE, MOVE_SPEED + .25);
    }

    public static void tile3ToBall(AbstractComplexDrive drive, LinearOpMode linearOpMode, AllianceColor allianceColor) {
        tile3ToBall(drive, linearOpMode, allianceColor, 0L);
    }

    public static void tile3ToBall(AbstractComplexDrive drive, LinearOpMode linearOpMode, AllianceColor allianceColor, long delayMS) {
        if (delayMS > 0)
            linearOpMode.sleep(delayMS);

        Direction direction1 = null, direction2 = null;
        final double diagonalDistance = (4 + TILE_SIZE) * 2 * COS_45_I, straightDistance = 5;

        switch (allianceColor) {
            case RED:
                direction1 = Direction.LEFT;
                direction2 = Direction.RIGHT;
                break;
            case BLUE:
                direction1 = Direction.RIGHT;
                direction2 = Direction.LEFT;
                break;
        }
        drive.moveInches(Direction.FORWARD, straightDistance, MOVE_SPEED);
        drive.rotateDegrees(direction1, 45, ROTATE_SPEED);
        drive.moveInches(Direction.FORWARD, diagonalDistance, MOVE_SPEED);
        drive.rotateDegrees(direction2, 45, ROTATE_SPEED);
        drive.moveInches(Direction.FORWARD, WALL_TO_BALL_DISTANCE - straightDistance - diagonalDistance, MOVE_SPEED + .25);
        drive.moveInches(Direction.BACKWARD, BACKUP_DISTANCE, MOVE_SPEED + .25);
    }

    public static void tile1ToMidpoint(AbstractComplexDrive drive, LinearOpMode linearOpMode, AllianceColor allianceColor) {
        tile1ToMidpoint(drive, linearOpMode, allianceColor, 0L);
    }

    public static void midpointToBeacon2(AbstractComplexDrive drive, LinearOpMode linearOpMode, AllianceColor allianceColor) {
        midpointToBeacon2(drive, linearOpMode, allianceColor, 0L);
    }

    public static void approachBeaconVuforia(VuforiaNav vuforiaNav, BeaconColorSensor beaconColorSensor, BeaconPresser beaconPresser, AbstractComplexDrive drive, LinearOpMode linearOpMode, AllianceColor allianceColor) {
        approachBeaconVuforia(vuforiaNav, beaconColorSensor, beaconPresser, drive, linearOpMode, allianceColor, 0);
    }

    public static void approachBeacon(BeaconColorSensor beaconColorSensor, BeaconPresser beaconPresser, AbstractComplexDrive drive, LinearOpMode linearOpMode, AllianceColor allianceColor) {
        approachBeacon(beaconColorSensor, beaconPresser, drive, linearOpMode, allianceColor, 0);
    }

    public static void approachBeacon(BeaconColorSensor beaconColorSensor, final BeaconPresser beaconPresser, AbstractComplexDrive drive, LinearOpMode linearOpMode, AllianceColor allianceColor, long delayMS) {
        if (delayMS > 0)
            linearOpMode.sleep(delayMS);

        drive.moveInches(Direction.FORWARD, 15, MOVE_SPEED);

        beaconPresser.setLeftServoDown();
        beaconPresser.setRightServoDown();

        drive.sleep(250);

        beaconColorSensor.determineColor(allianceColor, new Runnable() {
            @Override
            public void run() { // Positive
                beaconPresser.setLeftServoUp();
                Log.i(TAG, "Beacon matched");
            }
        }, new Runnable() {
            @Override
            public void run() { // Negative
                beaconPresser.setRightServoUp();
                Log.i(TAG, "Beacon failed");
            }
        }, new Runnable() {
            @Override
            public void run() { // Neither
                Log.i(TAG, "Beacon not found!");
                //beaconPresser.setLeftServoUp();
                //beaconPresser.setRightServoUp();
            }
        });

        drive.sleep(250);

        drive.moveInches(Direction.FORWARD, 5, MOVE_SPEED);
        //drive.moveInches(Direction.BACKWARD, 1, MOVE_SPEED);
        //drive.moveInches(Direction.FORWARD, 1, MOVE_SPEED);
        drive.moveInches(Direction.BACKWARD, 19, MOVE_SPEED);

        beaconPresser.setLeftServoDown();
        beaconPresser.setRightServoDown();
    }

    public static void approachBeaconVuforia(VuforiaNav vuforiaNav, BeaconColorSensor beaconColorSensor, BeaconPresser beaconPresser, AbstractComplexDrive drive, LinearOpMode linearOpMode, AllianceColor allianceColor, long delayMS) {
        if (delayMS > 0)
            linearOpMode.sleep(delayMS);

        drive.moveInches(Direction.FORWARD, 10, MOVE_SPEED);

        beaconPresser.setLeftServoDown();
        beaconPresser.setRightServoDown();
        if (!vuforiaNav.isActivated())
            vuforiaNav.activate();

        //linearOpMode.sleep(1000);

        linearOpMode.telemetry.update();

        //linearOpMode.sleep(1000);

        //linearOpMode.sleep(1000);

        float[] location;
        Telemetry.Item locationTelemetry = linearOpMode.telemetry.addData("Location", "N/A").setRetained(true);
        Telemetry.Item powerTelemetry = linearOpMode.telemetry.addData("Power", "N/A").setRetained(true);
        linearOpMode.telemetry.update();
        final double approachSpeed = .1;

        int iteration = 0;
        long startTime = System.currentTimeMillis();
        while (linearOpMode.opModeIsActive() && (System.currentTimeMillis() - startTime) < 7500) {
            vuforiaNav.getNearestBeaconLocation();
            location = vuforiaNav.getLocation();
            locationTelemetry.setValue(Arrays.toString(location));

            if (location != null) {
                double distanceToBeacon = location[1] / VuforiaNav.MM_PER_INCH, distanceFromCenter = location[0] / VuforiaNav.MM_PER_INCH;

                if (FastMath.abs(distanceToBeacon) < 4.75)
                    break;

                Orientation orientation = Orientation.getOrientation(vuforiaNav.getLastLocation(), AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

                double signedSqrt = FastMath.signum(distanceFromCenter) * FastMath.sqrt(FastMath.abs(distanceFromCenter));
                //double targetAngle = Range.clip(distanceFromCenter / 2, -5, 5);
                double currentAngle = orientation.thirdAngle;
                double correction = -signedSqrt / 30 + FastMath.pow(currentAngle, 1 / 3d) / 40;//(currentAngle - targetAngle) / 50; //-(distanceFromCenter / 30) + (orientation.thirdAngle / 120);
                if (iteration++ % 50 == 0) {
                    Log.e("EXP", "Distance: " + distanceFromCenter);
                    //Log.e("EXP", "Target: " + targetAngle);
                    Log.e("EXP", "Correction: " + correction);
                    Log.e("EXP", "Remaining: " + distanceToBeacon);
                }

                correction *= 1 + FastMath.abs(distanceToBeacon) / 15;

                double range = 2 / FastMath.abs(distanceToBeacon);
                correction = Range.clip(correction, -range, range);

                if (Double.isNaN(correction))
                    correction = 0;

                double leftPower = approachSpeed + correction;
                double rightPower = approachSpeed - correction;

                powerTelemetry.setValue("Left: " + leftPower + " Right: " + rightPower);

                drive.moveFreely(leftPower, rightPower);
            }
            linearOpMode.telemetry.update();
        }

        drive.stop();

        drive.moveInches(Direction.BACKWARD, 37, MOVE_SPEED);
    }

    public static void midpointToBeacon2(AbstractComplexDrive drive, LinearOpMode linearOpMode, AllianceColor allianceColor, long delayMS) {
        if (delayMS > 0)
            linearOpMode.sleep(delayMS);

        Direction direction1 = null, direction2 = null;

        switch (allianceColor) {
            case RED:
                direction1 = Direction.RIGHT;
                direction2 = Direction.LEFT;
                break;
            case BLUE:
                direction1 = Direction.LEFT;
                direction2 = Direction.RIGHT;
                break;
        }

        drive.rotateDegrees(direction1, 90, ROTATE_SPEED);
        drive.moveInches(Direction.FORWARD, 41, MOVE_SPEED);
        drive.rotateDegrees(direction2, 90, ROTATE_SPEED);
    }

    public static void tile1ToMidpoint(AbstractComplexDrive drive, LinearOpMode linearOpMode, AllianceColor allianceColor, long delayMS) {
        if (delayMS > 0)
            linearOpMode.sleep(delayMS);

        Direction direction1 = null, direction2 = null;

        switch (allianceColor) {
            case RED:
                direction1 = Direction.LEFT;
                direction2 = Direction.LEFT;
                break;
            case BLUE:
                direction1 = Direction.RIGHT;
                direction2 = Direction.RIGHT;
                break;
        }

        drive.moveInches(Direction.FORWARD, 28 - ROBOT_CENTER_OFFSET, MOVE_SPEED);
        drive.rotateDegrees(direction1, 45, ROTATE_SPEED);
        drive.moveInches(Direction.FORWARD, 32.53, MOVE_SPEED);
        drive.rotateDegrees(direction2, 45, ROTATE_SPEED);
    }
}
