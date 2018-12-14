package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import static java.lang.Thread.sleep;
import static org.firstinspires.ftc.teamcode.Constants.LATCH_LOWER_LIMIT;
import static org.firstinspires.ftc.teamcode.Constants.LATCH_UPPER_LIMIT;

public class Latch {

    private Servo release;

    public Latch(Servo release)
    {
        this.release = release;
        this.release.scaleRange(LATCH_LOWER_LIMIT, LATCH_UPPER_LIMIT);

    }

    public void release()
    {
        release.setPosition(0);
    }

    public void unrelease()
    {
        release.setPosition(1);
    }

    public boolean isReleased()
    {
        return release.getPosition() > 0.5;
    }
}
