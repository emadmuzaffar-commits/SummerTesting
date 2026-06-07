package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class Test extends OpMode {

    private final Limelight limelight = new Limelight(hardwareMap)
;    @Override
    public void init() {
        limelight.init();
    }

    @Override
    public void loop() {
        limelight.aprilTagID();
    }
}
