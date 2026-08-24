package org.firstinspires.ftc.teamcode.runItDev.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.teamcode.runItDev.util.runIt.external.Call;
import org.firstinspires.ftc.teamcode.runItDev.util.runIt.external.RunIt;

public class Shooter {
    private final DcMotorEx s1;
    private final DcMotorEx s2;
    private final ElapsedTime timer;
    @RunIt(callTime = Call.SUBSYSTEM)
    public Shooter(HardwareMap hardwareMap) {
        s1 = hardwareMap.get(DcMotorEx.class, "shooter1");
        s2 = hardwareMap.get(DcMotorEx.class, "shooter2");
        s2.setDirection(DcMotorEx.Direction.REVERSE);
        timer = new ElapsedTime();
    }

    public void shoot(double power) {
        RobotLog.ii("shooter", "shootcalled");
        timer.reset();
        s1.setPower(power);
        s2.setPower(power);
    }

    public boolean checkTimer() {
        if (timer.seconds() < 5) {
            RobotLog.ii("shooter", "checktimetrue");
            return true;
        }
        return false;
    }

    @RunIt(callTime = Call.STOP)
    public void stop() {
        RobotLog.ii("shooter", "stopped");
        s1.setPower(0);
        s2.setPower(0);
    }

}
