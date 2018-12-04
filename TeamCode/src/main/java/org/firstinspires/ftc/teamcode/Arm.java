package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

import static java.lang.Thread.sleep;
import static org.firstinspires.ftc.teamcode.Constants.CLIMB_LOWER_LIMIT;
import static org.firstinspires.ftc.teamcode.Constants.CLIMB_UPPER_LIMIT;
import static org.firstinspires.ftc.teamcode.Constants.EXTEND_LOWER_LIMIT;
import static org.firstinspires.ftc.teamcode.Constants.EXTEND_UPPER_LIMIT;
import static org.firstinspires.ftc.teamcode.Constants.LIFT_LOWER_LIMIT;
import static org.firstinspires.ftc.teamcode.Constants.LIFT_UPPER_LIMIT;

public class Arm {

    public boolean climbed;

//    private boolean isZeroed = false;

    private DcMotor lift;
    private DcMotor extend;

//    private DigitalChannel liftLimitSwitch;
//    private DigitalChannel extendLimitSwitch;

    public Intake intake;
    public Latch latch;

//    public Thread zeroThread;
//    private boolean cancel = false;

    public Arm (DcMotor lift, DcMotor extend, DigitalChannel liftLimitSwitch, DigitalChannel extendLimitSwitch,
                CRServo leftWheelIntake, CRServo rightWheelIntake, DcMotor tiltIntake,
                Servo latchRelease)
    {
        this.lift = lift;
        this.lift.setDirection(DcMotorSimple.Direction.REVERSE);
        this.lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        this.extend = extend;
        this.extend.setDirection(DcMotorSimple.Direction.REVERSE);
        this.extend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.extend.setMode(DcMotor.RunMode.RUN_TO_POSITION);

//        this.liftLimitSwitch = liftLimitSwitch;
//        this.extendLimitSwitch = extendLimitSwitch;

        intake = new Intake(leftWheelIntake, rightWheelIntake, tiltIntake);
        latch = new Latch(latchRelease);

        climbed = false;
    }

    public void setLiftMode(DcMotor.RunMode mode)
    {
        lift.setMode(mode);
    }

    public void setLiftPower(double power)
    {
        int currentPosition = lift.getCurrentPosition();
        if (power >= 0 && currentPosition <= LIFT_UPPER_LIMIT ||
                power <= 0 && currentPosition >= LIFT_LOWER_LIMIT)// &&
//                isZeroed)
        {
            lift.setPower(power);
        }
    }

    public void setLiftPosition(int position)
    {
        if (position >= LIFT_LOWER_LIMIT && position <= LIFT_UPPER_LIMIT)// && isZeroed)
        {
            lift.setTargetPosition(position);
        }
    }

    public int getLiftPostition()
    {
        return lift.getCurrentPosition();
    }

    public void setExtendMode(DcMotor.RunMode mode)
    {
        extend.setMode(mode);
    }

    public void setExtendPower(double power)
    {
        int currentPosition = extend.getCurrentPosition();
        if (power >= 0 && currentPosition <= EXTEND_UPPER_LIMIT ||
                power <= 0 && currentPosition >= EXTEND_LOWER_LIMIT)// &&
//                isZeroed)
        {
            extend.setPower(power);
        }
    }

    public void setExtendPostion(int position)
    {
        if (position >= EXTEND_LOWER_LIMIT && position <= EXTEND_UPPER_LIMIT)// && isZeroed)
        {
            extend.setTargetPosition(position);
        }
    }

    public int getExtendPostion()
    {
        return extend.getCurrentPosition();
    }

    public void liftToScore()
    {
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setTargetPosition(LIFT_UPPER_LIMIT);
        lift.setPower(0.5);
    }

    public void ascend()
    {
        climbed = true;

        lift.setTargetPosition(CLIMB_UPPER_LIMIT);
        try
        {
            sleep(5000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        latch.unrelease();

        try
        {
            sleep(1000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        lift.setTargetPosition(CLIMB_LOWER_LIMIT);

        try
        {
            sleep(3000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public void descend()
    {
        lift.setTargetPosition(LIFT_UPPER_LIMIT);
        try
        {
            sleep(3000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        latch.release();
        try
        {
            sleep(500);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

//    public void zero()
//    {
//        //this creates its own thread so that it can be stopped with the OpMode
//        Runnable zeroRunnable = new Runnable() {
//            @Override
//            public void run()
//            {
//                {
//                    boolean liftLimit;
//                    boolean extendLimit;
//
//                    do
//                    {
//                        liftLimit = liftLimitSwitch.getState();
//                        extendLimit = extendLimitSwitch.getState();
//
//                        if (!liftLimit)
//                        {
//                            lift.setPower(-0.5);
//                        }
//                        else
//                        {
//                            lift.setPower(0);
//                        }
//
//                        if (extendLimit)
//                        {
//                            extend.setPower(-0.5);
//                        }
//                        else
//                        {
//                            extend.setPower(0);
//                        }
//
//                        if (cancel)
//                        {
//                            break;
//                        }
//                    } while (!liftLimit || !extendLimit);
//
//                    if (!cancel)
//                    {
//                        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//                        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//
//                        extend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//                        extend.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//
//                        isZeroed = true;
//                    }
//                }
//            }
//        };
//
//        zeroThread = new Thread(zeroRunnable);
//        zeroThread.start();
//    }
//
//    public void cancelZero()
//    {
//        cancel = true;
//    }
}
