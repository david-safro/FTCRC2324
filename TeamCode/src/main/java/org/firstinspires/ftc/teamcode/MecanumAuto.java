package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

// RR-specific imports
import com.acmerobotics.dashboard.canvas.CanvasOp;
import com.acmerobotics.dashboard.canvas.Spline;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

// Non-RR imports
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.teamcode.MecanumDrive;


@Config
@Autonomous(name = "RoberriPi Auto", group = "Autonomous")
public class MecanumAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

    }
    public class ArmMotor {
        private DcMotorEx armMotor;
        private Servo armRotServo;

        public ArmMotor(HardwareMap hardwareMap) {
            armMotor = hardwareMap.get(DcMotorEx.class, "armMotor");
            armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            armRotServo = hardwareMap.get(Servo.class, "armRotServo");
        }
        public class ArmUp implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                armMotor.setTargetPosition(350);
                armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                armMotor.setPower(0.5);
                armRotServo.setPosition(0.6); // up
                return false;
            }
        }
        public Action armUp() {
            return new ArmUp();
        }

        public class ArmDown implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                armMotor.setTargetPosition(20);
                armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                armMotor.setPower(0.3);
                return false;
            }
        }
        public Action armDown() {
            return new ArmDown();
        }
    }

    public class LeftClaw {
        private Servo leftClaw;

        public LeftClaw(HardwareMap hardwareMap) {
            leftClaw = hardwareMap.get(Servo.class, "leftClaw");
        }
        public class CloseLeft implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                leftClaw.setPosition(0.05); // CHANGE THIS (THIS IS CLOSED LEFT)
                return false;
            }
        }
        public Action closeLeft() {
            return new CloseLeft();
        }
        public class OpenLeft implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                leftClaw.setPosition(0.4); // CHANGE THIS (THIS IS OPEN LEFT)
                return false;
            }
        }
        public Action openLeft() {
            return new OpenLeft();
        }
    }
    public class rightClaw {
        private Servo rightClaw;

        public rightClaw(HardwareMap hardwareMap) {
            rightClaw = hardwareMap.get(Servo.class, "rightClaw");
        }
        public class CloseRight implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                rightClaw.setPosition(0.71); // CHANGE THIS (THIS IS CLOSED RIGHT)
                return false;
            }
        }
        public Action closeRight() {
            return new CloseRight();
        }
        public class OpenRight implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                rightClaw.setPosition(0.4); // CHANGE THIS (THIS IS OPEN RIGHT)
                return false;
            }
        }
        public Action openRight() {
            return new OpenRight();
        }
    }
    public class clawRot {
        private Servo armRotServo;

        public clawRot(HardwareMap hardwareMap) {
            armRotServo = hardwareMap.get(Servo.class, "armRotServo");
        }
        public class RotClawUp implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                armRotServo.setPosition(0.8);
                return false;
            }
        }
        public Action rotClawUp() {
            return new RotClawUp();
        }
    }
}
