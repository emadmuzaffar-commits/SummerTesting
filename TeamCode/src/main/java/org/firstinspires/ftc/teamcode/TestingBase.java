package org.firstinspires.ftc.teamcode;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

public abstract class TestingBase {
    public final Limelight3A ll;
    public final TelemetryManager telemetryM;
    public TestingBase(HardwareMap hardwareMap) {
        ll = hardwareMap.get(Limelight3A.class, "ll");
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
    }
}
