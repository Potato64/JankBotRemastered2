package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import static java.lang.Thread.sleep;

public class Latch {

    private final double openLimit = 0;
    private final double closedLimit = 0.5;

    private Servo release;

    public Latch(Servo release)
    {
        this.release = release;
        this.release.scaleRange(openLimit, closedLimit);

    }

    public void release()
    {
        release.setPosition(0);
    }

    public void unrelease()
    {
        release.setPosition(1);
    }
}
