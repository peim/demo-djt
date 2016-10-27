package com.peim.service;

import com.peim.service.dao.TaskService;
import com.peim.service.hash.HashAlgorithm;
import com.peim.service.hash.HashAlgorithmFactory;
import com.peim.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;

@Service("taskProcessingService")
public class TaskProcessingService {

    @Autowired
    private TaskService taskService;

    public void execute(int id, DeferredResult<ResponseEntity<Task>> deferredResult) {
        Task task = taskService.getTaskById(id);
        HashAlgorithm algorithm = HashAlgorithmFactory.of(task.getAlgo());
        CompletableFuture<String> future = CompletableFuture.supplyAsync(
                () -> algorithm.hash(task.getSrc())
        );
        future.whenComplete((success, failure) -> {
            try {
                if (failure != null) {
                    task.setStatus("FAILURE");
                    task.setDescription(failure.getMessage());
                } else {
                    task.setStatus("SUCCESS");
                    task.setDescription(success);
                }
                taskService.updateTask(task);
                deferredResult.setResult(ResponseEntity.ok(task));
            } catch (Exception e) {
                deferredResult.setErrorResult(ResponseEntity.badRequest());
                e.printStackTrace();
            }
        });
    }

    public void cancel(int id, DeferredResult<ResponseEntity<Task>> deferredResult) {
        Task task = taskService.getTaskById(id);
        //............
    }
}
