package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

public class DrivePaige implements DriveOperator
{
    private Gamepad gamepad;

    public DrivePaige(Gamepad gamepad)
    {
        this.gamepad = gamepad;
    }

    @Override
    public double speed()
    {
        return -gamepad.left_stick_y;
    }

    @Override double direction()
    {
        return 0;
    }

    @Override
    public double rotSpeed()
    {
        return gamepad.right_stick_x;
    }
}
