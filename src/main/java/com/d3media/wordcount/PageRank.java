package com.d3media.wordcount;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.StringTokenizer;

public class PageRank {

    public static class MapRank extends Mapper<LongWritable, Text, Text, Text> {

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            StringTokenizer tknz = new StringTokenizer(value.toString());

            if ( ! tknz.hasMoreTokens() ) return;

            Text page = new Text(tknz.nextToken());
            Text links_to = new Text();

            while (tknz.hasMoreTokens()) {
                links_to.set(tknz.nextToken());
                context.write(links_to, page);
            }
        }
    }

    public static class RedRank extends Reducer<Text,Text,Text,Text> {

        @Override
        protected void reduce(Text page, Iterable<Text> links_from, Context context) throws IOException, InterruptedException {

            long pagerank = 0;
            for (Text link_from : links_from) {
                pagerank++;
            }



        }
    }

}
