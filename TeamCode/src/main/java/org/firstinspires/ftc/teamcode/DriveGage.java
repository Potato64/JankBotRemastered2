package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

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
        return (gamepad.left_stick_x > 0) ?
            -Math.atan(-gamepad.left_stick_y / gamepad.left_stick_x) :
            Math.atan(-gamepad.left_stick_y / gamepad.left_stick_x);
}
