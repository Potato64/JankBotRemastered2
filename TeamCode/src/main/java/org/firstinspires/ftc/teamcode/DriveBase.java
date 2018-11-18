package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

public class DriveBase {

    private final double rotKP = 0.02;
//    private final double KI = 0.001;
//
//    private double accum = 0;

    private DcMotor left1;
    private DcMotor left2;
    private DcMotor right1;
    private DcMotor right2;

    private BNO055IMU imu;

    private double speed;
    private double targetHeading;
    private double rotSpeed;

    public DriveBase(DcMotor left1, DcMotor left2,
                DcMotor right1, DcMotor right2,
                BNO055IMU imu)
    {
        this.left1 = left1;
        this.left2 = left2;
        this.right1 = right1;
        this.right2 = right2;

        right1.setDirection(DcMotorSimple.Direction.REVERSE);
        right2.setDirection(DcMotorSimple.Direction.REVERSE);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";

        this.imu = imu;
        imu.initialize(parameters);
    }

    public double getHeading()
    {
        return imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
    }

    public void setMode(DcMotor.RunMode mode)
    {
        left1.setMode(mode);
        left2.setMode(mode);
        right1.setMode(mode);
        right2.setMode(mode);
    }

    public void setSpeed(double speed)
    {
        this.speed = speed;
    }

    public void setRotSpeed(double speed)
    {
        if (rotSpeed != 0 && speed == 0)
        {
            targetHeading = getHeading();
        }

        //does not actually change any sort of motor power directly,
        //but adjusts the target angle and lets the angle correction
        //of update() sort it out
        adjustHeading(speed);

        this.rotSpeed = speed;
    }

    public void setTargetHeading(double heading)
    {
        this.targetHeading = heading;
    }

    public void update()
    {
        //adjusts motor powers in order to maintain the correct heading
        double error = getHeading() - targetHeading;
        double powerChange = error * rotKP;// + error * KI;

        double leftPower = speed + powerChange;
        double rightPower = speed - powerChange;

        //keeps maximum power at or below 1, as to keep the proportions correct.
        double maxPower = Math.abs(speed) + Math.abs(powerChange);
        if (maxPower > 1)
        {
            leftPower /= maxPower;
            rightPower /= maxPower;
        }


        left1.setPower(leftPower);
        left2.setPower(leftPower);
        right1.setPower(rightPower);
        right2.setPower(rightPower);

        //if drivebase is supposed to keep rotating, adjust the target heading appropriately
        if (rotSpeed != 0)
        {
            adjustHeading(rotSpeed);
        }
    }

    public void updateTelOp()
    {
        double leftPower = speed + rotSpeed;
        double rightPower = speed - rotSpeed;

        double maxPower = Math.abs(speed) + Math.abs(rotSpeed);
        if (maxPower > 1)
        {
            leftPower /= maxPower;
            rightPower /= maxPower;
        }

        left1.setPower(leftPower);
        left2.setPower(leftPower);
        right1.setPower(rightPower);
        right2.setPower(rightPower);

        //if drivebase is supposed to keep rotating, adjust the target heading appropriately
        if (rotSpeed != 0)
        {
            adjustHeading(rotSpeed);
        }
    }

    private void adjustHeading(double speed)
    {
        targetHeading -= speed * 1 / rotKP;
    }
}