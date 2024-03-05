package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.util.Random;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        // Randomly choose one of the two starting points
        Vector2d startingPoint;
        if (new Random().nextBoolean()) {
            // Starting point: -34, -70
            startingPoint = new Vector2d(-34, -70);
        } else {
            // Starting point: -25, -70
            startingPoint = new Vector2d(-25, -70);
        }

        // Randomly choose a team prop location (left, center, or right)
        String[] possibleLocations = {"left", "center", "right"};
        String teamPropLocation = possibleLocations[new Random().nextInt(possibleLocations.length)];

        // Determine the shift in the y-coordinate
        double yShift = (teamPropLocation.equals("center")) ? 0 : 46;

        // Determine the destination based on the team prop location
        Vector2d destination;
        switch (teamPropLocation) {
            case "left":
                destination = new Vector2d(-48, -34 + yShift);
                break;
            case "center":
                destination = new Vector2d(-34, -24 + yShift);
                break;
            case "right":
                destination = new Vector2d(-25, -32 + yShift);
                break;
            default:
                throw new IllegalArgumentException("Invalid team prop location");
        }

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(startingPoint.getX(), startingPoint.getY(), 0))
                                .lineTo(destination) // Drive to the determined destination
                                .waitSeconds(1) // Pause for 1 second
                                .lineTo(new Vector2d(-34, -16 + yShift)) // Drive to (-34, -16)
                                .turn(Math.toRadians(-90)) // Turn 90 degrees right
                                .lineTo(new Vector2d(2, -14 + yShift)) // Drive to (2, -14)
                                .lineTo(new Vector2d(50, -34 + yShift)) // Drive to (50, -34)
                                .waitSeconds(1) // Pause for 1 second
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();

        // Comment indicating to place the pixel on the prop
        System.out.println("PLACE PIXEL ON PROP");
    }
}
