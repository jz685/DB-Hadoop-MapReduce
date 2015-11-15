import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
//import org.apache.hadoop.mapreduce.lib.InputFormat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class UpdateJobRunner
{
    /**
     * Create a map-reduce job to update the current centroids.
     * @param jobId Some arbitrary number so that Hadoop can create a directory "<outputDirectory>/<jobname>_<jobId>"
     *        for storage of intermediate files.  In other words, just pass in a unique value for this
     *        parameter.
     * @param The input directory specified by the user upon executing KMeans, in which the points
     *        to find the KMeans point files are located.
     * @param The output directory for which to write job results, specified by user.
     * @precondition The global centroids variable has been set.
     */
    public static Job createUpdateJob(int jobId, String inputDirectory, String outputDirectory)
        throws IOException
    {
        Job job = new Job(new Configuration(), "kmeans_update_" + jobId);
        job.setJarByClass(KMeans.class);
        job.setMapperClass(PointToClusterMapper.class);
        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(Point.class);
        job.setReducerClass(ClusterToPointReducer.class);
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(Point.class);
        FileInputFormat.addInputPath(job, new Path(inputDirectory));
        FileOutputFormat.setOutputPath(job, new Path(outputDirectory + "/" + job.getJobName()));
        job.setInputFormatClass(KeyValueTextInputFormat.class);
        return job;
    }

    /**
     * Run the jobs until the centroids stop changing.
     * Let C_old and C_new be the set of old and new centroids respectively.
     * We consider C_new to be unchanged from C_old if for every centroid, c, in 
     * C_new, the L2-distance to the centroid c' in c_old is less than [epsilon].
     *
     * Note that you may retrieve publically accessible variables from other classes
     * by prepending the name of the class to the variable (e.g. KMeans.one).
     *
     * @param maxIterations   The maximum number of updates we should execute before
     *                        we stop the program.  You may assume maxIterations is positive.
     * @param inputDirectory  The path to the directory from which to read the files of Points
     * @param outputDirectory The path to the directory to which to put Hadoop output files
     * @return The number of iterations that were executed.
     */
    public static int runUpdateJobs(int maxIterations, String inputDirectory,
        String outputDirectory) throws IOException, InterruptedException, ClassNotFoundException
    {
        int iterations = 0;
        Job updateJob;
        List<Point> oldPoints = new ArrayList<>(KMeans.centroids);
        // Run Job the max iterations
        for (int i = 0; i < maxIterations; i++) {
            iterations++;
            // Create job and run
            updateJob = createUpdateJob((int)(new Date().getTime()), inputDirectory, outputDirectory);
            updateJob.waitForCompletion(true);

            // If distance does not changed for old and new centroids
            if (!centroidsChanged(oldPoints, KMeans.centroids)) {
                break;
            }
        }

        return iterations;
    }

    /**
     * Check if centroids are changed
     */
    private static boolean centroidsChanged(List<Point> oldPoints, List<Point> newPoints) {
        int dimension = oldPoints.size();
        for (int i = 0; i < dimension; i++) {
            Point oldPoint = oldPoints.get(i);
            Point newPoint = newPoints.get(i);
            if (Point.distance(oldPoint, newPoint) > 0.00001) {
                return true;
            }
        }
        return false;
    }

}
