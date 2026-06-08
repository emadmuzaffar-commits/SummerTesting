package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@TeleOp
public class Tele extends OpMode {
    private Follower follower;
    private TagTracker limelight;

    @Override
    public void init() {
        limelight = new TagTracker(hardwareMap);
        follower = Constants.createFollower(hardwareMap);
        follower.startTeleopDrive();
        follower.update();
        limelight.init();
    }

    @Override
    public void loop() {
        follower.update();
        limelight.loop(follower, gamepad1);
    }
}
