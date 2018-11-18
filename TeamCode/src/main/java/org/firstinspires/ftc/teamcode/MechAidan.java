package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

public class MechAidan implements MechOperator
{

    Gamepad gamepad;

    public MechAidan(Gamepad gamepad)
    {
        this.gamepad = gamepad;
    }

    @Override
    public int liftPosition()
    {
        return (int)(gamepad.left_trigger * Constants.LIFT_UPPER_LIMIT);
    }

    @Override
    public int extendPosition()
    {
//        return (int)(gamepad.right_trigger * Constants.EXTEND_UPPER_LIMIT);
        return (int)(-gamepad.left_stick_y  * Constants.EXTEND_UPPER_LIMIT / 2 + Constants.EXTEND_UPPER_LIMIT / 2);
    }

    @Override
    public boolean runIntake()
    {
        return gamepad.y;
    }

    @Override
    public boolean reverseIntake()
    {
        return gamepad.x;
    }

    @Override
    public int tiltIntakePosition()
    {
        return (int)(-gamepad.right_stick_y  * Constants.TILT_UPPER_LIMIT / 2 + Constants.TILT_UPPER_LIMIT / 2);
    }

}
