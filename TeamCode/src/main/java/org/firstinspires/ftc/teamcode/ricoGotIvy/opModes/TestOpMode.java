package org.firstinspires.ftc.teamcode.ricoGotIvy.opModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.ricoGotIvy.Commands;
import org.firstinspires.ftc.teamcode.ricoGotIvy.util.RI;

public class TestOpMode extends OpMode {
    static {RI.go("org.firstinspires.ftc.teamcode.ricoGotIvy");}

    boolean bBoolean = false;
    boolean xBoolean = false;

    @Override
    public void init() {
        //RunIt handles pretty much everything
    }

    @Override
    public void loop() {
        //TODO: write and implement control system v1
        if (gamepad1.b) {
            bBoolean = true;
            Commands.shoot(1000).schedule();
        } else if (!gamepad1.b && bBoolean) {
            bBoolean = false;
        }

        if (gamepad1.x) {
            xBoolean = true;
            Commands.transferCommand.schedule();
        } else if (!gamepad1.x && xBoolean) {
            xBoolean = false;
        }
    }
}
