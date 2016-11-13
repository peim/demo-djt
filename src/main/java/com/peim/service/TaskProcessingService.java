package com.peim.service;

import com.peim.model.Task;
import com.peim.service.dao.TaskService;
import com.peim.service.hash.HashAlgorithm;
import com.peim.service.hash.HashAlgorithmFactory;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
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

    private final static String TASK_UPDATING_PATH = "/queue/task";

    @Autowired
    private TaskService taskService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private HashService hashService;

    private ThreadPoolTaskExecutor executor;
    private ConcurrentHashMap<Task, CompletableFuture<String>> processingTasks =
            new ConcurrentHashMap<>();

    public TaskProcessingService() {
        executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(20);
        executor.initialize();
    }

    public void execute(Task task, DeferredResult<ResponseEntity<Task>> deferredResult) {
        if (executor.getActiveCount() == executor.getMaxPoolSize()) {
            task.setStatus("WAITING");
            task.setDescription(null);
            messagingTemplate.convertAndSend(TASK_UPDATING_PATH, task);
        }

        CompletableFuture<String> future = CompletableFuture.supplyAsync(
                () -> {
                    task.setStatus("PROCESSING");
                    task.setDescription(null);
                    messagingTemplate.convertAndSend(TASK_UPDATING_PATH, task);
                    return hashService.hash(task);
                },
                executor
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

    public void cancel(Task task, DeferredResult<ResponseEntity<Task>> deferredResult) {
        boolean isCanceled = processingTasks.get(task).cancel(true);
        if (isCanceled) {
            task.setStatus("CANCELED");
            task.setDescription("Task has been canceled");
            taskService.updateTask(task);
        }
        deferredResult.setResult(ResponseEntity.ok(task));
    }
}
