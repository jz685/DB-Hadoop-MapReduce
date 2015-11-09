import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/** 
 * You can modify this class as you see fit, as long as you correctly update the
 * global centroids.
 */
public class ClusterToPointReducer extends Reducer<Text, Text, Text, Text>
{

	public void reduce (int idIndex, Iterable<Point> pointItr, Context context){
		int counter = 0;
		Point sumPoint = null;
		for (Point temp : pointItr){
			if (sumPoint == null){
				sumpoint = new Point(temp);
			} else {
				sumpoint = Point.addPoints(sumPoint, temp);
			}
			counter ++;
		}
		// change global variable
		KMeans.centroids[idIndex] = Point.multiplyScalar(sumpoint, 1 / counter);
		
	}
}
