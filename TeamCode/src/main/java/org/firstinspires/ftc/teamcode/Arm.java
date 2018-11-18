package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

import static java.lang.Thread.sleep;

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
        this.lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        this.extend = extend;
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
        if (power >= 0 && currentPosition <= Constants.LIFT_UPPER_LIMIT ||
                power <= 0 && currentPosition >= Constants.LIFT_LOWER_LIMIT)// &&
//                isZeroed)
        {
            lift.setPower(power);
        }
    }

    public void setLiftPosition(int position)
    {
        if (position >= Constants.LIFT_LOWER_LIMIT && position <= Constants.LIFT_UPPER_LIMIT)// && isZeroed)
        {
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setTargetPosition(position);
        }
    }

    public void setExtendMode(DcMotor.RunMode mode)
    {
        extend.setMode(mode);
    }

    public void setExtendPower(double power)
    {
        int currentPosition = extend.getCurrentPosition();
        if (power >= 0 && currentPosition <= Constants.EXTEND_UPPER_LIMIT ||
                power <= 0 && currentPosition >= Constants.EXTEND_LOWER_LIMIT)// &&
//                isZeroed)
        {
            extend.setPower(power);
        }
    }

    public void setExtendPostion(int position)
    {
        if (position >= Constants.EXTEND_LOWER_LIMIT && position <= Constants.EXTEND_UPPER_LIMIT)// && isZeroed)
        {
            extend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            extend.setTargetPosition(position);
        }
    }

    public void liftToScore()
    {
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setTargetPosition(Constants.LIFT_UPPER_LIMIT);
        lift.setPower(0.5);
    }

    public void ascend()
    {
        climbed = true;

        //motors are floated so they don't interfere with ascent
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        extend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        latch.unrelease();
        try
        {
            sleep(500);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        lift.setTargetPosition(Constants.LIFT_LOWER_LIMIT);

        try
        {
            sleep(3000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        extend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void descend()
    {
        //motors are floated so they don't interfere with descent
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        extend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        lift.setTargetPosition(Constants.LIFT_UPPER_LIMIT);
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

        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        extend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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
