package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "auto")
public class TestingAuto extends OpMode {

    DcMotor motor;
    String motor1_name = "motor1";
    @Override
    public void init() {
        motor = hardwareMap.dcMotor.get(motor1_name);
    }

    @Override
    public void loop() {
        motor.setPower(1);
    }
}
