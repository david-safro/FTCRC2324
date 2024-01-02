package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="RoberriPi", group="TeleOp")
public class MecanumTeleOp extends OpMode {
    private RobotHardware robot;
    private ArmFunctions functions;
    @Override
    public void init() {
        robot = new RobotHardware(hardwareMap);
        functions = new ArmFunctions(hardwareMap);
    }

    boolean isButtonA = false;
    public double speedMult;
    @Override

    public void loop() {

        double forward = -gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double rotation = gamepad1.right_stick_x;
        if (gamepad1.a) {
            if(!isButtonA) {
                robot.toggleSpeed();
                isButtonA = true;
            }
        } else {
            isButtonA = false;
        }
        functions.setup(gamepad1.start);
        robot.setMotorPowers(strafe, forward, rotation * 0.8);
        functions.claw(gamepad2.x, gamepad2.b);
        functions.clawRot(gamepad1.x, gamepad1.b);
        functions.armMotor(gamepad2.right_stick_button, gamepad2.left_stick_button);
        functions.planeLaunch(gamepad2.start, gamepad2.left_bumper, gamepad2.right_bumper);
        telemetry.addData("PlaneServoPos", functions.PLpos);
        telemetry.addData("clawPos", functions.clawPos);
        telemetry.addData("clawRotPos", functions.clawRotPos);
        telemetry.addData("armMotorPos", functions.armMotorPos);
        telemetry.update();
    }
}