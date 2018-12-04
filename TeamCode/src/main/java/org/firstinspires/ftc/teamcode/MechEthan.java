package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

import static org.firstinspires.ftc.teamcode.Constants.CLIMB_LOWER_LIMIT;
import static org.firstinspires.ftc.teamcode.Constants.CLIMB_UPPER_LIMIT;
import static org.firstinspires.ftc.teamcode.Constants.EXTEND_LOWER_LIMIT;
import static org.firstinspires.ftc.teamcode.Constants.EXTEND_UPPER_LIMIT;
import static org.firstinspires.ftc.teamcode.Constants.LIFT_LOWER_LIMIT;
import static org.firstinspires.ftc.teamcode.Constants.LIFT_UPPER_LIMIT;
import static org.firstinspires.ftc.teamcode.Constants.TILT_UPPER_LIMIT;

public class MechEthan implements MechOperator
{
    private final int MAX_LIFT_INCREMENT = 500;
    private final int MAX_EXTEND_INCREMENT = 200;

    private int currentLiftPos;
    private int currentExtendPos;

    private boolean xTracker;

    private Gamepad gamepad;

    private ArmMode mode;

    public MechEthan(Gamepad gamepad)
    {
        this.gamepad = gamepad;

        mode = ArmMode.Lift;
        xTracker = false;
    }

    @Override
    public int liftPosition()
    {
        if (gamepad.left_stick_y > 0) //if lowering arm
        {
            if (mode == ArmMode.Lift && currentLiftPos > LIFT_LOWER_LIMIT || mode == ArmMode.Climb && currentLiftPos > CLIMB_LOWER_LIMIT)
            {
                return currentLiftPos - (int) (gamepad.left_stick_y * MAX_LIFT_INCREMENT);
            }
            else
            {
                return (mode == ArmMode.Lift) ? LIFT_LOWER_LIMIT : CLIMB_LOWER_LIMIT;
            }
        }
        else if (gamepad.left_stick_y < 0) //if raising arm
        {
            if (mode == ArmMode.Lift && currentLiftPos < LIFT_UPPER_LIMIT || mode == ArmMode.Climb && currentLiftPos < CLIMB_UPPER_LIMIT)
            {
                return currentLiftPos - (int) (gamepad.left_stick_y * MAX_LIFT_INCREMENT);
            }
            else
            {
                return (mode == ArmMode.Lift) ? LIFT_UPPER_LIMIT : CLIMB_UPPER_LIMIT;
            }
        }
        else //if stick not pushed
        {
            return currentLiftPos;
        }
    }

    @Override
    public int extendPosition()
    {
        if (gamepad.right_stick_y > 0) //if contracting arm
        {
            if (currentExtendPos > EXTEND_LOWER_LIMIT)
            {
                return currentExtendPos - (int) (gamepad.right_stick_y * MAX_EXTEND_INCREMENT);
            }
            else
            {
                return EXTEND_LOWER_LIMIT;
            }
        }
        else if (gamepad.right_stick_y < 0) //if extending arm
        {
            if (currentLiftPos < EXTEND_UPPER_LIMIT)
            {
                return currentExtendPos - (int) (gamepad.right_stick_y * MAX_EXTEND_INCREMENT);
            }
            else
            {
                return EXTEND_UPPER_LIMIT;
            }
        }
        else //if stick not pushed
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
    public boolean tiltResetEncoder()
    {
        return gamepad.left_bumper;
    }

    @Override
    public int tiltIntakePosition()
    {
        return (int)(gamepad.left_trigger  * TILT_UPPER_LIMIT);
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
    public void toggleArmMode()
    {
        if (!xTracker && gamepad.x)
        {
            mode = (mode == ArmMode.Lift) ? ArmMode.Climb : ArmMode.Lift;
        }
        xTracker = gamepad.x;
    }

    @Override
    public boolean climb()
    {
        return gamepad.y;
    }
}
