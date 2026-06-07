package org.firstinspires.ftc.teamcode;


import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Limelight {
    @Configurable
    public static class llConfig {
        public static int pipeline = 0;
    }
    private final Limelight3A ll;
    private final TelemetryManager telemetryM;
    public Limelight(HardwareMap hardwareMap) {
        ll = hardwareMap.get(Limelight3A.class, "ll");
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
    }

    /// Main init method.
    /// Sets the pipeline to llConfig.pipeline and starts ll.
    public void init() {
        ll.pipelineSwitch(llConfig.pipeline);
        ll.start();
    }

    /// Method to detect an AprilTag. Prints to panels telemetry
    public void aprilTagID() {

    }


}
