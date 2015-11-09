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
	public Text thepoint = new Text();
	public Text index = new Text();

	public void map(Point inputPoint, Text value, Context context) throws IOException, InterruptedException{

		// Assuming KMeans.centroids has length > 1
		float dist = Point.distance(inputPoint, KMeans.centroids.get(0));

		int idIndex = 0;
		for (int i = 1; i < KMeans.centroids.size(); i ++){
			if (Point.distance(inputPoint, KMeans.centroids.get(i)) > dist){
				idIndex = i;
			}
		}
		thepoint.set(inputPoint.toString());
		index.set(String.valueOf(idIndex));
		context.write(index, thepoint);
	}
}
