package org.firstinspires.ftc.teamcode;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

public abstract class TestingBase {
    public final Follower follower;
    public final Limelight3A ll;
    public final TelemetryManager telemetryM;
    public TestingBase(HardwareMap hardwareMap) {
        ll = hardwareMap.get(Limelight3A.class, "ll");
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
        follower = Constants.createFollower(hardwareMap);
    }
}
