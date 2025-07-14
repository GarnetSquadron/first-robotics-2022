package org.firstinspires.ftc.teamcode.depricated.IntoTheDeepStuff.cv.Pipelines;

import static org.firstinspires.ftc.teamcode.ExtraMath.ApproximatelyEqualTo;

import com.acmerobotics.roadrunner.Pose2d;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.calib3d.Calib3d;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.MatOfPoint3f;
import org.opencv.core.Point;
import org.opencv.core.Point3;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;

/**
 * This contains methods that are useful for making pipelines
 */

public abstract class SamplePipeline extends OpenCvPipeline
{
    //region Our working image buffers
    Mat ycrcbMat = new Mat();
    Mat crMat = new Mat();
    Mat cbMat = new Mat();
    Mat grayScaleMat = new Mat();

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
    public static int RED_MASK_THRESHOLD = 180;
    //endregion

    //region The elements we use for noise reduction
    Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3.5, 3.5));
    Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3.5, 3.5));
    //endregion


    static final int CONTOUR_LINE_THICKNESS = 2;

    //region AnalyzedStone
    public static class AnalyzedStone
    {
        public RotatedRect rect;
        double angle;
        String color;
        Mat rvec;
        Mat tvec;
        Point Pos;
        Point CoordsOnScreen;
        /**
         * short side
         */
        public double width;
        /**
         * long side
         */
        public double length;

        public String getColor()
        {
            return color;
        }

        public double getAngleRad()
        {
            return Math.toRadians(angle);
        }

        public Point getPos()
        {
            return Pos;
        }

        public Pose2d getPose2d()
        {
            return new Pose2d(getPos().x, getPos().y, getAngleRad());
        }

        public void setPos(Point p)
        {
            Pos = p;
        }

        public void setCoordsOnScreen(Point p)
        {
            CoordsOnScreen = p;
        }

        public Point getCoordsOnScreen()
        {
            return CoordsOnScreen;
        }

    }

    ArrayList<AnalyzedStone> internalStoneList = new ArrayList<>();
    volatile ArrayList<AnalyzedStone> clientStoneList = new ArrayList<>();

    //endregion

    public static Mat cameraMatrix = new Mat(3, 3, CvType.CV_64FC1);
    public static MatOfDouble distCoeffs = new MatOfDouble();

    //region Focal lengths (fx, fy) and principal point (cx, cy)
    double fx = 1409.76; // camera's focal length in pixels
    double fy = 1409.76;
    static double cx = 634.365; // camera's principal point x-coordinate (usually image width / 2)
    static double cy = 354.346;//354.346; // camera's principal point y-coordinate (usually image height / 2)4
    static double k1 = 0.0490191, k2 = 0.624066, k3 = -3.46861, p1 = -0.00146273, p2 = -0.00249107;
    //endregion
    double camAngle;//angle of where the camera is pointing from vertical
    double camHeight;//height of the camera
    public Telemetry telemetry;

    SamplePipeline()
    {
        // Initialize camera parameters

        camAngle = Math.PI / 4;
        camHeight = 12;

        cameraMatrix.put(0, 0,
                fx, 0, cx,
                0, fy, cy,
                0, 0, 1);

        // Distortion coefficients (k1, k2, p1, p2, k3)
        // If you have calibrated your camera and have these values, use them
        // Otherwise, you can assume zero distortion for simplicity
        distCoeffs = new MatOfDouble(k1, k2, p1, p2, k3);
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
        switch (color) {
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
        CONTOURS
    }

    //region perspective math
    Point getLowestPixel(Mat input)
    {
        Rect rect = Imgproc.boundingRect(input);
        int y = rect.y - rect.height + 1;
        int firstx = 0;
        try {
            for (int i = 0; i < input.width(); i++) {


                if (input.get(i, y)[0] == 1) {
                    return new Point(i, y);
                }


            }
        } catch (NullPointerException e) {

        }
        return new Point(0, 0);

    }

    Point getHighestPixel(Mat input)
    {
        Rect rect = Imgproc.boundingRect(input);
        int y = rect.y - 1;
        int firstx = 0;
        for (int i = 0; i < input.width(); i++) {
            if (input.get(i, y)[0] == 1) {
                return new Point(i, y);
            }
        }
        return null;
    }

    /**
     * Like the name says, gets the coords on the floor from the coords on the screen given the
     * height and angle of the camera
     *
     * @param p      the point on the screen
     * @param angle  the angle between the line that the camera is pointing along and a line
     *               perpendicular to the ground.
     * @param height this is the height of the camera. what did you think it was lol. Also the
     *               height scales everything, so whatever units for height you input you will get
     *               those same units as the output. so inches in, inches out
     * @return
     */
    Point getCoordOnFloorFromCoordOnScreen(Point p, double angle, double height)
    {
//        double fx = CamMat.get(0,0)[0];//TODO: figure out the number in the brackets (Its prob 1 but I am not sure maybe 0)
//        double fy = CamMat.get(1,1)[0];
//        double cx = CamMat.get(0,2)[0];
//        double cy = CamMat.get(1,2)[0];

        return getCoordOnFloorFromCoordOnScreen(p, fx, fy, cx, cy, angle, height, k1, k2, k3, p1, p2);
    }

    Point getCoordOnFloorFromCoordOnScreen(Point p)
    {
//        double fx = CamMat.get(0,0)[0];//TODO: figure out the number in the brackets (Its prob 1 but I am not sure maybe 0)
//        double fy = CamMat.get(1,1)[0];
//        double cx = CamMat.get(0,2)[0];
//        double cy = CamMat.get(1,2)[0];

        return getCoordOnFloorFromCoordOnScreen(p, camAngle, camHeight);
    }

    /**
     * also doesnt work. idk
     *
     * @param x
     * @param y
     * @param xc
     * @param yc
     * @param k1
     * @param k2
     * @param k3
     * @param p1
     * @param p2
     * @return
     */
    Point undistortDivisionModel(double x, double y, double xc, double yc, double k1, double k2, double k3, double p1, double p2)
    {
        double r = Math.hypot(x - xc, y - yc);
        double denominator = 1 + k1 * Math.pow(r, 2) + k2 * Math.pow(r, 4) + k3 * Math.pow(r, 6);
        return new Point(xc + (x - xc) / denominator, yc + (y - yc) / denominator);

    }

    /**
     * doesnt work idk why
     *
     * @param x
     * @param y
     * @param xc
     * @param yc
     * @param k1
     * @param k2
     * @param k3
     * @param p1
     * @param p2
     * @return
     */
    Point undistortPolynomialModel(double x, double y, double xc, double yc, double k1, double k2, double k3, double p1, double p2)
    {
        return new Point(undistortXPolynomialModel(x, y, xc, yc, k1, k2, k3, p1, p2), undistortYPolynomialModel(x, y, xc, yc, k1, k2, k3, p1, p2));
    }

    /**
     * doesnt work idk why
     *
     * @param xd
     * @param yd
     * @param xc
     * @param yc
     * @param k1
     * @param k2
     * @param k3
     * @param p1
     * @param p2
     * @return
     */
    double undistortXPolynomialModel(double xd, double yd, double xc, double yc, double k1, double k2, double k3, double p1, double p2)
    {
        double x = xd - xc;
        double y = yd - yc;
        double r = Math.hypot(x, y);
        double term2 = (x) * (k1 * Math.pow(r, 2) + k2 * Math.pow(r, 4) + k3 * Math.pow(r, 6));
        double term3 = p1 * (Math.pow(r, 2) + 2 * Math.pow(x, 2)) + 2 * p2 * x * y;
        return xd + term2 + term3;
    }

    /**
     * doesnt work idk why
     *
     * @param xd
     * @param yd
     * @param xc
     * @param yc
     * @param k1
     * @param k2
     * @param k3
     * @param p1
     * @param p2
     * @return
     */
    double undistortYPolynomialModel(double xd, double yd, double xc, double yc, double k1, double k2, double k3, double p1, double p2)
    {
        return undistortXPolynomialModel(yd, xd, yc, xc, k1, k2, k3, p2, p1);
    }

    Point getCoordOnFloorFromCoordOnScreen(Point p, double fx, double fy, double cx, double cy, double angle, double height, double k1, double k2, double k3, double p1, double p2)
    {
        if (p != null) {
            //Point up = undistortDivisionModel(p.x,p.y,cx,cy,k1,k2,k3,p1,p2);//does not seem to work idk
            Point q = new Point((p.x - cx) / fx, -(p.y - cy) / fx);
            double cos = Math.cos(angle);
            double sin = Math.sin(angle);
            double tan = Math.tan(angle);
            double Z = height / (cos - q.y * sin);
            double Y = Z * q.y;
            double ZZ = Z - height * cos;
            double YY = Y + height * sin;
            double y = (angle == 0) ? (YY / cos) : (ZZ / sin);
            double x = q.x * Z;
            return new Point(x, y);
            ///////////////////////////////////////////////////////////
//            double y=height*(q.y+tan)/(1-q.y*tan);
//            double Z=Math.hypot(y,height);
//            double x=q.x*Z;
//            return new Point(x,y);
        } else {
            return null;
        }
    }

    double getCamZ(double y, double h, double angle)
    {
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        double tan = Math.tan(angle);
        double L = h * tan;
        double H = h / cos;
        double l = L - y;
        double n = l * sin;
        return H - n;
    }

    double getscale(double y)
    {
        return getscale(getCamZ(y, camHeight, camAngle), fx);
    }

    /**
     * returns how small objects should appear compared to their actual size at a given point
     *
     * @param y      the distance of the object on the ground to the camera in inches
     * @param height the height of the camera
     * @param angle  the angle of the camera
     * @param f
     * @return
     */
    double getscale(double y, double height, double angle, double f)
    {
        return getscale(getCamZ(y, height, angle), f);
    }

    /**
     * returns how small objects should appear compared to their actual size at a given point
     *
     * @param Z distance from the plane parrallel to the camera that the object lies in
     * @param f focal length in pixels
     * @return
     */
    double getscale(double Z, double f)
    {
        return Z / f;
    }


    double getApriximateRealWidth(AnalyzedStone sample)
    {
        RotatedRect rectangle = sample.rect;
        return Math.min(rectangle.size.height, rectangle.size.width) * getscale(sample.Pos.y);
    }

    double getApriximateRealLength(AnalyzedStone sample)
    {
        RotatedRect rectangle = sample.rect;
        return Math.max(rectangle.size.height, rectangle.size.width) * getscale(sample.Pos.y);
    }

    /**
     * checks whether a given sample is
     *
     * @param sample
     * @param expectedWidth
     * @param expectedLength
     * @param tolerance
     * @return
     */
    boolean CheckScale(AnalyzedStone sample, double expectedWidth, double expectedLength, double tolerance)
    {
        RotatedRect rectangle = sample.rect;
        return ApproximatelyEqualTo(sample.length, expectedWidth, tolerance) && ApproximatelyEqualTo(sample.length, expectedLength, tolerance);
    }

    Point getPoseOfClosestPixel(Mat input, double angle, double height)
    {
        return getCoordOnFloorFromCoordOnScreen(getLowestPixel(input), angle, height);
    }

    double getOrientation(Mat input, Mat CamMat, double angle, double height, Point3 sampleDimensions)
    {
        Point topCorner = getHighestPixel(input);
        Point bottomCorner = getLowestPixel(input);
        Point topCoords = getCoordOnFloorFromCoordOnScreen(topCorner, angle, height);
        Point bottomCoords = getCoordOnFloorFromCoordOnScreen(bottomCorner, angle, height - sampleDimensions.y);
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

        for (int i = 0; i < 4; i++) {
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

        for (int i = 1; i < array.length; i++) {
            if (array[i] < min) {
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

        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
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

    static void DrawScreenAxes(Mat drawOn, String color)
    {
        Imgproc.line(drawOn, new Point(0, cy), new Point(1280, cy), getColorScalar(color));
        Imgproc.line(drawOn, new Point(cx, 0), new Point(cx, 720), getColorScalar(color));
    }

    static void drawRotatedRect(RotatedRect rect, Mat drawOn, String color)
    {
        /*
         * Draws a rotated rect by drawing each of the 4 lines individually
         */

        Point[] points = new Point[4];
        rect.points(points);

        Scalar colorScalar = getColorScalar(color);

        for (int i = 0; i < 4; ++i) {
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

    public void setAngle(double angle)
    {
        camAngle = angle;
    }

    //region threshold auto tuner
    public double getMidPixelBrightness(Mat input)
    {
        Imgproc.cvtColor(input, grayScaleMat, Imgproc.COLOR_BGR2GRAY);
        return grayScaleMat.get((int) Math.round(cx), (int) Math.round(cy))[0];
    }
    //endregion
}

