package com.peim.config;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SparkConfiguration {

    @Bean
    public JavaSparkContext sparkContext() {
        SparkConf conf = new SparkConf()
                .setAppName("demo-djt-spark")
                .setMaster("local[4]");
        JavaSparkContext sparkContext = new JavaSparkContext(conf);
        return sparkContext;
    }
}
