package org.firstinspires.ftc.teamcode.ricoGotIvy.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.ricoGotIvy.util.RunIt.CallTime;
import org.firstinspires.ftc.teamcode.ricoGotIvy.util.RunIt.RunIt;

public class Transfer {
    final DcMotorEx transfer;
    private Transfer(HardwareMap hardwareMap) {
        transfer = hardwareMap.get(DcMotorEx.class, "transfer");
    }

    @RunIt(callTime = CallTime.SUBSYSTEM)
    public Transfer transferWrapped(HardwareMap hardwareMap) {
        return new Transfer(hardwareMap);
    }

    public void run() {
        transfer.setPower(1);
    }

    public void back() {
        transfer.setPower(-1);
    }

    @RunIt(callTime = CallTime.STOP)
    public void stop() {
        transfer.setPower(0);
    }
}
