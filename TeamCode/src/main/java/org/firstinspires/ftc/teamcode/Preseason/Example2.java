package org.firstinspires.ftc.teamcode.Preseason;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class Example2 extends OpMode {
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;

    void setTankDrive(double leftStickX, double rightStickY) {
        double leftPower = leftStickX + rightStickY;
        double rightPower = leftStickX - rightStickY;
        frontLeft.setPower(leftPower);
        frontRight.setPower(rightPower);
        backLeft.setPower(leftPower);
        backRight.setPower(rightPower);
    }

    @Override
    public void init() {
        frontLeft = hardwareMap.get(DcMotor.class, "motor");
        frontRight = hardwareMap.get(DcMotor.class, "motor");
        backLeft = hardwareMap.get(DcMotor.class, "motor");
        backRight = hardwareMap.get(DcMotor.class, "motor");
    }

    @Override
    public void loop() {
        setTankDrive(gamepad1.left_stick_x, gamepad1.right_stick_y);
    }

}
