import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat; //part-r-00000
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
//======================================================================================================
public class MaxMin {

	public static class maxminMapper extends Mapper<LongWritable, Text, Text, DoubleWritable> {
		Text t1 = new Text();
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			String[] colvalue = value.toString().split(",");
				for (int i = 0; i < colvalue.length; i++) {
				t1.set(String.valueOf(i + 1));
				context.write(t1, new DoubleWritable(Double.parseDouble(colvalue[i])));
				}
		} 
	}// end of maxminMapper class

	public static class maxminReducer extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {
		public void reduce(Text key, Iterable<DoubleWritable> values, Context context)throws IOException,InterruptedException{

			double min = Integer.MAX_VALUE; //(2^31-1 = 2147483647)
			double max = 0;
			Iterator<DoubleWritable> iterator = values.iterator(); //Iterating
			while (iterator.hasNext()) 
			{
				double value = iterator.next().get();	//100
				if (value < min) 		
				{ //Finding min value
				min = value;	//100
				} //end of if
				//=======================================
				if (value > max) //100
				{ //Finding max value
				max = value;	//100
				} // end of if
			}// end of while
			context.write(new Text(key), new DoubleWritable(min));
			context.write(new Text(key), new DoubleWritable(max));
		} //end of reduce method
	} //end of maxminReducer class

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = new Job(conf, "Find Minimum and Maximum");
		job.setJarByClass(MaxMin.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(DoubleWritable.class);
		job.setMapperClass(maxminMapper.class);
		job.setReducerClass(maxminReducer.class);
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}//end of main() function
} // end MaxMin Class
