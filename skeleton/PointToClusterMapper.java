import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.List;

/**
 * You can modify this class as you see fit.  You may assume that the global
 * centroids have been correctly initialized.
 */
public class PointToClusterMapper extends Mapper<Text, Text, IntWritable, Point>
{
	public void map(Text key, Text value, Context context) throws IOException, InterruptedException {

		float distance = 0;
		float minDistance = Float.MAX_VALUE;
		List<Point> centroids = KMeans.centroids;
		int minCentroidIndex = -1;
		int pointLen = centroids.size();
		Point point = new Point(key.toString());

		// Iterate centroid points to find the closest centroid index
		for (int i = 0; i < pointLen; i++) {
			Point centroidPoint = centroids.get(i);
			distance = Point.distance(centroidPoint, point);
			if (distance < minDistance) {
				minDistance = distance;
				minCentroidIndex = i;
			}
		}
		
		// Write min Index and corresponding point to the context
		IntWritable centroidIndexWritable = new IntWritable(minCentroidIndex);
		context.write(centroidIndexWritable, point);
	}
}
