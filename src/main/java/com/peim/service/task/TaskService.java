package com.peim.service.task;

import com.peim.model.Task;

import java.util.List;

public interface TaskService {

    Task getTaskById(int id);

    List<Task> getTaskByStatus(String status);

    List<Task> getAllTasks();

    Task addTask(Task task);

    void removeTask(int id);
}
