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
                .setMaster("local")
                .setAppName("demo-djt-spark");
        JavaSparkContext sparkContext = new JavaSparkContext(conf);
        return sparkContext;
    }
}
