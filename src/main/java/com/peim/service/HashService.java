package com.peim.service;

import com.peim.model.Task;
import com.peim.service.hash.HashAlgorithm;
import com.peim.service.hash.HashAlgorithmFactory;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HashService {

    @Autowired
    private JavaSparkContext sparkContext;

    public String hash(Task task) {
        HashAlgorithm algorithm = HashAlgorithmFactory.of(task.getAlgo());
        JavaRDD<String> lines = sparkContext.textFile(task.getSrc());
        return lines.reduce((line1, line2) -> algorithm.hash(line1 + line2));
    }
}
