package org.pattonvillerobotics.robotclasses;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by wrightk03 on 10/6/16.
 */

public class ButtonPresser {

    private static final double LEFT_POSITION = 0.25;
    private static final double MIDDLE_POSITION = 0.5;
    private static final double RIGHT_POSITION = 0.8;

    public Servo buttonPresser;

    public ButtonPresser(HardwareMap hardwaremap){
        buttonPresser = hardwaremap.servo.get("button_presser");
        setPosition(MIDDLE_POSITION);
    }

    /**
     * extends the button presser to its left position of 0.2
     * @see 'LEFT_POSITION'.
     */
    public void presserLeft(){
        setPosition(LEFT_POSITION);
    }

    /**
     * extends the button presser to its right position of 0.8
     * @see 'RIGHT_POSITION'.
     */
    public void presserRight(){
        setPosition(RIGHT_POSITION);
    }

    /**
     * sets the position for the button presser servo.
     *
     * @param position the position to set the servo too
     */
    public void setPosition(double position){
        buttonPresser.setPosition(position);
    }

}

