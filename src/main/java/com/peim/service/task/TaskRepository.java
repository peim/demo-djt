package com.peim.service.task;

import com.peim.model.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Integer> {

    List<Task> findByStatus(String status);

    List<Task> findAll();
}