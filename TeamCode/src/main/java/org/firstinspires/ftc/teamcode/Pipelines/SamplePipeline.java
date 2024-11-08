package org.firstinspires.ftc.teamcode.Pipelines;

import com.acmerobotics.dashboard.config.Config;

import org.opencv.calib3d.Calib3d;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.MatOfPoint3f;
import org.opencv.core.Rect;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.Point3;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;

/**
 * This contains methods that are useful for making pipelines
 */
@Config
public abstract class SamplePipeline extends OpenCvPipeline {
    //region Our working image buffers
    Mat ycrcbMat = new Mat();
    Mat crMat = new Mat();
    Mat cbMat = new Mat();

    Mat blueThresholdMat = new Mat();
    Mat redThresholdMat = new Mat();
    Mat yellowThresholdMat = new Mat();

    Mat morphedBlueThreshold = new Mat();
    Mat morphedRedThreshold = new Mat();
    Mat morphedYellowThreshold = new Mat();

    Mat contoursOnPlainImageMat = new Mat();
    //endregion

    //region threshold values
    static final int OriginalYELLOW_MASK_THRESHOLD = 57;
    static final int OriginalBLUE_MASK_THRESHOLD = 150;
    static final int OriginalRED_MASK_THRESHOLD = 198;
    public static int YELLOW_MASK_THRESHOLD = 80;
    public static int BLUE_MASK_THRESHOLD = 150;
    public static int RED_MASK_THRESHOLD = 200;
    //endregion

