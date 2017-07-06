package org.pattonvillerobotics.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.pattonvillerobotics.commoncode.robotclasses.drive.SimpleDrive;
import org.pattonvillerobotics.opmodes.CustomRobotParameters;
import org.pattonvillerobotics.robotclasses.BigWheel;
import org.pattonvillerobotics.robotclasses.ButtonPresser;
import org.pattonvillerobotics.robotclasses.ForkLift;
import org.pattonvillerobotics.robotclasses.GuideRail;
import org.pattonvillerobotics.robotclasses.LineFollowerDrive;

/**
 * Created by Joshua Zahner on 10/11/16.
 *<p>
 * The TeleOp Class gives the driver control over the robot.
 * <p>
 * The driver can also use the following controls on the
 * gamepad: A, B, X, Y, right trigger, left trigger,
 * start, up d-pad, down d-pad, left stick, and right stick.
 */
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp", group = "Teleop")
public class TeleOp extends LinearOpMode {

    //private SimpleDrive drive;
    private LineFollowerDrive drive;
    private ButtonPresser buttonPresser;
    private BigWheel wheel;
    private GuideRail guideRail;
    private ForkLift lift;

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();

        while (opModeIsActive()) {
            doLoop();
        }
    }

    /**
     * This method initializes the robot and defines what drive is,
     * defines what the button presser is, defines the motor directions,
     * and defines the gamepad controls that are sticky.
     */
    public void initialize() {
        //drive = new SimpleDrive(this, hardwareMap);

        drive = new LineFollowerDrive(hardwareMap, this, CustomRobotParameters.TELEOP_ROBOT_PARAMETERS);
        drive.leftDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        drive.rightDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        buttonPresser = new ButtonPresser(hardwareMap);

        wheel = new BigWheel(hardwareMap, this);

        guideRail = new GuideRail(hardwareMap, this);

        lift = new ForkLift(hardwareMap, this);

        gamepad1.left_stick_y = 0;
        gamepad1.right_stick_y = 0;

    }

    /**
     * This method allows the driver to control the robot with the gamepad.
     *
     * If the driver presses x, the button presser will extend. If the driver
     * presses b, the button presser will return to the default position.
     * Using the left and right sticks allow the driver to turn the robot
     * and move it forward and backward.
     */
    public void doLoop() {

        //**************** DRIVE TRAIN CONTROLS ****************\\

        drive.moveFreely(gamepad1.left_stick_y, gamepad1.right_stick_y);

        //**************** BUTTON PRESSER CONTROLS ****************\\

        if (gamepad1.x) {
            buttonPresser.presserLeft();
        }else if (gamepad1.b) {
            buttonPresser.presserRight();
        }

        //**************** BIG WHEEL CONTROLS ****************\\

        if(gamepad1.y){
            wheel.fire();
        }

        if(gamepad1.left_trigger > 0 && gamepad1.right_trigger == 0){
            wheel.move(-gamepad1.left_trigger/8);
        }else if(gamepad1.right_trigger > 0 && gamepad1.left_trigger == 0){
            wheel.move(gamepad1.right_trigger/8);
        }else{
            wheel.stop();
        }

        //**************** GUIDE RAIL CONTROLS ****************\\

        double guideRailCurrentPosition = guideRail.guideRail.getPosition();

        if(gamepad1.dpad_up){
            guideRail.setPosition(guideRailCurrentPosition + 0.05);
        }else if(gamepad1.dpad_down){
            guideRail.setPosition(guideRailCurrentPosition - 0.05);
        }

        //**************** LINEAR SLIDES CONTROLS ****************\\

        if(!gamepad1.atRest() && gamepad2.atRest()){

            if(gamepad1.left_bumper){
                lift.lowerSlides();
            }else if(gamepad1.right_bumper){
                lift.raiseSlides();
            }else{
                lift.stopSlides();
            }

            if(gamepad1.guide){
                lift.releaseForks();
            }

        }else if(!gamepad2.atRest() && gamepad1.atRest()){

            double rightStickValue = gamepad2.right_stick_y;
            double leftStickValue = gamepad2.left_stick_y;

            lift.rightWinch.setPower(rightStickValue);
            lift.leftWinch.setPower(leftStickValue);

            if(gamepad2.guide){
                lift.releaseForks();
            }

        }else{
            lift.stopSlides();
        }


        //**************** EXTRANEOUS CONTROLS ****************\\
        if(gamepad1.start){
            killAll();
        }

        telemetry.update();

    }

    private void killAll(){
        drive.stop();
        wheel.stop();
        lift.stopSlides();
        buttonPresser.setPosition(0.5);
        guideRail.setPosition(1.0);
    }
}
