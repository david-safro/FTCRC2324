package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
public class RobotHardware {
    private DcMotor leftFront, leftRear, rightFront, rightRear;

    public RobotHardware(HardwareMap hardwareMap) {
        // Wheels
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        leftRear = hardwareMap.get(DcMotor.class, "leftRear");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        rightRear = hardwareMap.get(DcMotor.class, "rightRear");
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    double speedMult = 1;

    public void toggleSpeed() {
        if (speedMult == 1) {
            speedMult = 0.5;
        } else {
            speedMult = 1;
        }
    }

    public void setMotorPowers(double forward, double strafe, double rotation) {

        double powerdenom = Math.max(Math.abs(forward) + Math.abs(strafe) + Math.abs(rotation), 1);

        double frontLeftPower = (forward + strafe + rotation) / powerdenom;
        double backLeftPower = (forward - strafe + rotation) / powerdenom;
        double frontRightPower = (forward - strafe - rotation) / powerdenom;
        double backRightPower = (forward + strafe - rotation) / powerdenom;

        leftFront.setPower(frontLeftPower * speedMult);
        leftRear.setPower(backLeftPower * speedMult);
        rightFront.setPower(frontRightPower * speedMult);
        rightRear.setPower(backRightPower * speedMult);
    }
}