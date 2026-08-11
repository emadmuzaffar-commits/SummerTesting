package org.firstinspires.ftc.teamcode.ricoGotIvy;

import com.pedropathing.ivy.Command;

import org.firstinspires.ftc.teamcode.ricoGotIvy.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.ricoGotIvy.subsystems.Transfer;
import org.firstinspires.ftc.teamcode.ricoGotIvy.util.runIt.internal.CallTime;
import org.firstinspires.ftc.teamcode.ricoGotIvy.util.runIt.external.RI;
import org.firstinspires.ftc.teamcode.ricoGotIvy.util.runIt.external.RunIt;

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
            .requiring(transfer)
            .setPriority(1);

    public static final Command transferBackCommand = Command.build()
            .setStart(() -> transfer.back())
            .setEnd(endCondition -> transfer.stop())
            .requiring(transfer)
            .setPriority(transferCommand.priority() - 1);

}