    //region The elements we use for noise reduction
    Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3.5, 3.5));
    Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3.5, 3.5));
    //endregion


    static final int CONTOUR_LINE_THICKNESS = 2;


    ArrayList<AnalyzedStone> internalStoneList = new ArrayList<>();
    volatile ArrayList<AnalyzedStone> clientStoneList = new ArrayList<>();


    public static Mat cameraMatrix = new Mat(3, 3, CvType.CV_64FC1);
    public static MatOfDouble distCoeffs = new MatOfDouble();

    //region Focal lengths (fx, fy) and principal point (cx, cy)
    double fx = 720; // camera's focal length in pixels
    double fy = 720;
    double cx = 640; // camera's principal point x-coordinate (usually image width / 2)
    double cy = 360; // camera's principal point y-coordinate (usually image height / 2)4
    //endregion
    double camAngle;//angle of where the camera is pointing from vertical
    double camHeight;//height of the camera

    SamplePipeline() {
        // Initialize camera parameters


        camAngle = 0;
        camHeight = 10;

        cameraMatrix.put(0, 0,
                fx, 0,cx,
                0,fy, cy,
                0, 0, 1);

        // Distortion coefficients (k1, k2, p1, p2, k3)
        // If you have calibrated your camera and have these values, use them
        // Otherwise, you can assume zero distortion for simplicity
        distCoeffs = new MatOfDouble(0, 0, 0, 0, 0);
    }


    public static class AnalyzedStone
    {
        double angle;
        String color;
        Mat rvec;
        Mat tvec;
        Point Pos;
        public double getAngleRad()
        {
            return Math.toRadians(angle);
        }
        public Point getPos(){
            return Pos;
        }
        public void setPos(Point p){
            Pos = p;
        }

    }
    public ArrayList<AnalyzedStone> getDetectedStones()
    {
        return clientStoneList;
    }


    //region COLORS
    static final Scalar RED = new Scalar(255, 0, 0);
    static final Scalar BLUE = new Scalar(0, 0, 255);
    static final Scalar YELLOW = new Scalar(255, 255, 0);
    //endregion

    static Scalar getColorScalar(String color)
    {
        switch (color)
        {
            case "Blue":
                return BLUE;
            case "Yellow":
                return YELLOW;
            default:
                return RED;
        }
    }
    enum Stage
    {
        FINAL,
        YCrCb,
        MASKS,
        MASKS_NR,
        CONTOURS;
    }

    //region perspective math
    Point getLowestPixel(Mat input){
        Rect rect =  Imgproc.boundingRect(input);
        int y = rect.y-rect.height+1;
        int firstx = 0;
        for(int i = 0; i<input.width(); i++){
            if(input.get(i,y)[0]==1){
                return new Point(i, y);
            }
        }
        return null;
    }
    Point getHighestPixel(Mat input){
        Rect rect =  Imgproc.boundingRect(input);
        int y = rect.y-1;
        int firstx = 0;
        for(int i = 0; i<input.width(); i++){
            if(input.get(i,y)[0]==1){
                return new Point(i, y);
            }
        }
        return null;
    }

    /**
     * Like the name says, gets the coords on the floor from the coords on the screen given the height and angle of the camera
     * @param p the point on the screen
     * @param CamMat the camera matrix
     * @param angle the angle between the line that the camera is pointing along and a line perpendicular to the ground.
     * @param height this is the height of the camera. what did you think it was lol.
     *               Also the height scales everything, so whatever
     *               units for height you input you will get those same units as the output. so inches in, inches out
     * @return
     */
    Point getCoordOnFloorFromCoordOnScreen(Point p,Mat CamMat,double angle, double height){
        double fx = CamMat.get(0,0)[1];//TODO: figure out the number in the brackets (Its prob 1 but I am not sure maybe 0)
        double fy = CamMat.get(1,1)[1];
        double cx = CamMat.get(0,2)[1];
        double cy = CamMat.get(1,2)[1];
        Point q = new Point((p.x-cy)/fx,(p.y-cy)/fx);
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double Z =  height/(cos-q.y*sin);
        double Y = Z*q.y;
        double ZZ = Z-height*cos;
        double YY = Y+height*sin;
        double y = (angle==0)?(YY/cos):(ZZ/sin);
        double x = q.x*Z;
        return new Point(x,y);
    }
    Point getPoseOfClosestPixel(Mat input, Mat CamMat,double angle, double height){
        return getCoordOnFloorFromCoordOnScreen(getLowestPixel(input),CamMat,angle,height);
    }
    double getOrientation(Mat input, Mat CamMat,  double angle,double height, Point3 sampleDimensions){
        Point topCorner = getHighestPixel(input);
        Point bottomCorner = getLowestPixel(input);
        Point topCoords = getCoordOnFloorFromCoordOnScreen(topCorner, CamMat, angle, height);
        Point bottomCoords = getCoordOnFloorFromCoordOnScreen(bottomCorner, CamMat, angle, height-sampleDimensions.y);
        //Point deltaCoords = topCoords-bottomCoords;
        return Math.atan(1);
    }
    //endregion

    //region ordering points
    static Point[] orderPoints(Point[] pts)
    {
        // Orders the array of 4 points in the order: top-left, top-right, bottom-right, bottom-left
        Point[] orderedPts = new Point[4];

        // Sum and difference of x and y coordinates
        double[] sum = new double[4];
        double[] diff = new double[4];

        for (int i = 0; i < 4; i++)
        {
            sum[i] = pts[i].x + pts[i].y;
            diff[i] = pts[i].y - pts[i].x;
        }

        // Top-left point has the smallest sum
        int tlIndex = indexOfMin(sum);
        orderedPts[0] = pts[tlIndex];

        // Bottom-right point has the largest sum
        int brIndex = indexOfMax(sum);
        orderedPts[2] = pts[brIndex];

        // Top-right point has the smallest difference
        int trIndex = indexOfMin(diff);
        orderedPts[1] = pts[trIndex];

        // Bottom-left point has the largest difference
        int blIndex = indexOfMax(diff);
        orderedPts[3] = pts[blIndex];

        return orderedPts;
    }

    static int indexOfMin(double[] array)
    {
        int index = 0;
        double min = array[0];

        for (int i = 1; i < array.length; i++)
        {
            if (array[i] < min)
            {
                min = array[i];
                index = i;
            }
        }
        return index;
    }

    static int indexOfMax(double[] array)
    {
        int index = 0;
        double max = array[0];

        for (int i = 1; i < array.length; i++)
        {
            if (array[i] > max)
            {
                max = array[i];
                index = i;
            }
        }
        return index;
    }
    //endregion

    //region drawing stuff
    static void drawTagText(RotatedRect rect, String text, Mat mat, String color)
    {
        Scalar colorScalar = getColorScalar(color);

        Imgproc.putText(
                mat, // The buffer we're drawing on
                text, // The text we're drawing
                new Point( // The anchor point for the text
                        rect.center.x - 50,  // x anchor point
                        rect.center.y + 25), // y anchor point
                Imgproc.FONT_HERSHEY_PLAIN, // Font
                1, // Font size
                colorScalar, // Font color
                1); // Font thickness
    }

    static void drawRotatedRect(RotatedRect rect, Mat drawOn, String color)
    {
        /*
         * Draws a rotated rect by drawing each of the 4 lines individually
         */

        Point[] points = new Point[4];
        rect.points(points);

        Scalar colorScalar = getColorScalar(color);

        for (int i = 0; i < 4; ++i)
        {
            Imgproc.line(drawOn, points[i], points[(i + 1) % 4], colorScalar, 2);
        }
    }
    void drawAxis(Mat img, Mat rvec, Mat tvec, Mat cameraMatrix, MatOfDouble distCoeffs)
    {
        // Length of the axis lines
        double axisLength = 5.0;

        // Define the points in 3D space for the axes
        MatOfPoint3f axisPoints = new MatOfPoint3f(
                new Point3(0, 0, 0),
                new Point3(axisLength, 0, 0),
                new Point3(0, axisLength, 0),
                new Point3(0, 0, -axisLength) // Z axis pointing away from the camera
        );

        // Project the 3D points to 2D image points
        MatOfPoint2f imagePoints = new MatOfPoint2f();
        Calib3d.projectPoints(axisPoints, rvec, tvec, cameraMatrix, distCoeffs, imagePoints);

        Point[] imgPts = imagePoints.toArray();

        // Draw the axis lines
        Imgproc.line(img, imgPts[0], imgPts[1], new Scalar(0, 0, 255), 2); // X axis in red
        Imgproc.line(img, imgPts[0], imgPts[2], new Scalar(0, 255, 0), 2); // Y axis in green
        Imgproc.line(img, imgPts[0], imgPts[3], new Scalar(255, 0, 0), 2); // Z axis in blue
    }

    //endregion
    void morphMask(Mat input, Mat output)
    {
        /*
         * Apply some erosion and dilation for noise reduction
         */

        Imgproc.erode(input, output, erodeElement);
        Imgproc.erode(output, output, erodeElement);

        Imgproc.dilate(output, output, dilateElement);
        Imgproc.dilate(output, output, dilateElement);
    }
}

