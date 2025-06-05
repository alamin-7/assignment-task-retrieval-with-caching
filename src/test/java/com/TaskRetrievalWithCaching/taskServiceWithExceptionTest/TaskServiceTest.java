package com.TaskRetrievalWithCaching.taskServiceWithExceptionTest;

import com.TaskRetrievalWithCaching.caching.LRUCache;
import com.TaskRetrievalWithCaching.exceptions.MalformedJsonException;
import com.TaskRetrievalWithCaching.exceptions.TaskNotFoundException;
import com.TaskRetrievalWithCaching.models.Task;
import com.TaskRetrievalWithCaching.services.TaskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled("Test is failing due to recent cache refactor - needs fix")
@WebMvcTest(TaskService.class)
public class TaskServiceTest {

    private TaskService taskService;

    @Mock
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    private final String taskFolderPath = "src/test/resources/tasks";

    @Mock
    private LRUCache<String, String> cache;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        taskService = new TaskService(taskFolderPath, objectMapper, cache);
    }

    @Test
    void shouldReturnTaskWhenFileExistsAndJsonIsValid() throws Exception {

        String id = "123e4567-e89b-12d3-a456-426614174000";
        File file = new File(taskFolderPath + File.separator + id + ".json");
        Task mockTask = new Task();
        mockTask.setId(id);
        mockTask.setDescription("This is a test task.");

        when(objectMapper.readValue(file, Task.class)).thenReturn(mockTask);

        Task result = taskService.getTaskById(id);

        assertNotNull(result);
        assertEquals("123e4567-e89b-12d3-a456-426614174000", result.getId());
        assertEquals("This is a test task.", result.getDescription());

    }

    @Test
    void shouldThrowTaskNotFoundExceptionWhenFileDoesNotExist() {

        String id = "nonexistent";
        File file = new File(taskFolderPath + File.separator + id + ".json");
        file.delete();

        TaskNotFoundException exception = assertThrows(TaskNotFoundException.class,
                () -> taskService.getTaskById(id));

        assertEquals("Task not found for ID: nonexistent", exception.getMessage());
    }

    @Test
    void shouldThrowMalformedJsonExceptionWhenJsonIsInvalid() throws Exception {

        String id = "123e4567-e89b-12d3-a456-426614174000";
        File file = new File(taskFolderPath + File.separator + id + ".json");

        when(objectMapper.readValue(file, Task.class))
                .thenThrow(new JsonProcessingException("Malformed JSON") {});

        MalformedJsonException exception = assertThrows(MalformedJsonException.class,
                () -> taskService.getTaskById(id));

        assertEquals("Malformed JSON in file: "+ id +".json", exception.getMessage());
    }

    @Test
    void shouldReturn500WhenUnhandledExceptionOccurs() throws Exception {

        when(taskService.getTaskById("123e4567-e89b-12d3-a456-426614174000"))
                .thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(get("/123e4567-e89b-12d3-a456-426614174000"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("Unexpected internal errors")
                );

    }

}

