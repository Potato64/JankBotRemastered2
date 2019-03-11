package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="TestServo", group="Iterative Opmode")
public class TestServo extends OpMode
{

    Servo servo;

    @Override
    public void init()
    {
        servo = hardwareMap.get(Servo.class, "rightRelease");
    }

    @Override
    public void loop()
    {
        servo.setPosition(gamepad1.left_trigger);
        telemetry.addData("pos:", servo.getPosition());
    }
}
