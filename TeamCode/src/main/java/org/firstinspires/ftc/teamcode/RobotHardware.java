package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class RobotHardware {
    private DcMotor leftFront, leftRear, rightFront, rightRear;
    private Gyroscope gyro;

    public RobotHardware(HardwareMap hardwareMap) {
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        leftRear = hardwareMap.get(DcMotor.class, "leftRear");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        rightRear = hardwareMap.get(DcMotor.class, "rightRear");
        gyro = hardwareMap.get(Gyroscope.class, "gyro");
    }

    public double calculateHeadingError(double targetHeading) {
        double currentHeading = gyro.getAngularOrientation().firstAngle;
        double error = targetHeading - currentHeading;
        while (error > 180) error -= 360;
        while (error <= -180) error += 360;
        return error;
    }

    public double[] getJoystickValues(double forward, double strafe, double rotation, boolean fieldCentric) {
        if (fieldCentric) {
            double gyroHeading = Math.toRadians(gyro.getAngularOrientation().firstAngle);
            double temp = forward * Math.cos(gyroHeading) - strafe * Math.sin(gyroHeading);
            strafe = forward * Math.sin(gyroHeading) + strafe * Math.cos(gyroHeading);
            forward = temp;
        }
        return new double[]{forward, strafe, rotation};
    }

    public void setMotorPowers(double[] joystickValues, double pidPower) {
        double flPower = joystickValues[0] + joystickValues[1] + joystickValues[2] + pidPower;
        double frPower = joystickValues[0] - joystickValues[1] - joystickValues[2] + pidPower;
        double blPower = joystickValues[0] - joystickValues[1] + joystickValues[2] + pidPower;
        double brPower = joystickValues[0] + joystickValues[1] - joystickValues[2] + pidPower;
        leftFront.setPower(flPower);
        leftRear.setPower(blPower);
        rightFront.setPower(frPower);
        rightRear.setPower(brPower);
    }
}
