package com.peim.controller;

import com.peim.model.Task;
import com.peim.service.TaskProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping(path = "/api")
public class AsyncController {

    @Autowired
    private TaskProcessingService taskProcessingService;

    @RequestMapping(path = "/task/execute/{id}", method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<Task>> execute(@PathVariable(value = "id") int id) {
        final DeferredResult<ResponseEntity<Task>> deferredResult = new DeferredResult<>(300000L);
        deferredResult.onTimeout(() -> deferredResult.setErrorResult(ResponseEntity
                .status(HttpStatus.REQUEST_TIMEOUT).body("Request timeout occurred."))
        );
        taskProcessingService.execute(id, deferredResult);
        return deferredResult;
    }

    @RequestMapping(path = "/task/cancel/{id}", method = RequestMethod.PUT)
    public DeferredResult<ResponseEntity<Task>> cancel(@PathVariable(value = "id") int id) {
        final DeferredResult<ResponseEntity<Task>> deferredResult = new DeferredResult<>(300000L);
        deferredResult.onTimeout(() -> deferredResult.setErrorResult(ResponseEntity
                .status(HttpStatus.REQUEST_TIMEOUT).body("Request timeout occurred."))
        );
        taskProcessingService.cancel(id, deferredResult);
        return deferredResult;
    }
}
