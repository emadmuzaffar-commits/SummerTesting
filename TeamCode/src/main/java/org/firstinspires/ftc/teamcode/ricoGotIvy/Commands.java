package org.firstinspires.ftc.teamcode.ricoGotIvy;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.ricoGotIvy.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.ricoGotIvy.subsystems.Transfer;
import org.firstinspires.ftc.teamcode.ricoGotIvy.util.CallTime;
import org.firstinspires.ftc.teamcode.ricoGotIvy.util.RI;
import org.firstinspires.ftc.teamcode.ricoGotIvy.util.RunIt;

public class Commands {
    static Shooter shooter;
    static Transfer transfer;

    private Commands() {
        shooter = RI.g(Shooter.class);
        transfer = RI.g(Transfer.class);
    }

    @RunIt(callTime = CallTime.INIT)
    public Commands CommandsWrapped() {
        return new Commands();
    }

    public static Command shoot(double target) {
        return Command.build()
                .setExecute(() -> shooter.shoot(target))
                .setDone(() -> shooter.checkTimer())
                .setEnd(endCondition -> shooter.stop())
                .requiring(shooter);
    }

    public static final Command transferCommand = Command.build()
            .setStart(() -> transfer.run())
            .setEnd(endCondition -> transfer.stop())
            .requiring(transfer);

}
