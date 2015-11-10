import com.google.common.annotations.VisibleForTesting;
import org.apache.hadoop.io.file.tfile.ByteArray;
import org.junit.*;

import java.io.*;

import static org.junit.Assert.*;

public class HelperTests
{
    @Test
    public void distance_test_1()
    {
        // MyClass is tested
        Point p1 = new Point("0 0 0 0 0");
        Point p2 = new Point("3.0 3 3 0 3");

        // Tests
        assertEquals(6.0, Point.distance(p1, p2), 0.0001);
    }

    @Test
    public void distance_test_2()
    {
        Point p1 = new Point("2 2");
        Point p2 = new Point("2 2");

        // Tests
        assertEquals(0.0, Point.distance(p1, p2), 0.0001);
    }

    @Test
    public void distance_test_3()
    {
        Point p1 = new Point("2 2 4");
        Point p2 = new Point("2 2 0");

        // Tests
        assertEquals(4.0, Point.distance(p1, p2), 0.0001);
    }

    @Test
    public void point_add_test1()
    {
        Point p1 = new Point("0 0 0");
        Point p2 = new Point("1 2 2");
        Point p3 = new Point("2 1 1");
    
        assertEquals("1.0 2.0 2.0", Point.addPoints(p1, p2).toString());
        assertEquals("3.0 3.0 3.0", Point.addPoints(p2, p3).toString());
    }

    @Test
    public void point_mult_test1()
    {
        Point p1 = new Point("0 0 0");
        Point p2 = new Point("1 2 2");
        Point p3 = new Point("2 1 1");
    
        assertEquals("0.0 0.0 0.0", Point.multiplyScalar(p1, 3).toString());
        assertEquals("2.5 5.0 5.0", Point.multiplyScalar(p2, (float)2.5).toString());
    }

    @Test
    public void point_additional_test1() {
        Point p1 = new Point(3);
        Point p2 = new Point("1.23 2.46 3.52 4.79");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DataOutputStream dataOut = null;
        DataInputStream dataIn = null;
        try {
            dataOut = new DataOutputStream(out);
            p2.write(dataOut);
            byte[] byteOutput = out.toByteArray();
            ByteArrayInputStream in = new ByteArrayInputStream(byteOutput);
            dataIn = new DataInputStream(in);
            p1.readFields(dataIn);
            assertEquals(p1.hashCode(), p2.hashCode());
            assertEquals("1.23 2.46 3.52 4.79", p2.toString());
            assertEquals("1.23 2.46 3.52 4.79", p1.toString());
            assertEquals(p1.hashCode(), p2.hashCode());
        } catch (IOException ex) {
            System.out.println("IO Exception");
        }
    }

    @Test
    public void point_compare_test1() {
        Point p1 = new Point("1.23 2.46 3.52 4.80");
        Point p2 = new Point("1.23 2.46 3.52 4.79");
        Point p3 = new Point("1.23 2.46 3.52 4.79");
        Point p4 = new Point("1.23 2.46 3.42 4.79");
        assertEquals((p2.compareTo(p3) == 0), true);
        assertEquals((p1.compareTo(p2) == 1), true);
        assertEquals((p4.compareTo(p3) == -1), true);
    }
} 
