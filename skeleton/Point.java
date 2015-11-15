import java.io.*; // DataInput/DataOuput
import java.io.DataInput;
import java.io.IOException;
import java.lang.IllegalArgumentException;
import java.lang.Override;
import java.lang.System;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import org.apache.hadoop.io.*; // Writable


/**
 * A Point is some ordered list of floats.
 * 
 * A Point implements WritableComparable so that Hadoop can serialize
 * and send Point objects across machines.
 *
 * NOTE: This implementation is NOT complete.  As mentioned above, you need
 * to implement WritableComparable at minimum.  Modify this class as you see fit.
 */
public class Point implements WritableComparable<Point> {

    /* Dimension */
    private int DIMENSION;
    /* POINTS array */
    private float[] POINTS;
    /* Hashcode uses String hashCode */
    private int hash;

    /**
     * Constructing an empty three dimension point
     */
    public Point() {
        // Set dimension to 3
        this.DIMENSION = 3;
        this.POINTS = new float[3];
        // Initialize hashcode to 0
        this.hash = 0;
        // Initialize Points to 0.0
        for (int i = 0; i < this.DIMENSION; i++) {
            this.POINTS[i] = 0.0f;
        }
    }

    /**
     * Construct a Point with the given dimensions [dim]. The coordinates should all be 0.
     * For example:
     * Constructing a Point(2) should create a point (x_0 = 0, x_1 = 0)
     */
    public Point(int dim)
    {
        // If dimension less equal to 0
        if (dim <= 0) {
            throw new IllegalArgumentException("Dimension <= 0");
        }

        // Set dimension to dim
        this.DIMENSION = dim;
        this.POINTS = new float[dim];
        // Initialize hashcode to 0
        this.hash = 0;
        // Initialize Points to 0.0
        for (int i = 0; i < this.DIMENSION; i++) {
            this.POINTS[i] = 0.0f;
        }
    }

    /**
     * Construct a point from a properly formatted string (i.e. line from a test file)
     * @param str A string with coordinates that are space-delimited.
     * For example: 
     * Given the formatted string str="1 3 4 5"
     * Produce a Point {x_0 = 1, x_1 = 3, x_2 = 4, x_3 = 5}
     */
    public Point(String str)
    {
        // Validity Check
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("Point String malformated");
        }

        // Obtain space-delimited points list
        String[] pointsStr = str.split(" ");

        // If splited list is not correct
        if (pointsStr.length == 0) {
            throw new IllegalArgumentException("Points List malformated");
        }
        // Initialize POINTS and DIMENSION
        this.DIMENSION = pointsStr.length;
        this.POINTS = new float[this.DIMENSION];
        // Initialize hash to str's hashCode
        this.hash = str.hashCode();

