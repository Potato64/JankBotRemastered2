
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

@TeleOp(name="TestMotor", group="Iterative Opmode")
public class JankBot_TestMotor extends OpMode
{
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    private Arm arm;

    DcMotor motor;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init()
    {
        motor = hardwareMap.get(DcMotor.class, "lift");

//        arm = new Arm(hardwareMap.get(DcMotor.class, "lift"), hardwareMap.get(DcMotor.class, "extend"),
//                hardwareMap.get(DigitalChannel.class, "liftLimit"), hardwareMap.get(DigitalChannel.class, "extendLimit"),
//                hardwareMap.get(CRServo.class, "leftWheelIntake"), hardwareMap.get(CRServo.class, "rightWheelIntake"),
//                hardwareMap.get(DcMotor.class, "tiltIntake"),
//                hardwareMap.get(Servo.class, "releaseLatch"));

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
        runtime.reset();
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

//        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motor.setPower(0.5);
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop()
    {
        motor.setTargetPosition((int)(-gamepad1.left_stick_y * 3000));
//        motor.setPower(-gamepad1.left_stick_y);

        telemetry.addData("Ticks: ", motor.getCurrentPosition());
    }
}
