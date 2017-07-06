package org.pattonvillerobotics.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServoImpl;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


/**
 * Created by joshua on 3/4/17.
 */

@TeleOp(name = "CRServoTest", group = "Tests")
public class CRServoTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        CRServoImpl servo = new CRServoImpl(hardwareMap.servoController.get("Servo Controller 1"), 4);

        waitForStart();

        while(opModeIsActive()){

            if(gamepad1.a){
                servo.setDirection(DcMotorSimple.Direction.FORWARD);
                servo.setPower(1.0);
            }else if(gamepad1.b){
                servo.setDirection(DcMotorSimple.Direction.REVERSE);
                servo.setPower(1.0);
            }else{
                servo.setPower(0);
            }

        }


    }

}
