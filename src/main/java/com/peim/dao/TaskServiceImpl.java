package com.peim.dao;

import com.peim.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("taskService")
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public TaskServiceImpl() {
    }

    @Override
    public Task getTaskById(int id) {
        return this.taskRepository.findOne(id);
    }

    @Override
    public List<Task> getTaskByStatus(String status) {
        return this.taskRepository.findByStatus(status);
    }

    @Override
    public List<Task> getAllTasks() {
        return this.taskRepository.findAll();
    }

    @Override
    public Task addTask(Task task) {
        return this.taskRepository.save(task);
    }

    @Override
    public void removeTask(int id) {
        this.taskRepository.delete(id);
    }
}