package com.TaskRetrievalWithCaching.controllers;

import com.TaskRetrievalWithCaching.models.Task;
import com.TaskRetrievalWithCaching.services.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/getTask")
public class TaskController {

    private TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable String id) throws IOException {

        Task task = taskService.getTaskById(id);

        return ResponseEntity.ok(task);
    }

}
