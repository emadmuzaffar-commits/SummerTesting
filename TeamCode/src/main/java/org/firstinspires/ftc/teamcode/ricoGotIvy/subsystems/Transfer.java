package org.firstinspires.ftc.teamcode.ricoGotIvy.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.ricoGotIvy.util.runIt.external.Call;
import org.firstinspires.ftc.teamcode.ricoGotIvy.util.runIt.external.RunIt;

public class Transfer {
    final DcMotorEx transfer;

    @RunIt(callTime = Call.SUBSYSTEM)
    private Transfer(HardwareMap hardwareMap) {
        transfer = hardwareMap.get(DcMotorEx.class, "transfer");
    }

    public void run() {
        transfer.setPower(1);
    }

    public void back() {
        transfer.setPower(-1);
    }

    @RunIt(callTime = Call.STOP)
    public void stop() {
        transfer.setPower(0);
    }
}
