package com.peim.service;

import com.peim.model.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.request.async.DeferredResult;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TaskProcessingTestService {

    private static final String SUCCESS = "SUCCESS";
    private static final String PROCESSING = "PROCESSING";
    private static final String WAITING = "WAITING";
    private static final String CANCELED = "CANCELED";

    @Autowired
    private TaskProcessingService taskProcessingService;

    @Test
    public void executeOneTaskTest() throws Exception {
        Task task = new Task(1, "file:///etc/hosts/file1", "md5", "NEW", null);
        DeferredResult<ResponseEntity<Task>> deferredResult = new DeferredResult<>(300000L);
        taskProcessingService.execute(task, deferredResult);

        Thread.sleep(1000);

        assertTrue(task.getStatus().equals(PROCESSING));

        Thread.sleep(11000);

        assertTrue(task.getStatus().equals(SUCCESS));
    }

    @Test
    public void executeThreeTaskTest() throws Exception {
        Task task1 = new Task(1, "file:///etc/hosts/file1", "md5", "NEW", null);
        Task task2 = new Task(2, "file:///etc/hosts/file2", "sha-1", "NEW", null);
        Task task3 = new Task(3, "file:///etc/hosts/file3", "sha-256", "NEW", null);
        DeferredResult<ResponseEntity<Task>> deferredResult = new DeferredResult<>(300000L);
        taskProcessingService.execute(task1, deferredResult);
        taskProcessingService.execute(task2, deferredResult);

        Thread.sleep(1000);

        taskProcessingService.execute(task3, deferredResult);

        assertTrue(task1.getStatus().equals(PROCESSING));
        assertTrue(task2.getStatus().equals(PROCESSING));
        assertTrue(task3.getStatus().equals(WAITING));

        Thread.sleep(11000);

        assertTrue(task1.getStatus().equals(SUCCESS));
        assertTrue(task2.getStatus().equals(SUCCESS));
    }

    @Test
    public void cancelTaskTest() throws Exception {
        Task task = new Task(5, "file:///etc/hosts/file1", "md5", "NEW", null);
        DeferredResult<ResponseEntity<Task>> deferredResult = new DeferredResult<>(300000L);
        taskProcessingService.execute(task, deferredResult);

        Thread.sleep(1000);

        assertTrue(task.getStatus().equals(PROCESSING));

        Thread.sleep(5000);

        taskProcessingService.cancel(task, deferredResult);

        assertTrue(task.getStatus().equals(CANCELED));
    }

}
