package com.peim.service;

import com.peim.model.Task;
import com.peim.service.dao.TaskService;
import com.peim.service.hash.HashAlgorithm;
import com.peim.service.hash.HashAlgorithmFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service("taskProcessingService")
public class TaskProcessingService {

    @Autowired
    private TaskService taskService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private ThreadPoolTaskExecutor executor;
    private ConcurrentHashMap<Task, CompletableFuture<String>> processingTasks =
            new ConcurrentHashMap<>();

    public void execute(int id, DeferredResult<ResponseEntity<Task>> deferredResult) {
        Task task = taskService.getTaskById(id);
        HashAlgorithm algorithm = HashAlgorithmFactory.of(task.getAlgo());

        if (getExecutor().getActiveCount() == getExecutor().getMaxPoolSize()) {
            task.setStatus("WAITING");
            task.setDescription(null);
            messagingTemplate.convertAndSend("/queue/task", task);
        }

        CompletableFuture<String> future = CompletableFuture.supplyAsync(
                () -> {
                    task.setStatus("PROCESSING");
                    task.setDescription(null);
                    messagingTemplate.convertAndSend("/queue/task", task);
                    return algorithm.hash(task.getSrc());
                },
                getExecutor()
        );
        processingTasks.put(task, future);
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
        processingTasks.get(task).cancel(true);
        task.setStatus("CANCELED");
        task.setDescription("Task has been canceled");
        taskService.updateTask(task);
        deferredResult.setResult(ResponseEntity.ok(task));
    }

    private ThreadPoolTaskExecutor getExecutor() {
        if (executor == null) {
            executor = new ThreadPoolTaskExecutor();
            executor.setCorePoolSize(2);
            executor.setMaxPoolSize(2);
            executor.setQueueCapacity(20);
            executor.initialize();
        }
        return executor;
    }
}
