package com.TaskRetrievalWithCaching.services;

import com.TaskRetrievalWithCaching.exceptions.MalformedJsonException;
import com.TaskRetrievalWithCaching.exceptions.TaskNotFoundException;
import com.TaskRetrievalWithCaching.models.Task;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.MalformedInputException;

@Service
public class TaskService {

     private final ObjectMapper objectMapper = new ObjectMapper();
     private final String taskFolderPath;

    public TaskService(@Value("${task.folder.path}") String taskFolderPath) {
        this.taskFolderPath = taskFolderPath;
    }

    public Task getTaskById(String id) {

        File file = new File(taskFolderPath + File.separator + id + ".json");

        if (!file.exists()) {
            throw new TaskNotFoundException("Task not found for ID: " + id);
        }

        try {
            return objectMapper.readValue(file, Task.class);
        } catch (JsonProcessingException ex){
            throw new MalformedJsonException("Malformed JSON in file: " + id + ".json");
        } catch (IOException ex){
            throw new RuntimeException("Failed to read file: " + id + ".json", ex);
        }
    }
}
