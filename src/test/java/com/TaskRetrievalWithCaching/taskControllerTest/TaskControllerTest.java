package com.TaskRetrievalWithCaching.taskControllerTest;

import com.TaskRetrievalWithCaching.controllers.TaskController;
import com.TaskRetrievalWithCaching.models.Task;
import com.TaskRetrievalWithCaching.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    private Task mockTask;

    @BeforeEach
    public void setUp() {
        mockTask = new Task();
        mockTask.setId("1");
        mockTask.setDescription("Test Task");
    }

    @Test
    void testGetTask() throws Exception {

        when(taskService.getTaskById("1")).thenReturn(mockTask);

        mockMvc.perform(get("/getTask/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.description").value("Test Task"));
    }

}
