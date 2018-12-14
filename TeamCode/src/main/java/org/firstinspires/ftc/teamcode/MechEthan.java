package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

public class MechEthan implements MechOperator
{
    private final int MAX_LIFT_INCREMENT = 500;
    private final int MAX_EXTEND_INCREMENT = 200;
    private final int MAX_TILT_INCREMENT = 50;

    private int currentLiftPos;
    private int currentExtendPos;

    private int currentTiltPos;

    private boolean xTracker;

    private Gamepad gamepad;

    public MechEthan(Gamepad gamepad)
    {
        this.gamepad = gamepad;

        xTracker = false;
    }

    @Override
    public int liftPosition()
    {
        if (gamepad.left_stick_y != 0) //if moving arm
        {
            return currentLiftPos - (int) (gamepad.left_stick_y * MAX_LIFT_INCREMENT);
        }
        else //hold current position
        {
            return currentLiftPos;
        }
    }

    @Override
    public int extendPosition()
    {
        if (gamepad.right_stick_y != 0) //if contracting or expanding arm
        {
            return currentExtendPos - (int) (gamepad.right_stick_y * MAX_EXTEND_INCREMENT);
        }
        else //hold current extension
        {
            return currentExtendPos;
        }
    }

    @Override
    public boolean runIntake()
    {
        return gamepad.b;
    }

    public boolean reverseIntake()
    {
        return gamepad.a;
    }

    @Override
    public boolean toggleLatch()
    {
        if (gamepad.x && !xTracker)
        {
            return xTracker = true;
        }

        xTracker = gamepad.x;
        return false;
    }

    @Override
    public int tiltIntakePosition()
    {
        if (gamepad.left_trigger > 0) //if lowering intake
        {
            return currentTiltPos + (int)(gamepad.left_trigger * MAX_TILT_INCREMENT);
        }
        else if (gamepad.right_trigger > 0) //if lifting intake
        {
            return currentTiltPos - (int)(gamepad.right_trigger * MAX_TILT_INCREMENT);
        }
        else //hold intake position
        {
            return currentTiltPos;
        }
    }

    @Override
    public void giveLiftPos(int pos)
    {
        currentLiftPos = pos;
    }

    @Override
    public void giveExtendPos(int pos)
    {
        currentExtendPos = pos;
    }

    @Override
    public void giveTiltPos(int pos)
    {
        currentTiltPos = pos;
    }

}
