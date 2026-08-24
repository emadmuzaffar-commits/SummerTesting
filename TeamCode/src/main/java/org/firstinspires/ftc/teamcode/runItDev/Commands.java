package org.firstinspires.ftc.teamcode.runItDev;

import com.pedropathing.ivy.Command;

import org.firstinspires.ftc.teamcode.runItDev.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.runItDev.subsystems.Transfer;
import org.firstinspires.ftc.teamcode.runItDev.util.runIt.external.Call;
import org.firstinspires.ftc.teamcode.runItDev.util.runIt.external.RI;
import org.firstinspires.ftc.teamcode.runItDev.util.runIt.external.RunIt;

public class Commands {
    static Shooter shooter;
    static Transfer transfer;

    @RunIt(callTime = Call.INIT)
    public static void CommandInit() {
        shooter = RI.g(Shooter.class);
        transfer = RI.g(Transfer.class);
    }

    public static Command shoot = Command.build()
                .setStart(() -> shooter.shoot(0.75))
                .setDone(() -> shooter.checkTimer())
                .setEnd(endCondition -> shooter.stop())
                .requiring(shooter);

    public static final Command transferCommand = Command.build()
            .setStart(() -> transfer.run())
            .setEnd(endCondition -> transfer.stop())
            .requiring(Transfer.class)
            .setPriority(1);

    public static final Command transferBackCommand = Command.build()
            .setStart(() -> transfer.back())
            .setEnd(endCondition -> transfer.stop())
            .requiring(Transfer.class)
            .setPriority(transferCommand.priority() - 1);

}
