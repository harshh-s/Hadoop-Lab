import java.io.BufferedReader;
import java.util.*;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;


public class Apriori {
	public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> 
{
     private final static IntWritable one = new IntWritable();
     private Text word = new Text();
     private String t = new String();
     private String subsets = new String();
     
     
     //Get conf from main function
   //  static Configuration test;
     
   /*  public void setup(Context context) throws IOException
     {
    	   //Add Feature for read file
         Path pt = new Path("DataFile.txt");
         FileSystem fs = FileSystem.get(new Configuration());
         BufferedReader br = new BufferedReader(new InputStreamReader(fs.open(pt)));
         String line;
         line = br.readLine();
         while(line!=null)
         {
        	   
         }
         //end
     }*/
     
    
     public void map(Object key, Text value, Context context) throws IOException, InterruptedException 
      {
    	    
    	   // Path pt = new Path("hdfs:/ethonwu/A.txt");
    	    //FileSystem fs = FileSystem.get(test);
    	    //FSDataOutputStream fsout = fs.create(pt);
    	    Map<String, List<String>> tmpList = new HashMap<String, List<String>>(); 
    	    
        StringTokenizer itr = new StringTokenizer(value.toString());
        while (itr.hasMoreTokens())
          {
              t = itr.nextToken().toString();
              int list_l = t.length();
              StringTokenizer st = new StringTokenizer(t, ",");
              int[] list = new int[list_l];
              int k = 0;
              
              while (st.hasMoreTokens())
              {  
            	     // Word count here
	             // word.set(st.nextToken().toString());
                 //context.write(word, one);
	            list[k] = Integer.valueOf(st.nextToken());
	            k++;
	          }
             for(int i=1;i<k;i++) {
            	 List<String> people = new ArrayList<String>();
             }
              /*
              for(int i=1;i<=k;i++) {
            	  word.set(Integer.toString(i));
            	  context.write(word, one);
              }*/
                            
            //  String[][] Subests_Array = new String[k][k];
             // int[] Array_index = new int[k];
              //for(int index = 0 ; index<k ; index++) {
            	    //Array_index[index] = 0;
              //}
             int n = k;
             subsets = new String();
             char tmp = '\0';  
            // for(int l = 0 ; l<k ; l++) {
             for (int i = 0; i < (1<<n); i++)
              {       
	             subsets = new String();
	             subsets = "";
	             int flag = 0;
	             tmp = '\0';
                 for (int j = 0; j < n; j++)  
                  {        
    	      
    	                tmp = '\0';
                    if ((i & (1 << j)) > 0) 
                     {               
                    
                    	   if(subsets == "")
        	   		        {
        	   			      subsets = subsets + Integer.toString(list[j]);
        	   		        }
                    	   else 
        	   		        {
        	   		          subsets = subsets +"," + Integer.toString(list[j]);
        	   		          flag++;
        	   		         }
                    	
                      }
                    }
              
                 if (!"".equals(subsets)) {
                	// if(l==flag) {
                		     word.set(subsets);
                		   //  one.set(flag+1);
                         context.write(word, new IntWritable(1));
                	 //}
              //  	 Subests_Array[flag][Array_index[flag]++] = subsets;
                	    
                	  }
                	   
                	 
                //  word.set(subsets);
                //  context.write(word, one);
                 }
             //}
               //  for(int len=0;len<k;len++) {
                	 // for(int y=0 ; y <=Array_index[len];y++) {
                		//  word.set(Subests_Array[len][y]);
                		  //context.write(word, one);
                	 // }
                	 
                // }
                 
                }
      
         //  fsout.writeUTF(subsets); 
             //fsout.flush();
             //fsout.sync();
             //fsout.close();
    
   
             }
       
        }
      
	public static class IntSumCombiner extends Reducer<Text,IntWritable,Text,IntWritable> 
	  {
		 private IntWritable pre_result = new IntWritable();
	     public void reduce(Text key, Iterable<IntWritable> values,Context context)throws IOException, InterruptedException
	    
	     {
	    	   
	    	    
	     
	       int sum = 0; 
	       for (IntWritable val : values) 
	       {
			     context.write(key,new IntWritable(val.get()));
	       }
	     //  if (sum >= 3) {
	      // pre_result.set(sum);
	       //context.write(key, pre_result);
	       //}
	      
	       
	        
	      }
	   }
	

 public static class IntSumReducer extends Reducer<Text,IntWritable,Text,IntWritable> 
  {
	 private IntWritable result = new IntWritable();
	 private Text halfresult = new Text();
     public void reduce(Text key, Iterable<IntWritable> values,Context context)throws IOException, InterruptedException
    
     {
    	   
    	    
     
       int sum = 0; 
       for (IntWritable val : values) 
       {
		 sum += val.get();
       }
       
    	
       if (sum >= 3) 
       {
	      result.set(sum);  
	      context.write(key, result);
	   }
       
        
      }
   }
 
public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
   // TokenizerMapper.test = conf; 
    String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
    if (otherArgs.length != 2) {
      System.err.println("Usage: wordcount <in> <out>");
      System.exit(2);
    }
    Job job = new Job(conf, "word count");
    //New for in MR job
    /*
    Path inPath = new Path(args[0]);
    Path outPath = null;
    for(int i= 0;i<=2 ; i++)
    {
     outPath = new Path(args[1]+i);
     Job job = new Job(conf, "word count");
     job.setJarByClass(Apriori.class);
     job.setMapperClass(TokenizerMapper.class);
     job.setReducerClass(IntSumReducer.class);
     job.setOutputKeyClass(Text.class);
     job.setOutputValueClass(IntWritable.class);
     FileInputFormat.addInputPath(job,inPath);
     FileOutputFormat.setOutputPath(job,outPath);
     job.waitForCompletion(true);
     inPath = outPath;
     
     
    }
    */
    //end
    
    
    Path outputPath = new Path(args[1]);
    outputPath.getFileSystem(conf).delete(outputPath, true);
    job.setJarByClass(Apriori.class);
    
    
    job.setMapperClass(TokenizerMapper.class);
    job.setCombinerClass(IntSumCombiner.class);
    job.setReducerClass(IntSumReducer.class);
   
    
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
    
    //job.setNumReduceTasks(3);
    
    FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
    FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
    
    
    System.exit(job.waitForCompletion(true) ? 0 : 1);
    /*
    while(job.waitForCompletion(true)) {}
    Path pt = new Path(args[1]); //Location on HDFS
    FileSystem fs = FileSystem.get(new Configuration());
    BufferedReader br = new BufferedReader(new InputStreamReader(fs.open(pt)));
    String line;
    line = br.readLine();
    int[] list = new int[line.length()];
    ArrayList<Integer> key_list = new ArrayList<>();
    ArrayList<Integer> value_list = new ArrayList<>();
    int k=0;
    while(line!=null)
    {
    	  StringTokenizer st = new StringTokenizer(line, ",");
    	  while (st.hasMoreTokens())
          {  
            list[k] = Integer.valueOf(st.nextToken());
            k++;
          }
    	  key_list.add(list[0]);
    	  value_list.add(list[1]);
    	  
    }
    Collections.sort(key_list);
    */
   
    
}
  }


