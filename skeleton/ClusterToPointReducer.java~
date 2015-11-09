import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.util.ArrayList;

import java.io.IOException;

/** 
 * You can modify this class as you see fit, as long as you correctly update the
 * global centroids.
 */
public class ClusterToPointReducer extends Reducer<Text, Text, Text, Text>
{

	public void reduce (Text idIndex, Iterable<Text> pointItr, Context context){
		int counter = 0;
		Point sumPoint = null;

		//Testing
		System.out.println("<RED> value of <Text>index: " + idIndex.toString());

		int index = Integer.parseInt(idIndex.toString());

		for (Text temp : pointItr){
			Point tempPoint = new Point(temp.toString());
			if (sumPoint == null){
				sumPoint = new Point(tempPoint);
			} else {
				sumPoint = Point.addPoints(sumPoint, tempPoint);
			}
			counter ++;
		}
		// change global variable
		KMeans.centroids.set(index, Point.multiplyScalar(sumPoint, 1 / counter));
		
	}
}
