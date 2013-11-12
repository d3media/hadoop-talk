package com.d3media.starter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;

import java.io.IOException;

public interface JobSelector {
    String getName();

    void run(Configuration conf, Path input, Path output) throws IOException;
}
