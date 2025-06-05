package com.TaskRetrievalWithCaching.services;

import com.TaskRetrievalWithCaching.caching.LRUCache;
import com.TaskRetrievalWithCaching.exceptions.MalformedJsonException;
import com.TaskRetrievalWithCaching.exceptions.TaskNotFoundException;
import com.TaskRetrievalWithCaching.models.Task;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class TaskService {

     private final ObjectMapper objectMapper;
     private final String taskFolderPath;
     private final LRUCache<String, String> cache;

    public TaskService(@Value("${task.folder.path}") String taskFolderPath, ObjectMapper objectMapper, LRUCache<String, String> cache) {
        this.taskFolderPath = taskFolderPath;
        this.objectMapper = objectMapper;
        this.cache = cache;
    }

    public Task getTaskById(String id) {

        if(cache.containsKey(id)) {
            System.out.println("Cache hit for ID: " + id);

            try{
                return objectMapper.readValue(cache.get(id), Task.class);
            } catch (JsonProcessingException ex){
                throw new MalformedJsonException(("Malformed JSON"));
            }
        }

        System.out.println("New data");

        File file = new File(taskFolderPath + File.separator + id + ".json");

        if (!file.exists()) {
            throw new TaskNotFoundException("Task not found for ID: " + id);
        }

        try {

            Task task =  objectMapper.readValue(file, Task.class);
            String taskJson = objectMapper.writeValueAsString(task);
            cache.put(id, taskJson);
            return task;

        } catch (JsonProcessingException ex){
            throw new MalformedJsonException("Malformed JSON in file: " + id + ".json");
        } catch (IOException ex){
            throw new RuntimeException("Failed to read file: " + id + ".json", ex);
        }
    }
}
