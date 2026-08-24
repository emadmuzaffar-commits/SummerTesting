package org.firstinspires.ftc.teamcode.runItDev;

import com.pedropathing.ivy.Scheduler;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.runItDev.subsystems.Drive;
import org.firstinspires.ftc.teamcode.runItDev.subsystems.Switch;
import org.firstinspires.ftc.teamcode.runItDev.util.runIt.external.RI;

@TeleOp
public class TestOpMode extends OpMode {
    static {RI.go("org.firstinspires.ftc.teamcode.runItDev", TestOpMode.class);}

    boolean switchBoolean = false;
    boolean aBoolean = false;
    boolean yBoolean = false;
    Switch aSwitch;

    @Override
    public void init() {
        //runIt handles pretty much everything
        aSwitch = RI.g(Switch.class); //Example of class retrieval through runIt
    }

    @Override
    public void loop() {
        //TODO: write and implement control system v1

        //RunIt automatically offers Follower as a RunIt subsystem
        //RunIt allows Drive.loop to be static by offering static wrapped access subsystem instances
        Drive.loop(gamepad1);

        //control logic
        if (aSwitch.isPressed() && !switchBoolean) {
            switchBoolean = true;
            if (Scheduler.isRunning(Commands.shoot)) Commands.shoot.cancel();
            else Commands.shoot.schedule();
        } else if (!aSwitch.isPressed() && switchBoolean) {
            switchBoolean = false;
        }

        if (gamepad1.a && !aBoolean) {
            aBoolean = true;
            if (Scheduler.isRunning(Commands.transferCommand)) Commands.transferCommand.cancel();
            else Commands.transferCommand.schedule();
        } else if (!gamepad1.a && aBoolean) {
            aBoolean = false;
        }

        if (gamepad1.y && !yBoolean) {
            yBoolean = true;
            if (Scheduler.isRunning(Commands.transferBackCommand)) Commands.transferBackCommand.cancel();
            else Commands.transferBackCommand.schedule();
        } else if (!gamepad1.y && yBoolean) {
            yBoolean = false;
        }
    }
}
