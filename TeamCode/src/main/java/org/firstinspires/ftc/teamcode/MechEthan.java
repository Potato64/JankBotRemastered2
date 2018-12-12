package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

import static org.firstinspires.ftc.teamcode.Constants.CLIMB_LOWER_LIMIT;
import static org.firstinspires.ftc.teamcode.Constants.CLIMB_UPPER_LIMIT;
import static org.firstinspires.ftc.teamcode.Constants.EXTEND_LOWER_LIMIT;
import static org.firstinspires.ftc.teamcode.Constants.EXTEND_UPPER_LIMIT;
import static org.firstinspires.ftc.teamcode.Constants.LIFT_LOWER_LIMIT;
import static org.firstinspires.ftc.teamcode.Constants.LIFT_UPPER_LIMIT;
import static org.firstinspires.ftc.teamcode.Constants.TILT_LOWER_LIMIT;
import static org.firstinspires.ftc.teamcode.Constants.TILT_UPPER_LIMIT;

public class MechEthan implements MechOperator
{
    private final int MAX_LIFT_INCREMENT = 500;
    private final int MAX_EXTEND_INCREMENT = 200;
    private final int MAX_TILT_INCREMENT = 50;

    private int currentLiftPos;
    private int currentExtendPos;

    private int currentTiltPos;

    private Gamepad gamepad;

    public MechEthan(Gamepad gamepad)
    {
        this.gamepad = gamepad;
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

    @Override
    public boolean climb()
    {
        return gamepad.y;
    }
}
