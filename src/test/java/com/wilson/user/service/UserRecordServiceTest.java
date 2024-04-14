package com.wilson.user.service;

import com.wilson.user.model.UserRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * The type User record service test.
 */
@ExtendWith(MockitoExtension.class)
public class UserRecordServiceTest {

    @Mock
    private UserRecordService userRecordService;

    private UserRecord testUserRecord;

    /**
     * Sets up.
     */
    @BeforeEach
    public void setUp() {
        testUserRecord = new UserRecord(1L, "John Doe", "john.doe@example.com");
    }

    /**
     * When valid create user then succeed.
     */
    @Test
    public void whenValidCreateUserThenSucceed() {
        when(userRecordService.createUser(any(UserRecord.class))).thenReturn(testUserRecord);

        UserRecord createdUserRecord = userRecordService.createUser(new UserRecord(null, "John Doe", "john.doe@example.com"));
        assertNotNull(createdUserRecord);
        assertEquals(testUserRecord, createdUserRecord);
    }

    /**
     * When valid get user by id then succeed.
     */
    @Test
    public void whenValidGetUserByIdThenSucceed() {
        when(userRecordService.getUserById(1L)).thenReturn(testUserRecord);

        UserRecord userRecord = userRecordService.getUserById(1L);
        assertNotNull(userRecord);
        assertEquals(testUserRecord, userRecord);
    }

    /**
     * When valid get all users then succeed.
     */
    @Test
    public void whenValidGetAllUsersThenSucceed() {
        List<UserRecord> userRecords = new ArrayList<>();
        userRecords.add(testUserRecord);
        when(userRecordService.getAllUsers()).thenReturn(userRecords);

        List<UserRecord> allUserRecords = userRecordService.getAllUsers();
        assertNotNull(allUserRecords);
        assertEquals(1, allUserRecords.size());
        assertEquals(testUserRecord, allUserRecords.get(0));
    }

    /**
     * When valid update user then succeed.
     */
    @Test
    public void whenValidUpdateUserThenSucceed() {
        UserRecord updatedUserRecord = new UserRecord(1L, "Jane Smith", "jane.smith@example.com");
        when(userRecordService.updateUser(eq(1L), any(UserRecord.class))).thenReturn(updatedUserRecord);

        UserRecord result = userRecordService.updateUser(1L, new UserRecord(1L, "Jane Smith", "jane.smith@example.com"));
        assertNotNull(result);
        assertEquals(updatedUserRecord, result);
    }

    /**
     * When vallid delete user then succeed.
     */
    @Test
    public void whenVallidDeleteUserThenSucceed() {
        doNothing().when(userRecordService).deleteUser(1L);

        userRecordService.deleteUser(1L);
        verify(userRecordService, times(1)).deleteUser(1L);
    }
}
