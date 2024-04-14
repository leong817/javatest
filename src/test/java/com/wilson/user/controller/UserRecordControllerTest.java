package com.wilson.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wilson.user.model.UserRecord;
import com.wilson.user.service.UserRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The type User record controller test.
 */
@WebMvcTest(UserRecordController.class)
public class UserRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserRecordService userRecordService;

    /**
     * Perform create user.
     *
     * @throws Exception the exception
     */
    @Test
    public void performCreateUser() throws Exception {
        UserRecord userRecord = new UserRecord(null, "John Doe", "john@example.com");
        UserRecord createdUserRecord = new UserRecord(1L, "John Doe", "john@example.com");

        when(userRecordService.createUser(any(UserRecord.class))).thenReturn(createdUserRecord);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRecord)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    /**
     * Perform get user by id.
     *
     * @throws Exception the exception
     */
    @Test
    public void performGetUserById() throws Exception {
        UserRecord userRecord = new UserRecord(1L, "John Doe", "john@example.com");

        when(userRecordService.getUserById(1L)).thenReturn(userRecord);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    /**
     * Perform get all users.
     *
     * @throws Exception the exception
     */
    @Test
    public void performGetAllUsers() throws Exception {
        List<UserRecord> userRecords = Arrays.asList(
                new UserRecord(1L, "John Doe", "john@example.com"),
                new UserRecord(2L, "Jane Smith", "jane@example.com")
        );

        when(userRecordService.getAllUsers()).thenReturn(userRecords);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].email").value("john@example.com"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Jane Smith"))
                .andExpect(jsonPath("$[1].email").value("jane@example.com"));
    }

    /**
     * Perform update user.
     *
     * @throws Exception the exception
     */
    @Test
    public void performUpdateUser() throws Exception {
        UserRecord updatedUserRecord = new UserRecord(1L, "John Smith", "john@example.com");

        when(userRecordService.updateUser(eq(1L), any(UserRecord.class))).thenReturn(updatedUserRecord);

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUserRecord)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Smith"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    /**
     * Perform delete user.
     *
     * @throws Exception the exception
     */
    @Test
    public void performDeleteUser() throws Exception {
        doNothing().when(userRecordService).deleteUser(1L);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }
}
