
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="TestPixy", group="Iterative Opmode")
public class TestPixy extends OpMode
{
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    private PixyCam pixyCam;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init()
    {
        telemetry.addData("Status", "Initialized");

        pixyCam = new PixyCam(hardwareMap.get(I2cDeviceSynch.class, "pixy"));
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop()
    {

    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start()
    {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop()
    {
        pixyCam.getObject();

        telemetry.addData("Block", pixyCam.blockList[0].toString());
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop()
    {

    }

}
