package org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff.cv.Pipelines;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;

public class SampleDetectionPipelineAngledCam extends SamplePipeline
{
    // Some stuff to handle returning our various buffers
    Stage[] stages = Stage.values();
    public double brightness;

    // Keep track of what stage the viewport is showing
    int stageNum = 0;
    public Point RedCoords, BlueCoords, YellowCoords;

    @Override
    public void onViewportTapped()
    {
        int nextStageNum = stageNum + 1;

        if (nextStageNum >= stages.length) {
            nextStageNum = 0;
        }

        stageNum = nextStageNum;
    }

    @Override
    public Mat processFrame(Mat input)
    {
        // We'll be updating this with new data below
        internalStoneList.clear();

        /*
         * Run the image processing
         */
        findContours(input);

        clientStoneList = new ArrayList<>(internalStoneList);

        /*
         * Decide which buffer to send to the viewport
         */
        switch (stages[stageNum]) {
            case YCrCb: {
                return ycrcbMat;
            }

            case FINAL: {
                return input;
            }

            case MASKS: {
                Mat masks = new Mat();
                Core.addWeighted(yellowThresholdMat, 1.0, redThresholdMat, 1.0, 0.0, masks);
                Core.addWeighted(masks, 1.0, blueThresholdMat, 1.0, 0.0, masks);
                return masks;
            }

            case MASKS_NR: {
                Mat masksNR = new Mat();
                Core.addWeighted(morphedYellowThreshold, 1.0, morphedRedThreshold, 1.0, 0.0, masksNR);
                Core.addWeighted(masksNR, 1.0, morphedBlueThreshold, 1.0, 0.0, masksNR);
                return masksNR;
            }

            case CONTOURS: {
                return contoursOnPlainImageMat;
            }
        }

        return input;
    }

    void findContours(Mat input)
    {
        brightness = getMidPixelBrightness(input);
        // Convert the input image to YCrCb color space
        Imgproc.cvtColor(input, ycrcbMat, Imgproc.COLOR_RGB2YCrCb);

        // Extract the Cb channel for blue detection
        Core.extractChannel(ycrcbMat, cbMat, 2); // Cb channel index is 2

        // Extract the Cr channel for red detection
        Core.extractChannel(ycrcbMat, crMat, 1); // Cr channel index is 1

        // Threshold the Cb channel to form a mask for blue
        Imgproc.threshold(cbMat, blueThresholdMat, BLUE_MASK_THRESHOLD, 255, Imgproc.THRESH_BINARY);

        // Threshold the Cr channel to form a mask for red
        Imgproc.threshold(crMat, redThresholdMat, RED_MASK_THRESHOLD, 255, Imgproc.THRESH_BINARY);

        // Threshold the Cb channel to form a mask for yellow
        Imgproc.threshold(cbMat, yellowThresholdMat, YELLOW_MASK_THRESHOLD, 255, Imgproc.THRESH_BINARY_INV);

        // Apply morphology to the masks
        morphMask(blueThresholdMat, morphedBlueThreshold);
        morphMask(redThresholdMat, morphedRedThreshold);
        morphMask(yellowThresholdMat, morphedYellowThreshold);
        ArrayList<MatOfPoint> blueContoursList = new ArrayList<>();
        Imgproc.findContours(morphedBlueThreshold, blueContoursList, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE);

        ArrayList<MatOfPoint> redContoursList = new ArrayList<>();
        Imgproc.findContours(morphedRedThreshold, redContoursList, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE);

        ArrayList<MatOfPoint> yellowContoursList = new ArrayList<>();
        Imgproc.findContours(morphedYellowThreshold, yellowContoursList, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE);

        Imgproc.drawContours(input, yellowContoursList, -1, new Scalar(0, 0, 255), 1, 1);
        Imgproc.drawContours(input, blueContoursList, -1, new Scalar(0, 255, 0), 1, 1);
        Imgproc.drawContours(input, redContoursList, -1, new Scalar(255, 0, 0), 1, 1);

        for (MatOfPoint contour : blueContoursList) {
            analyzeContour(contour, input, "Blue");

        }

        for (MatOfPoint contour : redContoursList) {
            analyzeContour(contour, input, "Red");
        }

        for (MatOfPoint contour : yellowContoursList) {
            analyzeContour(contour, input, "Yellow");
        }
        DrawScreenAxes(input, "Red");

        //get the approximate position of the nearest sample
//        BlueCoords = getPoseOfClosestPixel(morphedBlueThreshold,cameraMatrix, camAngle, camHeight);
//        RedCoords = getPoseOfClosestPixel(morphedRedThreshold,cameraMatrix, camAngle, camHeight);
//        YellowCoords = getPoseOfClosestPixel(morphedYellowThreshold,cameraMatrix, camAngle, camHeight);
    }

    void analyzeContour(MatOfPoint contour, Mat input, String color)
    {

        // Transform the contour to a different format
        Point[] points = contour.toArray();
        MatOfPoint2f contour2f = new MatOfPoint2f(points);

        // Fit a rotated rectangle to the contour and draw it
        RotatedRect rotatedRectFitToContour = Imgproc.minAreaRect(contour2f);
        drawRotatedRect(rotatedRectFitToContour, input, color);
        drawRotatedRect(rotatedRectFitToContour, contoursOnPlainImageMat, color);

        // Adjust the angle based on rectangle dimensions
        double rotRectAngle = rotatedRectFitToContour.angle;
        if (rotatedRectFitToContour.size.width < rotatedRectFitToContour.size.height) {
            rotRectAngle += 90;
        }

        // Compute the angle and store it
        double angle = -(rotRectAngle - 180);
        drawTagText(rotatedRectFitToContour, (int) Math.round(angle) + " deg", input, color);

        // Store the detected stone information
        AnalyzedStone analyzedStone = new AnalyzedStone();
        analyzedStone.rect = rotatedRectFitToContour;
        analyzedStone.angle = rotRectAngle;
        analyzedStone.color = color;
        analyzedStone.setCoordsOnScreen(new Point((rotatedRectFitToContour.center.x - cx) / fx, -(rotatedRectFitToContour.center.y - cy) / fy));
        analyzedStone.setPos(getCoordOnFloorFromCoordOnScreen(rotatedRectFitToContour.center));
        analyzedStone.width = getApriximateRealWidth(analyzedStone);
        analyzedStone.length = getApriximateRealLength(analyzedStone);
        internalStoneList.add(analyzedStone);
    }
}
