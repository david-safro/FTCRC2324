package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import java.util.ArrayList;
import java.util.List;
@TeleOp(name = "Hexagon Detection and Follow", group = "Vision")
public class OpenCvDetection extends OpMode {
    OpenCvCamera webcam;
    boolean hexagonDetected = false;
    Point hexagonLocation = new Point();
    RobotHardware robotHardware;

    Mat cvtMat = new Mat();
    Mat mask = new Mat();
    Mat hierarchy = new Mat();

    int cameraHeight = 600;
    int cameraWidth = 800;

    @Override
    public void init() {
        robotHardware = new RobotHardware(hardwareMap);
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "webcam"), cameraMonitorViewId);

        webcam.setPipeline(new OpenCvPl());

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(cameraWidth, cameraHeight, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("Error", "Camera open failed");
                telemetry.update();
            }
        });
    }

    @Override
    public void loop() {
        if (hexagonDetected) {
            telemetry.addData("Status", "Hexagon Detected");
            telemetry.addData("Hexagon Location", hexagonLocation);
            driveTowardsHexagon(hexagonLocation);
        } else {
            telemetry.addData("Status", "No Hexagon Detected");
            robotHardware.setMotorPowers(0, 0, 0);
        }
        telemetry.update();
    }
    private void driveTowardsHexagon(Point hexagonLoc) {
        double centerX = cameraWidth / 2.0;
        double error = hexagonLoc.x - centerX;
        double turn = error / centerX;
        robotHardware.setMotorPowers(2, 0, turn);
    }

    class OpenCvPl extends OpenCvPipeline {
        @Override
        public Mat processFrame(Mat input) {
            hexagonDetected = false;
            Imgproc.cvtColor(input, cvtMat, Imgproc.COLOR_RGB2HSV);
            Imgproc.GaussianBlur(cvtMat, cvtMat, new Size(3, 3), 0);

            Scalar minValues = new Scalar(0, 0, 200);
            Scalar maxValues = new Scalar(180, 25, 255);

            Core.inRange(cvtMat, minValues, maxValues, mask);
            List<MatOfPoint> contours = new ArrayList<>();
            Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);

            for (MatOfPoint contour : contours) {
                MatOfPoint2f contourPoly = new MatOfPoint2f();
                Imgproc.approxPolyDP(new MatOfPoint2f(contour.toArray()), contourPoly, 3, true);
                Rect boundRect = Imgproc.boundingRect(new MatOfPoint(contourPoly.toArray()));

                if (boundRect.width > 50 && contourPoly.toArray().length == 6) {
                    hexagonDetected = true;
                    Imgproc.rectangle(input, boundRect.tl(), boundRect.br(), new Scalar(0, 255, 0), 2);

                    hexagonLocation.x = boundRect.x + boundRect.width / 2.0;
                    hexagonLocation.y = boundRect.y + boundRect.height / 2.0;
                }
                contourPoly.release();
            }
            return input;
        }
    }

}
