package com.TaskRetrievalWithCaching.services;

import com.TaskRetrievalWithCaching.models.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class TaskService {

     private final ObjectMapper objectMapper = new ObjectMapper();
     private final String taskFolderPath;

    public TaskService(@Value("${task.folder.path}") String taskFolderPath) {
        this.taskFolderPath = taskFolderPath;
    }

    public Task getTaskById(String id) throws IOException {

        File file = new File(taskFolderPath + File.separator + id + ".json");

        return objectMapper.readValue(file, Task.class);
    }
}
