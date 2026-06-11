package org.firstinspires.ftc.teamcode;

import com.pedropathing.ftc.InvertedFTCCoordinates;
import com.pedropathing.ftc.PoseConverter;
import com.pedropathing.geometry.Pose;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.CommandBuilder;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

@TeleOp
public class Mt2Tele extends OpMode {

    private MT2 mt2 = null;

    @Override
    public void init() {
        mt2 = new MT2(hardwareMap);
        mt2.follower.startTeleopDrive();
        mt2.init();
    }

    @Override
    public void loop() {
        mt2.loop();
        mt2.follower.setTeleOpDrive(-gamepad1.left_stick_y, -gamepad1.left_stick_x, gamepad1.right_stick_x);
    }

    private static class MT2 extends TestingBase {
        public MT2(HardwareMap hardwareMap) {
            super(hardwareMap);
        }

        /// Main init method.
        /// Sets the pipeline to tagTrackerConfig.pipeline and starts ll.
        public void init() {
            ll.pipelineSwitch(1); //April Tag pipeline with id filter 23,24
            ll.start();
        }

        /// Localizes with mt2
        public void loop() {
            //Set orientation as required for mt2 and get ll result
            ll.updateRobotOrientation(follower.getPose().getY());
            LLResult result = ll.getLatestResult();
            Pose3D mt2 = result.getBotpose_MT2();

            //Transform result through pose 3d to pose 2d to pedro pose to pedro pose w/ correct heading
            Pose2D pose2D = new Pose2D(DistanceUnit.INCH, mt2.getPosition().x, mt2.getPosition().y, AngleUnit.DEGREES, 0);
            Pose pose = PoseConverter.pose2DToPose(pose2D, InvertedFTCCoordinates.INSTANCE);
            Pose setPose = pose.setHeading(follower.getHeading());

            if (Math.abs(follower.getAngularVelocity()) < 360 && result.getBotposeTagCount() > 0) {
                follower.setPose(setPose);
                follower.update();
            }
        }


    }
}
