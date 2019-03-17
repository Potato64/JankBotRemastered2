package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import static java.lang.Thread.sleep;
import static org.firstinspires.ftc.teamcode.Constants.LEFT_LATCH_LOWER_LIMIT;
import static org.firstinspires.ftc.teamcode.Constants.LEFT_LATCH_MID;
import static org.firstinspires.ftc.teamcode.Constants.LEFT_LATCH_UPPER_LIMIT;
import static org.firstinspires.ftc.teamcode.Constants.RIGHT_LATCH_LOWER_LIMIT;
import static org.firstinspires.ftc.teamcode.Constants.RIGHT_LATCH_UPPER_LIMIT;

/**
 * controls the latches
 */
public class Latch {

    private Servo leftRelease;
    private Servo rightRelease;

    public Latch(Servo leftRelease, Servo rightRelease)
    {
        this.leftRelease = leftRelease;
        this.rightRelease = rightRelease;

        rightRelease.setDirection(Servo.Direction.REVERSE);
    }

    public void release()
    {
        leftRelease.setPosition(LEFT_LATCH_LOWER_LIMIT);
        rightRelease.setPosition(RIGHT_LATCH_LOWER_LIMIT);
    }

    public void unrelease()
    {
        leftRelease.setPosition(LEFT_LATCH_UPPER_LIMIT);
        rightRelease.setPosition(RIGHT_LATCH_UPPER_LIMIT);
    }

    public boolean isReleased()
    {
        return leftRelease.getPosition() < LEFT_LATCH_MID;
    }
}
