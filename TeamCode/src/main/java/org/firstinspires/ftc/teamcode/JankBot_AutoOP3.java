
package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

//starts facing crater, knocks off center mineral, goes to blue crater
@Autonomous(name="AutoOP3", group="Linear Opmode")
public class JankBot_AutoOP3 extends LinearOpMode
{
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    private Arm arm;
    private DriveBase base;

    private PixyCam pixy;

    @Override
    public void runOpMode()
    {

        arm = new Arm(hardwareMap.get(DcMotor.class, "lift"), hardwareMap.get(DcMotor.class, "extend"),
                hardwareMap.get(DigitalChannel.class, "liftLimit"), hardwareMap.get(DigitalChannel.class, "extendLimit"),
                hardwareMap.get(CRServo.class, "leftWheelIntake"), hardwareMap.get(CRServo.class, "rightWheelIntake"),
                hardwareMap.get(DcMotor.class, "tiltIntake"),
                hardwareMap.get(Servo.class, "releaseLatch"));

        base = new DriveBase(hardwareMap.get(DcMotor.class, "left1"), hardwareMap.get(DcMotor.class, "left1"),
                hardwareMap.get(DcMotor.class, "right1"), hardwareMap.get(DcMotor.class, "right2"),
                hardwareMap.get(BNO055IMU.class, "imu"));

        pixy = new PixyCam(hardwareMap.get(I2cDeviceSynch.class, "pixy"));

        waitForStart();

        base.setSpeed(0.5);

        waitToDrive(5000);
    }

    public void wait(int wait)
    {
        long start = System.currentTimeMillis();

        while(System.currentTimeMillis() - start <= wait);
    }

    public void waitToDrive(int wait)
    {
        long start = System.currentTimeMillis();

        while(System.currentTimeMillis() - start <= wait)
        {
            base.update();
        }
    }
}
