import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * You can modify this class as you see fit.  You may assume that the global
 * centroids have been correctly initialized.
 */
public class PointToClusterMapper extends Mapper<Text, Text, Text, Text>
{
	
	
	public void map(Point inputPoint, Text value, Context context) {

		// Assume KMeans.centroids is a array of Point
		Point[] myCentroIds = KMeans.centroids;

		// Assuming KMeans.centroids has length > 1
		float dist = Point.distance(inputPoint, KMeans.centroids[0]);

		int idIndex = 0;
		for (int i = 1; i < myCentroIds.length; i ++){
			if (Point.distance(inputPoint, KMeans.centroids[0]) > dist){
				idIndex = i;
			}
		}
		// Emit Type: (int, Point)
		context.write(idIndex, inputPoint);
	}
}