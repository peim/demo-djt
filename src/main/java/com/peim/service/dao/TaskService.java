package com.peim.service.dao;

import com.peim.model.Task;

import java.util.List;

public interface TaskService {

    Task getTaskById(int id);

    List<Task> getTaskByStatus(String status);

    List<Task> getAllTasks();

    Task addTask(Task task);

    void updateTask(Task task);

    void removeTask(int id);
}
