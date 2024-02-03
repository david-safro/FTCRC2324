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
        functions.initilize();
    }
    boolean isButtonA, isButton2B, isButton2RS, isButton2X, togglePlane, lastGP2START = false;
    double speedMult = 1;
    @Override
    public void loop() {

        double forward = -gamepad1.left_stick_y;
        double strafe = -gamepad1.left_stick_x;
        double rotation = gamepad1.right_stick_x;
        if (gamepad1.a) {
            if(!isButtonA) {
                if (speedMult == 1) {
                    speedMult = 0.4;
                } else {
                    speedMult = 1;
                }

                isButtonA = true;
            }
        } else {
            isButtonA = false;
        }
        functions.setup(gamepad1.start);
        robot.setMotorPowers(strafe, forward, rotation * 0.8);
        functions.claw(gamepad2.a, gamepad2.b);
        functions.clawRot(gamepad2.x, gamepad2.y);
        functions.armMotor(gamepad2.right_stick_button, gamepad2.left_stick_button);
        functions.planeLaunchV2(gamepad2.start, togglePlane, lastGP2START);
        functions.lifter(gamepad1.y, gamepad1.a, gamepad1.b, gamepad1.x);
        telemetry.addData("lifterPos", functions.lifterPos);
        telemetry.addData("PlaneServoPos", functions.PLpos);
        telemetry.addData("clawPos", functions.clawPos);
        telemetry.addData("clawRotPos", functions.clawRotPos);
        telemetry.addData("armMotorPos", functions.armMotorPos);
        telemetry.update();
    }
}