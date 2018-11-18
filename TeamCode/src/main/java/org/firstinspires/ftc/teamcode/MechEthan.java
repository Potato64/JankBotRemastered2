package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

public class MechEthan implements MechOperator
{
    //TODO find actual limits
    private final int LIFT_LOWER_LIMIT = 0;
    private final int LIFT_UPPER_LIMIT = 500;

    //TODO find actual limits
    private final int EXTEND_LOWER_LIMIT = 0;
    private final int EXTEND_UPPER_LIMIT = 1000;

    private Gamepad gamepad;

    private boolean yTracker;
    private boolean isIntakeDeployed;

    public MechEthan(Gamepad gamepad)
    {
        this.gamepad = gamepad;

        yTracker = false;
        isIntakeDeployed = true;
    }

    @Override
    public int liftPosition()
    {
        return (int)(-gamepad.left_stick_y * LIFT_UPPER_LIMIT);
    }

    @Override
    public int extendPosition()
    {
        return (int)(-gamepad.right_stick_y * LIFT_UPPER_LIMIT);
    }

    @Override
    public boolean runIntake()
    {
        return gamepad.b;
    }

    @Override
    public boolean reverseIntake()
    {
        return false;
    }

    @Override
    public int tiltIntakePosition()
    {
        return 0;
    }
}
