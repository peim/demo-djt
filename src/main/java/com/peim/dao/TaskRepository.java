package com.peim.dao;

import com.peim.model.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

interface TaskRepository extends CrudRepository<Task, Integer> {

    List<Task> findByStatus(String status);

    List<Task> findAll();
}