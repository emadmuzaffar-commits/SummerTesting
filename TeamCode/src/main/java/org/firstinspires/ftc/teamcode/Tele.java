package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class Tele extends OpMode {
    private TagTracker tagTracker;

    @Override
    public void init() {
        tagTracker = new TagTracker(hardwareMap);
        tagTracker.follower.startTeleopDrive();
        tagTracker.init();
    }

    @Override
    public void loop() {
        tagTracker.follower.update();
        tagTracker.trackTag(gamepad1);
    }
}
