package com.d3media.starter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.GenericOptionsParser;
import org.reflections.Reflections;

import java.io.IOException;

public class EMRStarter {

    public static void main(String args[]) throws IOException, IllegalAccessException, InstantiationException, InterruptedException, ClassNotFoundException {
        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();

        String jobselection = otherArgs[0];

        System.out.println("searching job " + jobselection);

        Reflections reflections = new Reflections("com.d3media");
        for (Class<? extends JobSelector> c : reflections.getSubTypesOf(JobSelector.class)) {
            JobSelector job = c.newInstance();
            System.out.println("found job " + job.getName());
            if (job.getName().equals(jobselection)) {
                System.out.println("starting job " + job.getName());
                job.run(conf,
                        new Path(otherArgs[1]),
                        new Path(otherArgs[2]));
            }
        }
    }
}
