package org.firstinspires.ftc.teamcode;

public interface MechOperator
{
    int liftPosition();

    int extendPosition();

    boolean runIntake();

    boolean reverseIntake();

    boolean tiltResetEncoder();

    int tiltIntakePosition();

    void toggleArmMode();

    boolean climb();

    void giveLiftPos(int pos);

    void giveExtendPos(int pos);
}
