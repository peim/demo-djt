package com.peim.controller;

import com.peim.dao.TaskService;
import com.peim.model.Task;
import com.peim.service.TaskProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(path = "/api")
public class AsyncController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskProcessingService taskProcessingService;

    @RequestMapping(path = "/execute/task/{id}", method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<Task>> execute(@PathVariable(value = "id") int id) throws Exception {

        final DeferredResult<ResponseEntity<Task>> deferredResult = new DeferredResult<>(5000L);
        deferredResult.onTimeout(
                () -> deferredResult.setErrorResult(ResponseEntity
                        .status(HttpStatus.REQUEST_TIMEOUT)
                        .body("Request timeout occurred.")
                )
        );

        Task task = taskService.getTaskById(id);
        CompletableFuture<String> future = taskProcessingService.process(task);
        future.whenComplete((success, failure) -> {
            if (failure != null) {
                task.setStatus("FAILURE");
                task.setDescription(failure.getMessage());
            } else {
                task.setStatus("SUCCESS");
                task.setDescription(success);
            }
            deferredResult.setResult(ResponseEntity.ok(task));
        });

        return deferredResult;
    }
}
