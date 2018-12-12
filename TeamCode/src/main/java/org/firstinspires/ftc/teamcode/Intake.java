package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Intake
{
    private CRServo leftRoller;
    private CRServo rightRoller;

    private DcMotor tilt;

    public Intake(CRServo leftRoller, CRServo rightRoller,
                  DcMotor tilt)
    {
        this.leftRoller = leftRoller;
        this.rightRoller = rightRoller;

        this.rightRoller.setDirection(CRServo.Direction.REVERSE);

        this.tilt = tilt;
        this.tilt.setDirection(DcMotor.Direction.REVERSE);
        this.tilt.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.tilt.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void run()
    {
        leftRoller.setPower(1);
        rightRoller.setPower(1);
    }

    public void reverse()
    {
        leftRoller.setPower(-1);
        rightRoller.setPower(-1);
    }

    public void stop()
    {
        leftRoller.setPower(0);
        rightRoller.setPower(0);
    }

    public void setTiltPosition(int pos)
    {
        if (pos >= Constants.TILT_LOWER_LIMIT && pos <= Constants.TILT_LOWER_LIMIT);
        {
            tilt.setTargetPosition(pos);
        }
    }

    public void setTiltPower(double power)
    {
        tilt.setPower(power);
    }

    public int getTiltPosition()
    {
        return tilt.getCurrentPosition();
    }

    public void resetTiltEncoder()
    {
        this.tilt.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.tilt.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}
