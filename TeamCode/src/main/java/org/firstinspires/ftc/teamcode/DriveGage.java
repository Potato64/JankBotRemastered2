package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

import static java.lang.Math.PI;
import static java.lang.Math.atan;

public class DriveGage implements DriveOperator
{
    private Gamepad gamepad;

    public DriveGage(Gamepad gamepad)
    {
        this.gamepad = gamepad;
    }

    @Override
    public double speed()
    {
        return Math.sqrt(gamepad.left_stick_y * gamepad.left_stick_y + gamepad.left_stick_x * gamepad.left_stick_x);
    }

    @Override
    public double rotSpeed()
    {
        return gamepad.right_stick_x;
    }

    @Override
    public double direction()
    {
        if (gamepad.left_stick_x == 0)
        {
            return gamepad.left_stick_y < 0 ?
                    PI / 2 :
                    3 * PI / 2;
        }
        else
        {
            return (gamepad.left_stick_x > 0) ?
                    atan(-gamepad.left_stick_y / gamepad.left_stick_x) :
                    atan(-gamepad.left_stick_y / gamepad.left_stick_x) + PI;
        }
    }
}
