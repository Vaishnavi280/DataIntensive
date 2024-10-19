import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class OriginOfGoodsAnalysis {
    public static class OriginOfGoodsMapper
            extends Mapper <Object, Text, Text, Text> { 
	public OriginOfGoodsMapper() {
        }

        public void map(Object key, Text value, Context context)
                throws IOException, InterruptedException {

            String record = value.toString();
            String[] columns = record.split(","); 
	// Label Customers 
	context.write(new Text(columns[1].trim().toLowerCase()), new Text("ORIGIN " + columns[0] ));

        }
    }

public static class NationalityMapper
            extends Mapper <Object, Text, Text, Text> {
	public NationalityMapper() {
        }
        public void map(Object key, Text value, Context context)
                throws IOException, InterruptedException {

           String record = value.toString();
           String[] columns = record.split(","); 
	// Label Customers 
	   context.write(new Text(columns[1].trim().toLowerCase()), new Text("ADB " + columns[4] ));
        }
    }

    
     
    public static class ADBReducer
            extends Reducer <Text, Text, Text, Text> {

        public void reduce(Text key, Iterable <Text> values, Context context)
                throws IOException, InterruptedException {

         double count = 0;
	String region = "";
for(Text t:values){

	String[] columns = t.toString().split(" ");
	if (columns[0].equals("ADB")){
		count += Float.parseFloat(columns[1]);
	}else if (columns[0].equals("ORIGIN")){
		region =columns[1];
	}
}
	String str= String.format("%.2f %s",count,region);
        context.write(key, new Text(str));
    }
}

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf, "Reduce-side-Join");
        job.setJarByClass(OriginOfGoodsAnalysis.class);
        job.setReducerClass(ADBReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);


       	MultipleInputs.addInputPath(job, new Path(args[0]),TextInputFormat.class, OriginOfGoodsMapper.class); 
     	MultipleInputs.addInputPath(job, new Path(args[1]),TextInputFormat.class, NationalityMapper.class); 
  
        Path outputPath = new Path(args[2]); 
        FileOutputFormat.setOutputPath(job, outputPath); 
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
