package org.firstinspires.ftc.teamcode;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
public class ArmFunctions {
    private DcMotorEx armMotor;
    private Servo armRotServo, clawServo, planeLauncher;

    public ArmFunctions(HardwareMap hardwareMap) {
        armMotor = hardwareMap.get(DcMotorEx.class, "armMotor");
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armRotServo = hardwareMap.get(Servo.class, "armRotServo");
        clawServo = hardwareMap.get(Servo.class, "clawServo");
        planeLauncher = hardwareMap.get(Servo.class, "planeLauncher");
    }

    double clawPos;
    double clawRotPos;
    int armMotorPos;
    double PLpos;

    public void setup(boolean gp1start) {
        if (gp1start) {
            armRotServo.setPosition(0.486);
        }
    }
    public void initilize() {planeLauncher.setPosition(0.4);}
    public void planeLaunch(boolean GP2START, boolean GP2LB, boolean GP2RB) {
        PLpos = planeLauncher.getPosition();
        if (GP2START) {
            planeLauncher.setPosition(0.4); // initial
        } else if (GP2RB && GP2LB) {
            planeLauncher.setPosition(0.8); // launched
        }
    }

    public void clawRot(boolean gamepad2x, boolean gamepad2y) {
        if (gamepad2x) {
            armRotServo.setPosition(0.486 /* 0 degrees */); // down
        } else if (gamepad2y) {
            armRotServo.setPosition(0.9 /* 180 degrees */); // up
        }
        clawRotPos = armRotServo.getPosition();
    }

    public void armMotor(boolean GP2RS, boolean GP2LS) {
        if (GP2RS) {
            armMotor.setTargetPosition(1100);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armMotor.setPower(0.3);
            armRotServo.setPosition(0.9); // up
        } else if (GP2LS) {
            armMotor.setTargetPosition(20);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armMotor.setPower(0.3);
            armRotServo.setPosition(0.486); // down
        }
    }
    public void claw(boolean gamepad2a, boolean gamepad2b) {
        clawPos = clawServo.getPosition();
        if (gamepad2a) {
            clawServo.setPosition(0.27); // close
        } else if (gamepad2b) {
            clawServo.setPosition(0.1); // open
        }
        clawPos = clawServo.getPosition();
    }  // THIS IS FOR THE INITIAL CLAW

    /*public void claw(boolean GP2B, boolean isButton2B) {
        if (GP2B) {
            if(!isButton2B) {
                isButton2B = true;
                if (clawServo.getPosition() < 0.15) {
                    clawServo.setPosition(0.27); // close
                } else if (clawServo.getPosition() > 0.15) {
                    clawServo.setPosition(0.13); // open
                }
            }
        } else {
            isButton2B = false;
        }

    }*/
}