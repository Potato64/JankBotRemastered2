package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import static java.lang.Thread.sleep;
import static org.firstinspires.ftc.teamcode.Constants.LATCH_LOWER_LIMIT;
import static org.firstinspires.ftc.teamcode.Constants.LATCH_MID;
import static org.firstinspires.ftc.teamcode.Constants.LATCH_UPPER_LIMIT;

public class Latch {

    private Servo release;

    public Latch(Servo release)
    {
        this.release = release;
    }

    public void release()
    {
        release.setPosition(LATCH_LOWER_LIMIT);
    }

    public void unrelease()
    {
        release.setPosition(LATCH_UPPER_LIMIT);
    }

    public boolean isReleased()
    {
        return release.getPosition() < LATCH_MID;
    }
}
