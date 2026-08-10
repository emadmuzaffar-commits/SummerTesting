package org.firstinspires.ftc.teamcode.Preseason;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class PreseasonExample extends OpMode {
    DcMotor motor;
    Servo servo;

    @Override
    public void init() {
        motor = hardwareMap.get(DcMotor.class, "motor");
        servo = hardwareMap.get(Servo.class, "servo");
    }

    @Override
    public void loop() {
        if (gamepad1.a) {
            servo.setPosition(1);
            motor.setPower(1);
        } else {
            servo.setPosition(0);
            motor.setPower(0);
        }
    }

}
