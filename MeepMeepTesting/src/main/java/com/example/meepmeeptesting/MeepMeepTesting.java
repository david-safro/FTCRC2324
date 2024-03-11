package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(11, -60, Math.toRadians(90)))
                //.splineTo(new Vector2d(44.5, -36), Math.PI / 2) // to board
                .setTangent(0)
                .strafeTo(new Vector2d(30, -27))
                .turnTo(Math.toRadians(180))
                .waitSeconds(1) // open left
                .turnTo(Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(44.5, -39, 0), 1)
                .waitSeconds(1.5) // arm up
                .lineToX(49.5)
                .waitSeconds(0.5) // open left
                .waitSeconds(0.5)
                .lineToX(40)
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}