package org.firstinspires.ftc.teamcode;

public class PIDController {
    private double Kp, Ki, Kd;
    private double integral, derivative, previous_error;
    private double target;

    public PIDController(double Kp, double Ki, double Kd) {
        this.Kp = Kp;
        this.Ki = Ki;
        this.Kd = Kd;
    }

    public void setTarget(double target) {
        this.target = target;
    }

    public double getTarget() {
        return target;
    }

    public double calculate(double error, double deltaTime) {
        integral += error * deltaTime;
        derivative = (error - previous_error) / deltaTime;
        previous_error = error;
        return Kp * error + Ki * integral + Kd * derivative;
    }
}