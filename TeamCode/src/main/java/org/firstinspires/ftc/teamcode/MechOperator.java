package org.firstinspires.ftc.teamcode;

//interface for changing mech operators.
public interface MechOperator
{
    int liftPosition();

    int extendPosition();

    boolean runIntake();

    boolean reverseIntake();

    boolean toggleLatch();

    int tiltIntakePosition();

    void giveLiftPos(int pos);

    void giveExtendPos(int pos);

    void giveTiltPos(int pos);
}
