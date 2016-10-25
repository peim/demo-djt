package com.peim.controller;

import com.peim.dao.TaskService;
import com.peim.model.Task;
import com.peim.service.TaskProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class AsyncController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskProcessingService taskProcessingService;

    @RequestMapping("/execute")
    public DeferredResult<ResponseEntity<Task>> execute(final @RequestParam(value="taskId") int taskId) throws Exception {

        final DeferredResult<ResponseEntity<Task>> deferredResult = new DeferredResult<>(5000L);
        deferredResult.onTimeout(
                () -> deferredResult.setErrorResult(ResponseEntity
                        .status(HttpStatus.REQUEST_TIMEOUT)
                        .body("Request timeout occurred.")
                )
        );

        Task task = taskService.getTaskById(taskId);
        ListenableFuture<String> future = taskProcessingService.process(task);
        future.addCallback(new ListenableFutureCallback<String>() {
            @Override
            public void onSuccess(String result) {
                task.setStatus("SUCCESS");
                task.setDescription(result);
                deferredResult.setResult(ResponseEntity.ok(task));
              }
            @Override
            public void onFailure(Throwable t) {
                task.setStatus("FAILURE");
                task.setDescription(t.getMessage());
                deferredResult.setResult(ResponseEntity.ok(task));
            }
        });

        return deferredResult;
    }
}
