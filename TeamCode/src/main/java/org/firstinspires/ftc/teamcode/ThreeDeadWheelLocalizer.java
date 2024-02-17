package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.DualNum;
import com.acmerobotics.roadrunner.Time;
import com.acmerobotics.roadrunner.Twist2dDual;
import com.acmerobotics.roadrunner.Vector2dDual;
import com.acmerobotics.roadrunner.ftc.Encoder;
import com.acmerobotics.roadrunner.ftc.FlightRecorder;
import com.acmerobotics.roadrunner.ftc.OverflowEncoder;
import com.acmerobotics.roadrunner.ftc.PositionVelocityPair;
import com.acmerobotics.roadrunner.ftc.RawEncoder;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
public final class ThreeDeadWheelLocalizer implements Localizer {
    public static class Params {
        public double leftRearYTicks = 0.0; // y position of the first parallel encoder (in tick units)
        public double rightRearYTicks = 1.0; // y position of the second parallel encoder (in tick units)
        public double backEncoderXTicks = 0.0; // x position of the backEncoderendicular encoder (in tick units)
    }

    public static Params PARAMS = new Params();

    public final Encoder leftRear, rightRear, backEncoder;

    public final double inPerTick;

    private int lastleftRearPos, lastrightRearPos, lastbackEncoderPos;

    public ThreeDeadWheelLocalizer(HardwareMap hardwareMap, double inPerTick) {
        // TODO: make sure your config has **motors** with these names (or change them)
        //   the encoders should be plugged into the slot matching the named motor
        //   see https://ftc-docs.firstinspires.org/en/latest/hardware_and_software_configuration/configuring/index.html
        leftRear = new OverflowEncoder(new RawEncoder(hardwareMap.get(DcMotorEx.class, "leftRear")));
        rightRear = new OverflowEncoder(new RawEncoder(hardwareMap.get(DcMotorEx.class, "rightRear")));
        backEncoder = new OverflowEncoder(new RawEncoder(hardwareMap.get(DcMotorEx.class, "backEncoder")));

        // TODO: reverse encoder directions if needed
        //   leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
        leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
        rightRear.setDirection(DcMotorSimple.Direction.REVERSE);

        lastleftRearPos = leftRear.getPositionAndVelocity().position;
        lastrightRearPos = rightRear.getPositionAndVelocity().position;
        lastbackEncoderPos = backEncoder.getPositionAndVelocity().position;

        this.inPerTick = inPerTick;

        FlightRecorder.write("THREE_DEAD_WHEEL_PARAMS", PARAMS);
    }

    public Twist2dDual<Time> update() {
        PositionVelocityPair leftRearPosVel = leftRear.getPositionAndVelocity();
        PositionVelocityPair rightRearPosVel = rightRear.getPositionAndVelocity();
        PositionVelocityPair backEncoderPosVel = backEncoder.getPositionAndVelocity();

        int leftRearPosDelta = leftRearPosVel.position - lastleftRearPos;
        int rightRearPosDelta = rightRearPosVel.position - lastrightRearPos;
        int backEncoderPosDelta = backEncoderPosVel.position - lastbackEncoderPos;

        Twist2dDual<Time> twist = new Twist2dDual<>(
                new Vector2dDual<>(
                        new DualNum<Time>(new double[] {
                                (PARAMS.leftRearYTicks * rightRearPosDelta - PARAMS.rightRearYTicks * leftRearPosDelta) / (PARAMS.leftRearYTicks - PARAMS.rightRearYTicks),
                                (PARAMS.leftRearYTicks * rightRearPosVel.velocity - PARAMS.rightRearYTicks * leftRearPosVel.velocity) / (PARAMS.leftRearYTicks - PARAMS.rightRearYTicks),
                        }).times(inPerTick),
                        new DualNum<Time>(new double[] {
                                (PARAMS.backEncoderXTicks / (PARAMS.leftRearYTicks - PARAMS.rightRearYTicks) * (rightRearPosDelta - leftRearPosDelta) + backEncoderPosDelta),
                                (PARAMS.backEncoderXTicks / (PARAMS.leftRearYTicks - PARAMS.rightRearYTicks) * (rightRearPosVel.velocity - leftRearPosVel.velocity) + backEncoderPosVel.velocity),
                        }).times(inPerTick)
                ),
                new DualNum<>(new double[] {
                        (leftRearPosDelta - rightRearPosDelta) / (PARAMS.leftRearYTicks - PARAMS.rightRearYTicks),
                        (leftRearPosVel.velocity - rightRearPosVel.velocity) / (PARAMS.leftRearYTicks - PARAMS.rightRearYTicks),
                })
        );

        lastleftRearPos = leftRearPosVel.position;
        lastrightRearPos = rightRearPosVel.position;
        lastbackEncoderPos = backEncoderPosVel.position;

        return twist;
    }
}
