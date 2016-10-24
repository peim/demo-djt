package com.peim.controller;

import com.peim.model.Task;
import com.peim.service.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Transactional
@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public List<Task> getAll() {
        return taskService.getAllTasks();
    }

    @RequestMapping(path = "/id/{id}", method = RequestMethod.GET)
    public Task getById(@PathVariable int id) {
        return taskService.getTaskById(id);
    }

    @RequestMapping(path = "/country/{country}", method = RequestMethod.GET)
    public List<Task> getByStatus(@PathVariable String status) {
        return taskService.getTaskByStatus(status);
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public Task addTask(@RequestBody Task author) {
        return taskService.addTask(author);
    }

    @RequestMapping(path = "/delete/{id}", method = RequestMethod.DELETE)
    public void deleteTask(@PathVariable int id) {
        taskService.removeTask(id);
    }
}
