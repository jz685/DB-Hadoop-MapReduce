import java.io.*; // DataInput/DataOuput
import java.util.ArrayList;
import java.util.Collections;
import org.apache.hadoop.io.*; // Writable

import java.nio.file.Files;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * A Point is some ordered list of floats.
 * 
 * A Point implements WritableComparable so that Hadoop can serialize
 * and send Point objects across machines.
 *
 * NOTE: This implementation is NOT complete.  As mentioned above, you need
 * to implement WritableComparable at minimum.  Modify this class as you see fit.
 */
public class Point {

    float[] coords;

    /**
     * Construct a Point with the given dimensions [dim]. The coordinates should all be 0.
     * For example:
     * Constructing a Point(2) should create a point (x_0 = 0, x_1 = 0)
     */
    public Point(int dim)
    {
        coords = new float[dim];
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
        String[] parts = str.split(" ");
		
		coords = new float[parts.length];
		for (int i = 0; i < parts.length; i ++){
			//System.out.println(parts[i]);
			coords[i] = Float.parseFloat(parts[i]);
		}
    }

    /**
     * Copy constructor
     */
    public Point(Point other)
    {
        this.coords = new float[other.coords.length];
		for (int i = 0; i < coords.length; i++){
			this.coords[i] = other.coords[i];
		}
    }

    /**
     * @return The dimension of the point.  For example, the point [x=0, y=1] has
     * a dimension of 2.
     */
    public int getDimension()
    {
        return coords.length;
    }

    /**
     * Converts a point to a string.  Note that this must be formatted EXACTLY
     * for the autograder to be able to read your answer.
     * Example:
     * Given a point with coordinates {x=1, y=1, z=3}
     * Return the string "1 1 3"
     */
    public String toString()
    {
        StringBuilder result = new StringBuilder();
		for (int i = 0; i < coords.length; i++){
			result.append(coords[i]);
			if(i < (coords.length - 1)) result.append(" ");
		}
        return result.toString();
    }

    /**
     * One of the WritableComparable methods you need to implement.
     * See the Hadoop documentation for more details.
     * You should order the points "lexicographically" in the order of the coordinates.
     * Comparing two points of different dimensions results in undefined behavior.
     */
	// ?? why Object?
    public int compareTo(Object o)
    {   
        float[] thisArray = this.coords;
		float[] thatArray = ((Point)o).coords;
		if (thisArray.length != thatArray.length) throw new IllegalArgumentException("Different dimensions");
		for (int i = 0; i < thisArray.length; i ++){
			if (thisArray[i] < thatArray[i]) return -1;
			if (thisArray[i] > thatArray[i]) return  1;
		}
        return 0;
    }

    /**
     * @return The L2 distance between two points.
     */
    public static final float distance(Point x, Point y)
    {
		float[] xcoords = x.coords;
		float[] ycoords = y.coords;
		float result = 0;
		if (xcoords.length != ycoords.length) throw new IllegalArgumentException("Different dimensions");
        float[] diffs = new float[xcoords.length];
		for(int i = 0; i < diffs.length; i ++){
			diffs[i] = Math.abs(xcoords[i] - ycoords[i]);
		}
		for(int i = 0; i < diffs.length; i ++){
			result += diffs[i] * diffs[i];
		}
		
        return (float)Math.sqrt(result);
    }

    /**
     * @return A new point equal to [x]+[y]
     */
    public static final Point addPoints(Point x, Point y)
    {
        float[] xcoords = x.coords;
		float[] ycoords = y.coords;
		if (xcoords.length != ycoords.length) throw new IllegalArgumentException("Different dimensions");

		Point result = new Point(xcoords.length);
		for(int i = 0; i < xcoords.length; i ++){
			result.coords[i] = (xcoords[i] + ycoords[i]);
		}
        return result;
    }

    /**
     * @return A new point equal to [c][x]
     */
	// USE FLOAT
    public static final Point multiplyScalar(Point x, float c)
    {
        float[] xcoords = x.coords;

		Point result = new Point(xcoords.length);
		for(int i = 0; i < xcoords.length; i ++){
			result.coords[i] = xcoords[i] * c;
		}
        return result;
    }


	public static List<String> readTxtFile(String aFileName) throws IOException {
    	Path path = Paths.get(aFileName);
    	return Files.readAllLines(path, StandardCharsets.UTF_8);
	}
//*
//	// For Testing Purpose
//	public static void main(String[] args){
//		Point p0 = null;
//		p0 = new Point("1 2");
//		System.out.println("Print p0: " + p0.toString());
//		//
//		Point p1 = new Point(3);
//		Point p2 = new Point("1 2 3 4");
//		Point p3 = new Point(p2);
//		Point p4 = new Point(4);
//		//System.out.println("Dim of p1: " + p1.getDimension());
//		//System.out.println("Print p2: " + p2.toString());
//		//System.out.println("Compare p2 to p4: " + p2.compareTo(p4));
//		//System.out.println("Compare p2 to p1: " + p2.compareTo(p1));
//		//System.out.println("Distance between p2 and p4 is: " + distance(p2, p4));
//		//System.out.println("Print p2 + p4: " + addPoints(p4, p2));
//		//System.out.println("Print p2 * 2: " + multiplyScalar(p2, 2));
//	}
//

}
