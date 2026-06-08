package org.firstinspires.ftc.teamcode;


import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Limelight {
    @Configurable
    public static class llConfig {
        public static int pipeline = 0;
        public static double kR = 0.2;
    }
    private final Limelight3A ll;
    private final TelemetryManager telemetryM;
    public Limelight(HardwareMap hardwareMap) {
        ll = hardwareMap.get(Limelight3A.class, "ll");
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
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
        return tx * llConfig.kR;
    }

    /// Main init method.
    /// Sets the pipeline to llConfig.pipeline and starts ll.
    public void init() {
        ll.pipelineSwitch(llConfig.pipeline);
        ll.start();
    }

    /// Main loop method
    public void loop(Follower follower, Gamepad gamepad1) {
        //get the tx from ll
        double tx = getTx();
        //apply rotation multiplier
        double r = getRotation(tx);
        //setTeleopDrive
        follower.setTeleOpDrive(gamepad1.left_stick_x, gamepad1.left_stick_y, r);
    }

}
