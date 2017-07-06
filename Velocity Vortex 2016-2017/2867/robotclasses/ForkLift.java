package org.pattonvillerobotics.robotclasses;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by joshua on 1/31/17.
 */

public class ForkLift {

    public DcMotor leftWinch;
    public DcMotor rightWinch;

    private Servo leftForkStop;
    private Servo rightForkStop;

    private HardwareMap hardwareMap;
    private LinearOpMode linearOpMode;

    public ForkLift(HardwareMap hardwareMap, LinearOpMode linearOpMode){

        this.hardwareMap = hardwareMap;
        this.linearOpMode = linearOpMode;

        leftWinch = hardwareMap.dcMotor.get("left_winch");
        rightWinch = hardwareMap.dcMotor.get("right_winch");

        leftForkStop = hardwareMap.servo.get("left_fork_stop");
        rightForkStop = hardwareMap.servo.get("right_fork_stop");

        leftForkStop.setPosition(0.0);
        rightForkStop.setPosition(1.0);

        leftWinch.setDirection(DcMotorSimple.Direction.FORWARD);
        rightWinch.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    public void raiseSlides(){
        runMotors(1.0, 0.9);
    }

    public void lowerSlides(){
        runMotors(-1.0, -0.9);
    }

    public void stopSlides(){
        runMotors(0);
        linearOpMode.sleep(100);
    }

    public void releaseForks(){

        leftForkStop.setPosition(0.35);
        rightForkStop.setPosition(0.5);

    }

    private void runMotors(double leftPower, double rightPower){
        leftWinch.setPower(leftPower);
        rightWinch.setPower(rightPower);
    }


    private void runMotors(double power){
        runMotors(power, power);
    }

}
