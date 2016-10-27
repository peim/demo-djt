package com.peim.controller;

import com.peim.service.dao.TaskService;
import com.peim.model.Task;
import com.peim.service.TaskProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(path = "/api")
public class AsyncController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskProcessingService taskProcessingService;

    @RequestMapping(path = "/task/execute/{id}", method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<Task>> execute(@PathVariable(value = "id") int id) {
        final DeferredResult<ResponseEntity<Task>> deferredResult = new DeferredResult<>(300000L);
        Task task = taskService.getTaskById(id);
        CompletableFuture<String> future = taskProcessingService.process(task);
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
        return deferredResult;
    }

    @RequestMapping(path = "/task/cancel/{id}", method = RequestMethod.PUT)
    public DeferredResult<ResponseEntity<Task>> cancel(@PathVariable(value = "id") int id) {
        final DeferredResult<ResponseEntity<Task>> deferredResult = new DeferredResult<>(300000L);
        Task task = taskService.getTaskById(id);
        deferredResult.setResult(ResponseEntity.ok(task));
        return deferredResult;
    }
}
