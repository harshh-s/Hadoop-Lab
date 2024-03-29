
package MapReduceJoin;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.mapred.lib.MultipleInputs;
import org.apache.hadoop.util.*;


public class JoinDriver extends Configured implements Tool {

	public static class KeyPartitioner implements Partitioner<TextPair, Text> {
    		@Override
    		public void configure(JobConf job) {}
    
    		@Override
    		public int getPartition(TextPair key, Text value, int numPartitions) {
      			return (key.getFirst().hashCode() & Integer.MAX_VALUE) % numPartitions;
    		}
  	}
	
	@Override
	public int run(String[] args) throws Exception {

		if (args.length != 3) {
			System.out.println("Usage: <Department Emp Strength input> <Department Name input> <output>");
			return -1;
		}

		JobConf conf = new JobConf(getConf(), getClass());
    		conf.setJobName("Join 'Department Emp Strength input' with 'Department Name input'");

		Path AInputPath = new Path(args[0]);
		Path BInputPath = new Path(args[1]);
		Path outputPath = new Path(args[2]);

		MultipleInputs.addInputPath(conf, AInputPath, TextInputFormat.class, DeptNameMapper.class);
		MultipleInputs.addInputPath(conf, BInputPath, TextInputFormat.class, DeptEmpStrengthMapper.class);

		FileOutputFormat.setOutputPath(conf, outputPath);

		conf.setPartitionerClass(KeyPartitioner.class);
    		conf.setOutputValueGroupingComparator(TextPair.FirstComparator.class);
    
    		conf.setMapOutputKeyClass(TextPair.class);
    
   		conf.setReducerClass(JoinReducer.class);

    		conf.setOutputKeyClass(Text.class);
    
    		JobClient.runJob(conf);

		return 0;
	}

	public static void main(String[] args) throws Exception {

		int exitCode = ToolRunner.run(new JoinDriver(), args);
		System.exit(exitCode);
	}
}

