package org.firstinspires.ftc.teamcode.ricoGotIvy.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.ricoGotIvy.util.CallTime;
import org.firstinspires.ftc.teamcode.ricoGotIvy.util.RunIt;

public class Shooter {
    private final DcMotorEx s1;
    private final DcMotorEx s2;
    private final ElapsedTime timer;
    private Shooter(HardwareMap hardwareMap) {
        s1 = hardwareMap.get(DcMotorEx.class, "s1");
        s2 = hardwareMap.get(DcMotorEx.class, "s2");
        timer = new ElapsedTime();
    }
    @RunIt(callTime = CallTime.SUBSYSTEM)
    public Shooter shooterWrapped(HardwareMap hardwareMap) {
        return new Shooter(hardwareMap);
    }

    @RunIt(callTime = CallTime.START)
    public void init() {
        s1.setDirection(DcMotorEx.Direction.REVERSE);
        s2.setDirection(DcMotorEx.Direction.FORWARD);
    }

    public void shoot(double power) {
        timer.reset();
        s1.setPower(power);
        s2.setPower(power);
    }

    public boolean checkTimer() {
        return timer.seconds() > 5;
    }

    @RunIt(callTime = CallTime.STOP)
    public void stop() {
        s1.setPower(0);
        s2.setPower(0);
    }

}
