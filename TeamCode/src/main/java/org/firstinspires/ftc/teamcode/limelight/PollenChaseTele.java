package org.firstinspires.ftc.teamcode.limelight;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp
public class PollenChaseTele extends OpMode {
    private PollenChaser pollenChase;

    @Override
    public void init() {
        pollenChase = new PollenChaser(hardwareMap);
        pollenChase.follower.startTeleopDrive();
        pollenChase.init();
    }

    @Override
    public void loop() {
        pollenChase.follower.update();
        pollenChase.trackTag(gamepad1);
    }

    private static class PollenChaser extends TestingBase {
        @Configurable
        public static class PollenChaseConfig {
            public static int pipeline = 0;
            public static double kR = 0.045;
            public static double kX = 0.055;
            public static double noTagSpinPower = 0.75;
        }

        public PollenChaser(HardwareMap hardwareMap) {
            super(hardwareMap);
        }

        /// Loop method to detect an AprilTag and return Tx. Prints status to panels telemetry
        private double getTx() {
            double tx = ll.getLatestResult().getTx();
            if (tx != 0) {
                telemetryM.addData("status", "detected");
            }
            return tx;
        }

        private double getTy() {
            double ty = ll.getLatestResult().getTy();
            if (ty != 0) {
                telemetryM.addData("status", "detected");
            }
            return ty;
        }

        private double getRotation(double tx) {
            if (tx == 0) {
                telemetryM.addData("Drive Power x", PollenChaseConfig.noTagSpinPower);
                return PollenChaseConfig.noTagSpinPower;
            }
            double r = -tx * PollenChaseConfig.kR;
            telemetryM.addData("Drive Power x", r);
            return r;
        }

        private double getY(double ty) {
            double y = -ty * PollenChaseConfig.kX;
            telemetryM.addData("Drive Power Y", y);
            return y;
        }


        /// Main init method.
        /// Sets the pipeline to PollenChaseConfig.pipeline and starts ll.
        public void init() {
            ll.pipelineSwitch(PollenChaseConfig.pipeline);
            ll.start();
        }

        /// Main trackTag method. Sets teleop drive to be x,y controlled by gamepad with rotation to track tag.
        /// Start teleop drive must be called before this
        public void trackTag(Gamepad gamepad1) {
            //get the tx and ty from ll
            double tx = getTx();
            double ty = getTy();
            //apply rotation multiplier
            double r = getRotation(tx);
            double y = getY(ty);
            //setTeleopDrive
            follower.setTeleOpDrive(y, -gamepad1.left_stick_x, r);
        }

    }
}
