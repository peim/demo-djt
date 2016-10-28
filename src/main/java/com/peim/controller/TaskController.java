package com.peim.controller;

import com.peim.service.dao.TaskService;
import com.peim.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getAll() {
        return taskService.getAllTasks();
    }

    @RequestMapping(path = "/id/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Task getById(@PathVariable int id) {
        return taskService.getTaskById(id);
    }

    @RequestMapping(path = "/status/{status}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getByStatus(@PathVariable String status) {
        return taskService.getTaskByStatus(status);
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Task addTask(@RequestBody Task task) {
        return taskService.addTask(task);
    }

    @RequestMapping(path = "/update", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateTask(@RequestBody Task task) {
        taskService.updateTask(task);
    }

    @RequestMapping(path = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteTask(@PathVariable int id) {
        taskService.removeTask(id);
    }
}
