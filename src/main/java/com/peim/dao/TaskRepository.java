package com.peim.dao;

import com.peim.model.Task;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

interface TaskRepository extends CrudRepository<Task, Integer> {

    List<Task> findByStatus(String status);

    List<Task> findAll();

    @Modifying(clearAutomatically = true)
    @Query("update Task t set t.status=:status, t.description=:description where t.id=:id")
    void updateTask(@Param("id") int id, @Param("status") String status,
                            @Param("description") String description);
}