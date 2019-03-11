
package org.firstinspires.ftc.teamcode;

import android.hardware.camera2.CameraDevice;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

// lands, then gets gold, then deploys marker, then goes to same crater
@Autonomous(name="AutoOP", group="Linear Opmode")
public class JankBot_AutoOP extends LinearOpMode
{
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    private static final String VUFORIA_KEY = "AdcmR0H/////AAABmX5FCuasR0WJva" +
            "W+WfKBZZY4Mkmr39mE5zeNXTSqAfbDNYf8W53AS1tK2Fgrwh1kN/CVmyZnbLlKxrn" +
            "I9kDObs2/9mddaFBkwb7TCKrCo4Cy/kqF85YWtWYekU3EqHzuMNvw1OonPzQJ7kjgB" +
            "hszai3nQKg5bN2dd8NBPFpScozkSlvIUrWFvbj20K1K7kQ3JjgMreeSNldB12C+Zeee" +
            "Ui9IVDBqfkcBOszPh0HvSMBGX3IkIkac56/UcTFbaa/GNWTwZtrVshjnypXm15Y1d62E" +
            "hg9G8wNHFMwhvzHqfGvLA++K7x/dCtB+iaPE7WNxom4RaE+UYJ8kDjbpjrbtiXPuqUN9+SbCwQK6IN86vAer";

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    private Arm arm;
    private DriveBase base;

    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

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

        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector())
        {
            initTfod();
        } 
        else 
        {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        telemetry.addData("Ready!", true);
        waitForStart();

        /** Activate Tensor Flow Object Detection. */
        if (tfod != null) 
        {
            tfod.activate();
        }

//        arm.descend();

        base.setSpeed(0.5);

        waitToDrive(500);

        base.setRotSpeed(0.5);
        while (opModeIsActive())
        {
            List<Recognition> minerals = tfod.getUpdatedRecognitions();
            
            if (minerals != null)
            {
                Double goldPos = null;

                for (Recognition mineral : minerals)
                {
                    if (mineral.getLabel().equals(LABEL_GOLD_MINERAL))
                    {
                        goldPos = (double)mineral.getLeft();
                        break;
                    }
                }

                if (goldPos == null)
                {
                    continue;
                }

                if (!opModeIsActive())
                {
                    break;
                }

                telemetry.addData("goldPos:", goldPos);
                
                if (Math.abs(goldPos) < 0.5)
                {
                    break;
                } 
//                else if (goldPos > 0)
//                {
//                    base.setTargetHeading(base.getHeading() - 1);
//                }
//                else
//                {
//                    base.setTargetHeading(base.getHeading() + 1);
//                }
            }

            base.update();
        }

        base.setTargetHeading(base.getHeading());
        base.setSpeed(0.5);

//        waitToDrive(1000);

//        base.setSpeed(0);

//        arm.intake.setPosition(1000);

//        arm.intake.run();

//        waitToDrive(1000);

//        arm.intake.setPosition(0);

//        waitToDrive(500);

//        arm.intake.stop();

//        base.setSpeed(0.5);
        //base.setTargetHeading(-45);

        waitToDrive(3000);

        base.setSpeed(0);

        arm.intake.setTiltPosition(500);
    }

    public void waitToDrive(int wait)
    {
        long start = System.currentTimeMillis();

        while(System.currentTimeMillis() - start <= wait)
        {
            base.update();
        }
    }

    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);


        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    /**
     * Initialize the Tensor Flow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
            "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }
}
