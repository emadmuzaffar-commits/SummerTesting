package org.firstinspires.ftc.teamcode.runItDev.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.runItDev.util.runIt.external.Call;
import org.firstinspires.ftc.teamcode.runItDev.util.runIt.external.RunIt;

public class Shooter {
    private final DcMotorEx s1;
    private final DcMotorEx s2;
    private final ElapsedTime timer;
    @RunIt(callTime = Call.SUBSYSTEM)
    public Shooter(HardwareMap hardwareMap) {
        s1 = hardwareMap.get(DcMotorEx.class, "s1");
        s2 = hardwareMap.get(DcMotorEx.class, "s2");
        s1.setDirection(DcMotorEx.Direction.REVERSE);
        s2.setDirection(DcMotorEx.Direction.FORWARD);
        timer = new ElapsedTime();
    }

    public void shoot(double power) {
        timer.reset();
        s1.setPower(power);
        s2.setPower(power);
    }

    public boolean checkTimer() {
        return timer.seconds() > 5;
    }

    @RunIt(callTime = Call.STOP)
    public void stop() {
        s1.setPower(0);
        s2.setPower(0);
    }

}
