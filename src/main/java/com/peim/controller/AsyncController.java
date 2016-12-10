package com.peim.controller;

import com.peim.model.Task;
import com.peim.service.TaskProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping(path = "/api")
public class AsyncController {

    @Autowired
    private TaskProcessingService taskProcessingService;

    @RequestMapping(path = "/task/execute", method = RequestMethod.PUT)
    public DeferredResult<ResponseEntity<Task>> execute(@RequestBody Task task) {
        final DeferredResult<ResponseEntity<Task>> deferredResult = new DeferredResult<>(300000L);
        deferredResult.onTimeout(() -> deferredResult.setErrorResult(ResponseEntity
                .status(HttpStatus.REQUEST_TIMEOUT).body("Request timeout occurred."))
        );
        taskProcessingService.execute(task, deferredResult);
        return deferredResult;
    }

    @RequestMapping(path = "/task/cancel", method = RequestMethod.PUT)
    public DeferredResult<ResponseEntity<Task>> cancel(@RequestBody Task task) {
        final DeferredResult<ResponseEntity<Task>> deferredResult = new DeferredResult<>(300000L);
        deferredResult.onTimeout(() -> deferredResult.setErrorResult(ResponseEntity
                .status(HttpStatus.REQUEST_TIMEOUT).body("Request timeout occurred."))
        );
        taskProcessingService.cancel(task, deferredResult);
        return deferredResult;
    }

    private String getString() {
        return "";
    }
}
