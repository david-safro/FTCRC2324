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
        public double rightFrontYTicks = -2386.367538953284; // y position of the first parallel encoder (in tick units)
        public double rightRearYTicks = 2485.666047346362; // y position of the second parallel encoder (in tick units)
        public double backEncoderXTicks = -1920.6305593846134; // x position of the backEncoderendicular encoder (in tick units)
    }

    public static Params PARAMS = new Params();

    public final Encoder rightFront, rightRear, backEncoder;

    public final double inPerTick;

    private int lastrightFrontPos, lastrightRearPos, lastbackEncoderPos;

    public ThreeDeadWheelLocalizer(HardwareMap hardwareMap, double inPerTick) {
        // TODO: make sure your config has **motors** with these names (or change them)
        //   the encoders should be plugged into the slot matching the named motor
        //   see https://ftc-docs.firstinspires.org/en/latest/hardware_and_software_configuration/configuring/index.html
        rightFront = new OverflowEncoder(new RawEncoder(hardwareMap.get(DcMotorEx.class, "rightFront")));
        rightRear = new OverflowEncoder(new RawEncoder(hardwareMap.get(DcMotorEx.class, "rightRear")));
        backEncoder = new OverflowEncoder(new RawEncoder(hardwareMap.get(DcMotorEx.class, "backEncoder")));

        // TODO: reverse encoder directions if needed
        //   rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightRear.setDirection(DcMotorSimple.Direction.REVERSE);

        lastrightFrontPos = rightFront.getPositionAndVelocity().position;
        lastrightRearPos = rightRear.getPositionAndVelocity().position;
        lastbackEncoderPos = backEncoder.getPositionAndVelocity().position;

        this.inPerTick = inPerTick;

        FlightRecorder.write("THREE_DEAD_WHEEL_PARAMS", PARAMS);
    }

    public Twist2dDual<Time> update() {
        PositionVelocityPair rightFrontPosVel = rightFront.getPositionAndVelocity();
        PositionVelocityPair rightRearPosVel = rightRear.getPositionAndVelocity();
        PositionVelocityPair backEncoderPosVel = backEncoder.getPositionAndVelocity();

        int rightFrontPosDelta = rightFrontPosVel.position - lastrightFrontPos;
        int rightRearPosDelta = rightRearPosVel.position - lastrightRearPos;
        int backEncoderPosDelta = backEncoderPosVel.position - lastbackEncoderPos;

        Twist2dDual<Time> twist = new Twist2dDual<>(
                new Vector2dDual<>(
                        new DualNum<Time>(new double[] {
                                (PARAMS.rightFrontYTicks * rightRearPosDelta - PARAMS.rightRearYTicks * rightFrontPosDelta) / (PARAMS.rightFrontYTicks - PARAMS.rightRearYTicks),
                                (PARAMS.rightFrontYTicks * rightRearPosVel.velocity - PARAMS.rightRearYTicks * rightFrontPosVel.velocity) / (PARAMS.rightFrontYTicks - PARAMS.rightRearYTicks),
                        }).times(inPerTick),
                        new DualNum<Time>(new double[] {
                                (PARAMS.backEncoderXTicks / (PARAMS.rightFrontYTicks - PARAMS.rightRearYTicks) * (rightRearPosDelta - rightFrontPosDelta) + backEncoderPosDelta),
                                (PARAMS.backEncoderXTicks / (PARAMS.rightFrontYTicks - PARAMS.rightRearYTicks) * (rightRearPosVel.velocity - rightFrontPosVel.velocity) + backEncoderPosVel.velocity),
                        }).times(inPerTick)
                ),
                new DualNum<>(new double[] {
                        (rightFrontPosDelta - rightRearPosDelta) / (PARAMS.rightFrontYTicks - PARAMS.rightRearYTicks),
                        (rightFrontPosVel.velocity - rightRearPosVel.velocity) / (PARAMS.rightFrontYTicks - PARAMS.rightRearYTicks),
                })
        );

        lastrightFrontPos = rightFrontPosVel.position;
        lastrightRearPos = rightRearPosVel.position;
        lastbackEncoderPos = backEncoderPosVel.position;

        return twist;
    }
}
