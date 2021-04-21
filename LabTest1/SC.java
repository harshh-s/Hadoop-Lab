import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


import java.io.*;
public class SC {
   public static void main(String[] args) throws IOException {
      File file = new File("input11.txt"); //Creates a new File instance by converting the given pathname string into an abstract pathname
      FileInputStream fn = new FileInputStream(file); //Instantiate the FileInputStream class by passing an object of the required file as parameter to its constructor.
      InputStreamReader input = new InputStreamReader(fn); //Creates an InputStreamReader that uses the default charset.
      BufferedReader reader = new BufferedReader(input); //Creates a buffering character-input stream that uses a default-sized input buffer.
      String sentence;
      // Initializing counters
      int countWord = 0,whitespaceCount = 0;
      if((sentence= reader.readLine()) != null) {
         String[] wordlist = sentence.split("\\s+"); // \\s+ is the space delimiter in java
         countWord += wordlist.length;
         whitespaceCount += countWord -1;
      }
      System.out.println(" Number of whitespaces = " + whitespaceCount);
   }
}
