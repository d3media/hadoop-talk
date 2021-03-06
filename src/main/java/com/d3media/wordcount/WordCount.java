package com.d3media.wordcount;

import com.d3media.starter.JobSelector;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;
import java.util.StringTokenizer;

public class WordCount implements JobSelector {

    @Override
    public String getName() {
        return "wordcount";
    }

    @Override
    public void run(Configuration conf, Path input, Path output) throws IOException, ClassNotFoundException, InterruptedException {
        Job job = new Job(conf, "wordcount");

        job.setJarByClass(WordCount.class);

        job.setMapperClass(Map.class);
        job.setCombinerClass(Reduce.class);
        job.setReducerClass(Reduce.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        FileInputFormat.addInputPath(job, input);
        FileOutputFormat.setOutputPath(job, output);

        job.waitForCompletion(true);
    }

    public static class Map extends Mapper<LongWritable, Text, Text, LongWritable> {

        @Override
        protected void map(LongWritable linenumber, Text line, Context context) throws IOException, InterruptedException {
            StringTokenizer tokenizer = new StringTokenizer(line.toString());

            Text word = new Text();

            while (tokenizer.hasMoreTokens()) {
                word.set(tokenizer.nextToken());
                context.write(word, new LongWritable(1));
            }
        }
    }

    public static class Reduce extends Reducer<Text, LongWritable, Text, LongWritable> {

        @Override
        protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
            long sum = 0l;

            for (LongWritable value : values) {
                sum++;
            }
            context.write(key, new LongWritable(sum));
        }
    }
}
