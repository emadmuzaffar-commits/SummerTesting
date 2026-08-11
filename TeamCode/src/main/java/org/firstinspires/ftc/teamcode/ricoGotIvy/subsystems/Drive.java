package org.firstinspires.ftc.teamcode.ricoGotIvy.subsystems;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.ricoGotIvy.util.runIt.internal.CallTime;
import org.firstinspires.ftc.teamcode.ricoGotIvy.util.runIt.external.RI;
import org.firstinspires.ftc.teamcode.ricoGotIvy.util.runIt.external.RunIt;

public class Drive {
    static Follower follower;
    @RunIt(callTime = CallTime.INIT)
    public static void initDrive() {
        follower = RI.g(Follower.class);
        follower.startTeleOpDrive();
    }
    public static void loop(Gamepad gamepad) {
        follower.setTeleOpDrive(gamepad.left_stick_x, gamepad.left_stick_y, gamepad.right_stick_x);
    }

}
