package org.firstinspires.ftc.teamcode;


import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class TagTracker extends TestingBase {
    @Configurable
    public static class tagTrackerConfig {
        public static int pipeline = 0;
        public static double kR = 0.2;
    }

    public TagTracker(HardwareMap hardwareMap) {
        super(hardwareMap);
    }

    /// Loop method to detect an AprilTag and return Tx. Prints status to panels telemetry
    private double getTx() {
        double tx = ll.getLatestResult().getTx();
        if (tx != 0) {
            telemetryM.addData("status", "detected");
        }
        return tx;
    }

    private double getRotation(double tx) {
        return tx * tagTrackerConfig.kR;
    }

    /// Main init method.
    /// Sets the pipeline to tagTrackerConfig.pipeline and starts ll.
    public void init() {
        ll.pipelineSwitch(tagTrackerConfig.pipeline);
        ll.start();
    }

    /// Main trackTag method. Sets teleop drive to be x,y controlled by gamepad with rotation to track tag.
    /// Start teleop drive must be called before this
    public void trackTag(Gamepad gamepad1) {
        //get the tx from ll
        double tx = getTx();
        //apply rotation multiplier
        double r = getRotation(tx);
        //setTeleopDrive
        follower.setTeleOpDrive(gamepad1.left_stick_x, gamepad1.left_stick_y, r);
    }

}
