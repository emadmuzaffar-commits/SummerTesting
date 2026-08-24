package org.firstinspires.ftc.teamcode.runItDev;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.runItDev.subsystems.Drive;
import org.firstinspires.ftc.teamcode.runItDev.util.runIt.external.RI;

@TeleOp
public class TestOpMode extends OpMode {
    static {RI.go("org.firstinspires.ftc.teamcode.runItDev", TestOpMode.class);}

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

        //RunIt automatically offers Follower as a RunIt subsystem
        //RunIt allows Drive.loop to be static by offering static wrapped access subsystem instances
        Drive.loop(gamepad1);

        //control logic
        if (gamepad1.b && !bBoolean) {
            bBoolean = true;
            Commands.shoot(1000).schedule();
        } else if (!gamepad1.b && bBoolean) {
            bBoolean = false;
        }

        if (gamepad1.a && !aBoolean) {
            aBoolean = true;
            Commands.transferCommand.schedule();
        } else if (!gamepad1.a && aBoolean) {
            aBoolean = false;
        }

        if (gamepad1.y && !yBoolean) {
            yBoolean = true;
            Commands.transferBackCommand.schedule();
        } else if (!gamepad1.y && yBoolean) {
            yBoolean = false;
        }
    }
}
