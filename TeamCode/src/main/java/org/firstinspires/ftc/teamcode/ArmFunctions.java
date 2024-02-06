package org.firstinspires.ftc.teamcode;

import static java.lang.Thread.sleep;

import android.util.Pair;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
public class ArmFunctions {
    boolean saveState = false;
    private DcMotorEx armMotor, lifter;
    private Servo armRotServo, leftClaw, rightClaw, planeLauncher;

    public ArmFunctions(HardwareMap hardwareMap) {
        armMotor = hardwareMap.get(DcMotorEx.class, "armMotor");
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armRotServo = hardwareMap.get(Servo.class, "armRotServo");
        leftClaw = hardwareMap.get(Servo.class, "leftClaw");
        rightClaw = hardwareMap.get(Servo.class, "rightClaw");
        //planeLauncher = hardwareMap.get(Servo.class, "planeLauncher");
        lifter = hardwareMap.get(DcMotorEx.class, "lifter");
        this.saveState = saveState;
    }

    double clawPos;
    double clawRotPos;
    boolean testing;
    double servopos;
    double lifterPos;
    int armMotorPos;
    double PLpos;

    public void setup(boolean gp1start) {
        if (gp1start) {
            armRotServo.setPosition(0.486);
        }
    }
    public void initilize() {planeLauncher.setPosition(0.4);}
    /*public void planeLaunch(boolean GP2START, boolean GP2LB) {
        PLpos = planeLauncher.getPosition();
        if (GP2START) {
            planeLauncher.setPosition(0.4); // initial
        } else if (GP2LB) {
            planeLauncher.setPosition(0.8); // launched
        }
    }*/
    /*public void planeLaunchV2(boolean GP2START, boolean togglePlane, boolean lastGP2START) {
        if (GP2START != lastGP2START) {
            togglePlane = !togglePlane;
            double newPosition = togglePlane ? 0.4 : 0.8;
            planeLauncher.setPosition(newPosition);
            lastGP2START = GP2START;
        }
    }*/
    /*public void clawRot(boolean gamepad2x, boolean gamepad2y) {
        if (gamepad2x) {
            armRotServo.setPosition(0.486); // down
        } else if (gamepad2y) {
            armRotServo.setPosition(0.9); // up
        }
        clawRotPos = armRotServo.getPosition();
    }*/

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
    public void lifter(boolean GP1Y, boolean GP1A, boolean GP1B, boolean GP1X) {
        lifterPos = lifter.getCurrentPosition();

        if (GP1Y) {
            lifter.setTargetPosition(0);
            lifter.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lifter.setPower(1);
        } else if (GP1A) {
            lifter.setTargetPosition(7000);
            lifter.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lifter.setPower(1);
        } else if (GP1B) {
            lifter.setTargetPosition(3000);
            lifter.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lifter.setPower(1);
        } else if (GP1X) {
            lifter.setTargetPosition(-7000);
            lifter.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lifter.setPower(0.4);
        }
    }
    public void rightClaw(boolean GP2B, boolean toggleRightClaw, boolean lastGP2B) {
        if (GP2B != lastGP2B) {
            toggleRightClaw = !toggleRightClaw;
            double rightClawNewPosition = toggleRightClaw ? 0.1 : 0.15; // open : close
            rightClaw.setPosition(rightClawNewPosition);
            lastGP2B = GP2B;
        }
    }
    public void leftClaw(boolean GP2X, boolean toggleLeftClaw, boolean lastGP2X) {
        if (GP2X == true && lastGP2X == false) {
            toggleLeftClaw = !toggleLeftClaw;
            double leftClawNewPosition = toggleLeftClaw ? 0.1 : 0.15; // open : close
            leftClaw.setPosition(leftClawNewPosition);
            lastGP2X = GP2X;
        }
    }
    /*public boolean clawRot(boolean GP2A, boolean lastGP2A, boolean toggleClawRot) {
        if (GP2A != lastGP2A) {
            //toggleClawRot = !toggleClawRot;
            //double clawRotNewPosition = toggleClawRot ? 0.5 : 0.7; // down : up
            //armRotServo.setPosition(clawRotNewPosition);
            lastGP2A = GP2A;
        }
        testing = lastGP2A;
        return lastGP2A;
    }*/
    public Pair<Boolean, Boolean> clawRot(boolean GP2A, boolean lastGP2A, boolean clawRotCheck) {
        if (GP2A && !clawRotCheck) {
            if (!lastGP2A) {
                armRotServo.setPosition(0.486); // down
                lastGP2A = true;
                clawRotCheck = false;
            } else {
                armRotServo.setPosition(0.9); // up
                lastGP2A = false;
                clawRotCheck = false;
            }
        }
        servopos = armRotServo.getPosition();
        return new Pair<Boolean, Boolean>(clawRotCheck, lastGP2A);
    }
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