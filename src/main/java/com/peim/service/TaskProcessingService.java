package com.peim.service;

import com.peim.service.hash.HashAlgorithm;
import com.peim.service.hash.HashAlgorithmFactory;
import com.peim.model.Task;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service("taskProcessingService")
public class TaskProcessingService {

    public CompletableFuture<String> process(Task task) {
        HashAlgorithm algorithm = HashAlgorithmFactory.of(task.getAlgo());
        return CompletableFuture.supplyAsync(
                () -> algorithm.hash(task.getSrc())
        );
    }
}
