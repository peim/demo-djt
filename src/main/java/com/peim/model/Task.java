package com.peim.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "task")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Task implements Serializable {

    private static final long serialVersionUID = 2000015;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String src;

    @Column(nullable = false)
    private String algo;

    private String status;

    private String description;

    public Task() {}

    public Task(int id, String src, String algo, String status, String description) {
        this.id = id;
        this.src = src;
        this.algo = algo;
        this.status = status;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getAlgo() {
        return algo;
    }

    public void setAlgo(String algo) {
        this.algo = algo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", src='" + src + '\'' +
                ", algo='" + algo + '\'' +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Task)) {
            return false;
        } else {
            Task task = (Task) obj;
            return id == task.id;
        }
    }

    @Override
    public int hashCode() {
        return id;
    }
}
