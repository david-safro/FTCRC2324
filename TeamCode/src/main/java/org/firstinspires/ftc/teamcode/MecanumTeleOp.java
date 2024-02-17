package org.firstinspires.ftc.teamcode;

import android.util.Pair;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

@TeleOp(name="RoberriPi", group="TeleOp")
public class MecanumTeleOp extends OpMode {
    public void clawRot() {
        if (currentGamepad2.a && !previousGamepad2.a) {
            toggleClawRot = !toggleClawRot;
        }
        if (toggleClawRot && currentGamepad2.a) {
            functions.clawRotation(toggleClawRot);
        } else if (!toggleClawRot && currentGamepad2.a) {
            functions.clawRotation(toggleClawRot);
        }
    }
    public void leftClaw() {
        if (currentGamepad2.x && !previousGamepad2.x) {
            toggleLeftClaw = !toggleLeftClaw;
        }
        if (toggleLeftClaw && currentGamepad2.x) {
            functions.leftClaw(toggleLeftClaw);
        } else if (!toggleLeftClaw && currentGamepad2.x) {
            functions.leftClaw(toggleLeftClaw);
        }
    }
    public void rightClaw() {
        if (currentGamepad2.b && !previousGamepad2.b) {
            toggleRightClaw = !toggleRightClaw;
        }
        if (toggleRightClaw && currentGamepad2.b) {
            functions.rightClaw(toggleRightClaw);
        } else if (!toggleRightClaw && currentGamepad2.b) {
            functions.rightClaw(toggleRightClaw);
        }
    }
    public void planeLaunch() {
        if (currentGamepad2.start && !previousGamepad2.start) {
            togglePlane = !togglePlane;
        }
        if (togglePlane && currentGamepad2.start) {
            functions.planeLaunch(togglePlane);
        } else if (!togglePlane && currentGamepad2.start) {
            functions.planeLaunch(togglePlane);
        }
    }
    public void arm() {
        if (currentGamepad2.y && !previousGamepad2.y) {
            toggleArm = !toggleArm;
        }
        if (toggleArm && currentGamepad2.y) {
            functions.armMotor(toggleArm);
        } else if (!toggleArm && currentGamepad2.y) {
            functions.armMotor(toggleArm);
        }
    }
    Gamepad currentGamepad1 = new Gamepad();
    Gamepad currentGamepad2 = new Gamepad();
    Gamepad previousGamepad1 = new Gamepad();
    Gamepad previousGamepad2 = new Gamepad();
    private RobotHardware robot;
    private ArmFunctions functions;
    @Override
    public void init() {
        robot = new RobotHardware(hardwareMap);
        functions = new ArmFunctions(hardwareMap);
        functions.initilize();
    }

    boolean isButton1BACK, togglePlane, toggleArm, toggleClawRot, lastGP2X, toggleLeftClaw, toggleRightClaw, lastGP2A, lastGP2B = false;
    Pair<Boolean, Boolean> clawRotSaveState = new Pair<Boolean, Boolean>(toggleClawRot, lastGP2A);
    double speedMult = 1;
    @Override
    public void loop() {
        previousGamepad1.copy(currentGamepad1);
        previousGamepad2.copy(currentGamepad2);

        currentGamepad1.copy(gamepad1);
        currentGamepad2.copy(gamepad2);

        double forward = -gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double rotation = gamepad1.right_stick_x;
        if (gamepad1.back) {
            if(!isButton1BACK) {
                if (speedMult == 1) {
                    speedMult = 0.4;
                } else {
                    speedMult = 1;
                }

                isButton1BACK = true;
            }
        } else {
            isButton1BACK = false;
        }
        robot.setMotorPowers(forward * speedMult, strafe * speedMult, rotation * 0.8 * speedMult);
        //clawRotSaveState = functions.clawRot(gamepad2.a, clawRotSaveState.first, clawRotSaveState.second);
        //functions.clawRot(currentGamepad2.a, previousGamepad2.a, functions.toggleClawRot);
        clawRot();
        leftClaw();
        planeLaunch();
        rightClaw();
        arm();
        functions.fixes(gamepad2.right_stick_button);
        functions.lifter(gamepad1.y, gamepad1.a, gamepad1.b, gamepad1.x);
        telemetry.addData("lifterPos", functions.lifterPos);
        telemetry.addData("PlaneServoPos", functions.PLpos);
        telemetry.addData("clawPos", functions.clawPos);
        telemetry.addData("clawRotPos", functions.clawRotPos);
        telemetry.addData("armMotorPos", functions.armMotorPos);
        telemetry.addData("lastGP2A", functions.toggleClawRot);
        telemetry.addData("servopos", functions.servopos);
        telemetry.addData("speedMult", speedMult);
        telemetry.addData("targetpos", functions.target);
        telemetry.update();


    }
}