package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Field Centric TeleOp", group="Examples")
public class FieldCentricTeleOp extends OpMode {
    private RobotHardware robot;
    private PIDController pidController;
    private ElapsedTime runtime = new ElapsedTime();
    private boolean fieldCentric = true;
    private boolean lastAButtonState = false; 

    @Override
    public void init() {
        robot = new RobotHardware(hardwareMap);
        pidController = new PIDController(0.1, 0.01, 0.001);
        pidController.setTarget(0.0);
    }

    @Override
    public void loop() {
        double error = robot.calculateHeadingError(pidController.getTarget());
        double pidPower = pidController.calculate(error, runtime.seconds());


        if (gamepad1.a && !lastAButtonState) {
            fieldCentric = !fieldCentric;
        }
        lastAButtonState = gamepad1.a;

        double[] joystickValues = robot.getJoystickValues(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, fieldCentric);
        robot.setMotorPowers(joystickValues, pidPower);

        runtime.reset();
    }
}
