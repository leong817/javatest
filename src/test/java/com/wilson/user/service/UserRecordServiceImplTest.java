package com.wilson.user.service;

import com.wilson.user.exception.ResourceNotFoundException;
import com.wilson.user.model.UserRecord;
import com.wilson.user.repository.UserRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * The type User record service impl test.
 */
@ExtendWith(MockitoExtension.class)
public class UserRecordServiceImplTest {

    @Mock
    private UserRecordRepository userRecordRepository;

    @Mock
    private ValidationService validationService;

    private UserRecordService userRecordService;

    @Mock
    private JavaMailSenderImpl javaMailSender;

    private UserRecord testUserRecord;

    /**
     * Sets up.
     */
    @BeforeEach
    public void setUp() {
        userRecordService = new UserRecordServiceImpl(userRecordRepository, validationService, javaMailSender);
        testUserRecord = new UserRecord(1L, "John Doe", "john.doe@example.com");
    }

    /**
     * When valid user then create user.
     */
    @Test
    public void whenValidUserThenCreateUser() {
        when(validationService.validateUser(testUserRecord)).thenReturn(true);
        when(userRecordRepository.save(testUserRecord)).thenReturn(testUserRecord);
        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                return null;
            }
        }).when(javaMailSender).send(any(SimpleMailMessage.class));
        UserRecord createdUserRecord = userRecordService.createUser(testUserRecord);
        assertNotNull(createdUserRecord);
        assertEquals(testUserRecord, createdUserRecord);
    }

    /**
     * When invalid user then create user.
     */
    @Test
    public void whenInvalidUserThenCreateUser() {
        when(validationService.validateUser(testUserRecord)).thenReturn(false);

        UserRecord createdUserRecord = userRecordService.createUser(testUserRecord);
        assertNull(createdUserRecord);
    }

    /**
     * When existing user then get user by id.
     */
    @Test
    public void whenExistingUserThenGetUserById() {
        when(userRecordRepository.findById(1L)).thenReturn(Optional.of(testUserRecord));

        UserRecord userRecord = userRecordService.getUserById(1L);
        assertNotNull(userRecord);
        assertEquals(testUserRecord, userRecord);
    }

    /**
     * When non existing user then get user by id.
     */
    @Test
    public void whenNonExistingUserThenGetUserById() {
        when(userRecordRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userRecordService.getUserById(2L));
    }

    /**
     * When get all users.
     */
    @Test
    public void whenGetAllUsers() {
        List<UserRecord> userRecords = Arrays.asList(testUserRecord);
        when(userRecordRepository.findAll()).thenReturn(userRecords);
        when(validationService.validateUser(testUserRecord)).thenReturn(true);

        List<UserRecord> allUserRecords = userRecordService.getAllUsers();
        assertNotNull(allUserRecords);
        assertEquals(1, allUserRecords.size());
        assertEquals(testUserRecord, allUserRecords.get(0));
    }

    /**
     * When valid user then update user.
     */
    @Test
    public void whenValidUserThenUpdateUser() {
        UserRecord updatedUserRecord = new UserRecord(1L, "Jane Smith", "jane.smith@example.com");
        when(userRecordRepository.findById(1L)).thenReturn(Optional.of(testUserRecord));
        when(validationService.validateUser(updatedUserRecord)).thenReturn(true);
        when(userRecordRepository.save(testUserRecord)).thenReturn(updatedUserRecord);

        UserRecord result = userRecordService.updateUser(1L, updatedUserRecord);
        assertNotNull(result);
        assertEquals(updatedUserRecord, result);
    }

    /**
     * When invalid user update user.
     */
    @Test
    public void whenInvalidUserUpdateUser() {
        UserRecord updatedUserRecord = new UserRecord(1L, "Jane Smith", "jane.smith@example.com");
        when(userRecordRepository.findById(1L)).thenReturn(Optional.of(testUserRecord));
        when(validationService.validateUser(updatedUserRecord)).thenReturn(false);

        UserRecord result = userRecordService.updateUser(1L, updatedUserRecord);
        assertNull(result);
    }

    /**
     * When non existing user update user.
     */
    @Test
    public void whenNonExistingUserUpdateUser() {
        UserRecord updatedUserRecord = new UserRecord(2L, "Jane Smith", "jane.smith@example.com");
        when(userRecordRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userRecordService.updateUser(2L, updatedUserRecord));
    }

    /**
     * When existing user then delete user.
     */
    @Test
    public void whenExistingUserThenDeleteUser() {
        when(userRecordRepository.findById(1L)).thenReturn(Optional.of(testUserRecord));

        userRecordService.deleteUser(1L);
        verify(userRecordRepository, times(1)).delete(testUserRecord);
    }

    @Test
    public void whenNonExistingUserThenDeleteUser() {
        when(userRecordRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userRecordService.deleteUser(2L));
    }
}