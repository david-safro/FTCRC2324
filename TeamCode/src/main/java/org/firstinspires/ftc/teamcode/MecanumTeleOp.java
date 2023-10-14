package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="TeleOp", group="Testing")
public class MecanumTeleOp extends OpMode {
    private RobotHardware robot;
    @Override
    public void init() {
        robot = new RobotHardware(hardwareMap);
    }
    @Override
    public void loop() {
        double forward = -gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double rotation = gamepad1.right_stick_x;

        robot.setMotorPowers(strafe, forward, rotation);
    }
}
