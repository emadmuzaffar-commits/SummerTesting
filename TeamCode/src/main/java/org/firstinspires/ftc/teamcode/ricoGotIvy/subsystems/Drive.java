package org.firstinspires.ftc.teamcode.ricoGotIvy.subsystems;

import com.pedropathing.follower.Follower;
import com.pedropathing.ftc.FollowerBuilder;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.ricoGotIvy.util.RunIt.CallTime;
import org.firstinspires.ftc.teamcode.ricoGotIvy.util.RunIt.RI;
import org.firstinspires.ftc.teamcode.ricoGotIvy.util.RunIt.RunIt;

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
