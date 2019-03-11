
package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

//lands, starts facing crater, knocks off center mineral, goes to same crater
@Autonomous(name="AutoOP2", group="Linear Opmode")
public class JankBot_AutoOP2 extends LinearOpMode
{
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    private Arm arm;
    private DriveBase base;

    @Override
    public void runOpMode()
    {

        arm = new Arm(hardwareMap.get(DcMotor.class, "lift"), hardwareMap.get(DcMotor.class, "extend"),
                hardwareMap.get(DigitalChannel.class, "liftLimit"), hardwareMap.get(DigitalChannel.class, "extendLimit"),
                hardwareMap.get(CRServo.class, "leftWheelIntake"), hardwareMap.get(CRServo.class, "rightWheelIntake"),
                hardwareMap.get(DcMotor.class, "tiltIntake"),
                hardwareMap.get(Servo.class, "leftRelease"), hardwareMap.get(Servo.class, "rightRelease"));

        base = new DriveBase(hardwareMap.get(DcMotor.class, "left1"), hardwareMap.get(DcMotor.class, "left1"),
                hardwareMap.get(DcMotor.class, "right1"), hardwareMap.get(DcMotor.class, "right2"),
                hardwareMap.get(BNO055IMU.class, "imu"));

        waitForStart();

        arm.setLiftMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setLiftMode(DcMotor.RunMode.RUN_TO_POSITION);

//        arm.descend();

        base.setSpeed(0.5);

        waitToDrive(4000);

        base.setSpeed(0);

        base.updateTeleOp();

        arm.intake.setTiltPosition(1000);
        arm.intake.setTiltPower(0.5);

        wait(1000);
    }

    public void wait(int wait)
    {
        long start = System.currentTimeMillis();

        while(System.currentTimeMillis() - start <= wait && opModeIsActive());
    }

    public void waitToDrive(int wait)
    {
        long start = System.currentTimeMillis();

        while(System.currentTimeMillis() - start <= wait && opModeIsActive())
        {
            base.updateTeleOp();
        }
    }
}
