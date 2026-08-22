package org.firstinspires.ftc.teamcode.runItDev;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.runItDev.subsystems.Drive;
import org.firstinspires.ftc.teamcode.runItDev.util.runIt.external.RI;

public class TestOpMode extends OpMode {
    static {RI.go("org.firstinspires.ftc.teamcode.ricoGotIvy");}

    boolean bBoolean = false;
    boolean aBoolean = false;
    boolean yBoolean = false;

    @Override
    public void init() {
        //RunIt handles pretty much everything
    }

    @Override
    public void loop() {
        //TODO: write and implement control system v1

        //control logic
        if (gamepad1.b) {
            bBoolean = true;
            Commands.shoot(1000).schedule();
        } else if (!gamepad1.b && bBoolean) {
            bBoolean = false;
        }

        if (gamepad1.a) {
            aBoolean = true;
            Commands.transferCommand.schedule();
        } else if (!gamepad1.x && aBoolean) {
            aBoolean = false;
        }

        if (gamepad1.y) {
            yBoolean = true;
            Commands.transferCommand.schedule();
        } else if (!gamepad1.y && yBoolean) {
            yBoolean = false;
        }

        //RunIt automatically offers Follower as a RunIt subsystem
        //RunIt allows Drive.loop to be static by offering static wrapped access subsystem instances
        Drive.loop(gamepad1);
    }
}