        // Add coordinate to the POINTS list
        for (int i = 0; i < this.DIMENSION; i++) {
            // Parse String to a float
            float coordinate = Float.parseFloat(pointsStr[i]);
            this.POINTS[i] = coordinate;
        }

    }

    /**
     * Construct a point from a Float list
     * @param Float coordinate list.
     * Given the Float list List<Float>=[1.0, 2.0, 3.1]
     * Produce a Point {x_0=1.0, x_1=2.0, x_2=3.1}
     */
    public Point(float[] pointsList) {
        if (pointsList == null || pointsList.length == 0) {
            throw new IllegalArgumentException("Points List malformated");
        }
        this.DIMENSION = pointsList.length;
        this.POINTS = Arrays.copyOf(pointsList, pointsList.length);
        this.hash = 0;
    }

    /**
     * Copy constructor
     */
    public Point(Point other)
    {
        // Copy other's dimension and points
        this.DIMENSION = other.getDimension();
        // This returns a new point list
        this.POINTS = other.getPointsList();
        this.hash = other.hashCode();
    }

    /**
     * @return The dimension of the point.  For example, the point [x=0, y=1] has
     * a dimension of 2.
     */
    public int getDimension()
    {
        return this.DIMENSION;
    }

    /**
     * Converts a point to a string.  Note that this must be formatted EXACTLY
     * for the autograder to be able to read your answer.
     * Example:
     * Given a point with coordinates {x=1, y=1, z=3}
     * Return the string "1 1 3"
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        int size = this.POINTS.length;

        for (int i = 0; i < size; i++) {
            sb.append(this.POINTS[i]);
            if (i != size - 1) {
                sb.append(" ");
            }
        }

        return sb.toString();
    }

    /**
     * One of the WritableComparable methods you need to implement.
     * See the Hadoop documentation for more details.
     * You should order the points "lexicographically" in the order of the coordinates.
     * Comparing two points of different dimensions results in undefined behavior.
     */
    public int compareTo(Point o)
    {
        // Only compares if o is a Point
        Point other = o;
        // Find the max dimension
        int maxDim = Math.max(other.getDimension(), this.getDimension());
        float[] thisPointList = this.POINTS;
        float[] otherPoints = other.getPointsList();
        int otherSize = otherPoints.length;

        // If dimension is different, we assume other coodinates are 0.0 for lower dimension point
        for (int i = 0; i < maxDim; i++) {
            float thisCoordinate = i < this.getDimension() ? thisPointList[i] : 0.0f;
            float otherCoordinate = i < otherSize ? otherPoints[i] : 0.0f;
            if (thisCoordinate < otherCoordinate) {
                return -1;
            } else if (thisCoordinate > otherCoordinate) {
                return 1;
            } else {
                continue;
            }
        }
        return 0;
    }

    /**
     * @return The L2 distance between two points.
     */
    public static final float distance(Point x, Point y)
    {
        float distance = 0.0f;
        float squareSum = 0.0f;
        // Obtain the max dimension of two points
        int maxDimension = Math.max(x.getDimension(), y.getDimension());
        // Obtain xPoint list and yPoint list
        float[] xPoint = x.getPointsList();
        int xSize = xPoint.length;
        float[] yPoint = y.getPointsList();
        int ySize =  yPoint.length;

        // Calculate Sum of (xCoordinate - yCoordinate)^2
        for (int i = 0; i < maxDimension; i++) {
            float xCoordinate = i < xSize ? xPoint[i] : 0.0f;
            float yCoordinate = i < ySize ? yPoint[i] : 0.0f;
            float squareCoordinate = (float) Math.pow(xCoordinate - yCoordinate, 2);
            squareSum += squareCoordinate;
        }

        // Distance is the square root of the sum
        distance = (float) Math.sqrt(squareSum);
        return distance;
    }

    /**
     * @return A new point equal to [x]+[y]
     */
    public static final Point addPoints(Point x, Point y)
    {
        // Validity check
        if (x == null || y == null) {
            return null;
        }
        // Obtain the max dimension of two points
        int maxDimension = Math.max(x.getDimension(), y.getDimension());
        // Obtain xPoint list and yPoint list
        float[] xPoint = x.getPointsList();
        int xSize = xPoint.length;
        float[] yPoint = y.getPointsList();
        int ySize =  yPoint.length;
        // A new points list for the new point
        float[] pointsList = new float[maxDimension];

        for (int i = 0; i < maxDimension; i++) {
            float xCoordinate = i < xSize ? xPoint[i] : 0.0f;
            float yCoordinate = i < ySize ? yPoint[i] : 0.0f;
            pointsList[i] = xCoordinate + yCoordinate;
        }
        return new Point(pointsList);
    }

    /**
     * @return A new point equal to [c][x]
     */
    public static final Point multiplyScalar(Point x, float c)
    {
        float[] pointsList = x.getPointsList();
        int size = pointsList.length;
        float[] multiplePointsList = new float[size];

        for (int i = 0; i < size; i++) {
            float multiplyResult = c * pointsList[i];
            multiplePointsList[i] = multiplyResult;
        }

        return new Point(multiplePointsList);
    }

    /**
     * WritableComparable method hashcode
     * We use String hashcode to ensure on different machine, hashcodes are the same
     * @return String hashCode
     */
    @Override
    public int hashCode() {
        // Return hash if it calculated before
        if (this.hash != 0) {
            return this.hash;
        }
        // Get serialized string's hashCode
        this.hash = this.toString().hashCode();
        return this.hash;
    }

    /**
     *  WritableComparable method write
     * Write Int as Dimension and write doubles from POINTS
     */
    @Override
    public void write(DataOutput out) throws IOException {
        // Write DIMENSION
        out.writeInt(this.DIMENSION);
        // Write all points
        for (int i = 0; i < this.DIMENSION; i++) {
            out.writeFloat(this.POINTS[i]);
        }
    }

    /**
     * WritableComparable readFields
     * @param in
     * @throws IOException
     */
    @Override
    public void readFields(DataInput in) throws IOException {
        // Read String from the input
        int dimension = in.readInt();

        // Initialize POINTS and DIMENSION
        float[] newPoints = new float[dimension];
        // Read floats from input
        for (int i = 0; i < dimension; i++) {
            newPoints[i] = in.readFloat();
        }

        // Initialize dimension points and hash
        this.DIMENSION = dimension;
        this.POINTS = newPoints;
        this.hash = 0;
    }

    /**
     * Writable method read
     * @param in
     * @return A new instance of Point
     * @throws IOException
     */
    public static Point read(DataInput in) throws IOException {
        Point point = new Point(3);
        point.readFields(in);
        return point;
    }

    /**
     * Get copied Points list
     * @return A List contains all coordinates
     */
    public float[] getPointsList() {
        return Arrays.copyOf(this.POINTS, this.POINTS.length);
    }
}
