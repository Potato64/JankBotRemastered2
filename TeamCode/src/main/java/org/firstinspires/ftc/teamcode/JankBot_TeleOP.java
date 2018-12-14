
package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="TeleOP", group="Iterative Opmode")
public class JankBot_TeleOP extends OpMode
{
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    DriveOperator driveOperator;
    MechOperator mechOperator;

    private Arm arm;
    private DriveBase driveBase;

    private I2cDeviceSynch pixy;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init()
    {
        driveOperator = new DrivePaige(gamepad1);
        mechOperator = new MechEthan(gamepad2);

        arm = new Arm(hardwareMap.get(DcMotor.class, "lift"), hardwareMap.get(DcMotor.class, "extend"),
                hardwareMap.get(DigitalChannel.class, "liftLimit"), hardwareMap.get(DigitalChannel.class, "extendLimit"),
                hardwareMap.get(CRServo.class, "leftWheelIntake"), hardwareMap.get(CRServo.class, "rightWheelIntake"),
                hardwareMap.get(DcMotor.class, "tiltIntake"),
                hardwareMap.get(Servo.class, "releaseLatch"));

        driveBase = new DriveBase(hardwareMap.get(DcMotor.class, "left1"), hardwareMap.get(DcMotor.class, "left2"),
                hardwareMap.get(DcMotor.class, "right1"), hardwareMap.get(DcMotor.class, "right2"),
                hardwareMap.get(BNO055IMU.class, "imu"));

        telemetry.addData("Status", "Initialized");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start()
    {
//        arm.zero();
//        while(arm.zeroThread.isAlive());

        runtime.reset();
        arm.setLiftPower(1);
        arm.setExtendPower(1);

        arm.intake.setTiltPower(1);
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop()
    {
        if (arm.climbed)
        {
            return;
        }

        driveBase.setSpeed(driveOperator.speed());
        driveBase.setRotSpeed(driveOperator.rotSpeed());
        driveBase.updateTeleOp();

        mechOperator.giveLiftPos(arm.getLiftPostition());
        mechOperator.giveExtendPos(arm.getExtendPostion());
        mechOperator.giveTiltPos(arm.intake.getTiltPosition());

        arm.setLiftPosition(mechOperator.liftPosition());
        arm.setExtendPostion(mechOperator.extendPosition());

        arm.intake.setTiltPosition(mechOperator.tiltIntakePosition());

        if (mechOperator.toggleLatch())
        {
            if (arm.latch.isReleased())
            {
                arm.latch.unrelease();
            }
            else
            {
                arm.latch.release();
            }
        }

        if (mechOperator.runIntake())
        {
            arm.intake.run();
        }
        else if (mechOperator.reverseIntake())
        {
            arm.intake.reverse();
        }
        else
        {
            arm.intake.stop();
        }

        telemetry.addData("tiltPos", arm.intake.getTiltPosition());
        telemetry.addData("liftPos", arm.getLiftPostition());
        telemetry.addData("extendPos", arm.getExtendPostion());
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
//    @Override
//    public void stop() {
//        if (arm.zeroThread.isAlive())
//        {
//            arm.cancelZero();
//        }
//    }

}
