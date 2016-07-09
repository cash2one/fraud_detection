package com.mr.code_backup;

import com.mr.config.Properties;
import com.mr.utils.CommUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.IOException;

/**
 * Created by TakiyaHideto on 15/12/15.
 */
public class AttachThirdPartyCookieTagCHANGRONG {
    public static class AttachThirdPartyCookieTagMapper extends Mapper<Object, Text, Text, Text> {
        private Text key_result = new Text();
        private Text value_result = new Text();

        @Override
        protected void map(Object key, Text value, Context context)	throws IOException, InterruptedException{
            // create counters
            Counter validOutputCt = context.getCounter("AttachThirdPartyCookieTagMapper", "validOutputCt");

            // initialization
            String line = value.toString().trim();
            String deviceId = line;
            String tagAttachment = "CHANGRONG_DEVICE_ID_1215";

            key_result.set(deviceId);
            value_result.set(tagAttachment);
            context.write(key_result, value_result);
            validOutputCt.increment(1);
        }
    }

    public static class AttachThirdPartyCookieTagReducer extends Reducer<Text, Text, NullWritable, Text> {
        private Text key_result = new Text();
        private Text value_result = new Text();

        @Override
        protected void reduce(Text key, Iterable<Text> values,Context context)
                throws IOException, InterruptedException {

            // initialize Counter
            Counter validOutputCt = context.getCounter("AttachThirdPartyCookieTagReducer","validOutputCt");

            value_result.set(key.toString() + Properties.Base.BS_SEPARATOR + "CHANGRONG_DEVICE_ID_1215");
            context.write(NullWritable.get(),value_result);
            validOutputCt.increment(1);
        }
    }

    public static void main(String[] args) throws Exception{
        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if(otherArgs.length != 2){
            System.err.println("<int> <out>");
            System.exit(4);
        }

        Job job = new Job(conf,"AttachThirdPartyCookieTagCHANGRONG");
        conf.set(Properties.Base.BS_DEFT_NAME, Properties.Base.BS_HDFS_NAME);
        conf.set(Properties.Base.BS_JOB_TRACKER, Properties.Base.BS_HDFS_TRACKER);
        conf.setBoolean("mapred.reduce.tasks.speculative.execution", false);

        FileSystem fs = FileSystem.get(conf);
        job.setJarByClass(AttachThirdPartyCookieTagCHANGRONG.class);
        job.setReducerClass(AttachThirdPartyCookieTagReducer.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Text.class);
        job.setNumReduceTasks(1);

        FileOutputFormat.setOutputPath(job, new Path(otherArgs[0]));
        String thirdPartyCookiesPath = otherArgs[1];
        job.getConfiguration().set("mapred.queue.name", "algo-dev");

        CommUtil.addInputFileComm(job, fs, thirdPartyCookiesPath, TextInputFormat.class, AttachThirdPartyCookieTagMapper.class);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
