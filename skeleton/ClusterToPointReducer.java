import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

/** 
 * You can modify this class as you see fit, as long as you correctly update the
 * global centroids.
 */
public class ClusterToPointReducer extends Reducer<IntWritable, Point, IntWritable, Text>
{
	public void reduce(IntWritable centroidIndex, Iterable<Point> points, Context context) throws IOException, InterruptedException {
		int pointsLen = 0;
		Iterator<Point> pointIterator = points.iterator();
		Point additionPoint = null;

		// Iterate all points and add together
		while (pointIterator.hasNext()) {
			pointsLen++;
			Point point = pointIterator.next();
			if (additionPoint == null) {
				additionPoint = new Point(point);
			} else {
				additionPoint = Point.addPoints(additionPoint, point);
			}
		}

		// Calculate new centroid point
		Point newCentroidPoint = Point.multiplyScalar(additionPoint, 1.0f / pointsLen);
		// Update KMeans centroid
		KMeans.centroids.set(centroidIndex.get(), newCentroidPoint);
	}
}
