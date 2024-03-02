package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

// RR-specific imports
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

    public class armMotor {
        private DcMotorEx armMotor;

        public armMotor(HardwareMap hardwareMap) {
            armMotor = hardwareMap.get(DcMotorEx.class, "armMotor");
            armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    // claw class
    public class leftClaw {
        private Servo leftClaw;

        public leftClaw(HardwareMap hardwareMap) {
            leftClaw = hardwareMap.get(Servo.class, "leftClaw");
        }
    }
    public class rightClaw {
        private Servo rightClaw;

        public rightClaw(HardwareMap hardwareMap) {
            rightClaw = hardwareMap.get(Servo.class, "rightClaw");
        }
    }
}
