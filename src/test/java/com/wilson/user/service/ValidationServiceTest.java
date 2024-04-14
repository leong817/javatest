package com.wilson.user.service;

import com.wilson.user.model.UserRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Validation service test.
 */
class ValidationServiceTest {

    private ValidationService validationService;
    private UserRecord validUserRecord;
    private UserRecord userRecordWithNullName;
    private UserRecord userRecordWithEmptyName;
    private UserRecord userRecordWithNullEmail;
    private UserRecord userRecordWithInvalidEmail;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        validationService = new ValidationService();
        validUserRecord = new UserRecord(1L, "John Doe", "john.doe@example.com");
        userRecordWithNullName = new UserRecord(2L, null, "jane.smith@example.com");
        userRecordWithEmptyName = new UserRecord(3L, "", "bob.jones@example.com");
        userRecordWithNullEmail = new UserRecord(4L, "Alice Smith", null);
        userRecordWithInvalidEmail = new UserRecord(5L, "Mike Brown", "invalid_email");
    }

    /**
     * When valid user record then validate user.
     */
    @Test
    void whenValidUserRecordThenValidateUser() {
        assertTrue(validationService.validateUser(validUserRecord));
    }

    /**
     * When null name then validate user.
     */
    @Test
    void whenNullNameThenValidateUser() {
        assertFalse(validationService.validateUser(userRecordWithNullName));
    }

    /**
     * When empty name then validate user.
     */
    @Test
    void whenEmptyNameThenValidateUser() {
        assertFalse(validationService.validateUser(userRecordWithEmptyName));
    }

    /**
     * When null email then validate user.
     */
    @Test
    void whenNullEmailThenValidateUser() {
        assertFalse(validationService.validateUser(userRecordWithNullEmail));
    }

    /**
     * When invalid email then validate user.
     */
    @Test
    void whenInvalidEmailThenValidateUser() {
        assertFalse(validationService.validateUser(userRecordWithInvalidEmail));
    }

    /**
     * When valid name then is valid name.
     */
    @Test
    void whenValidNameThenIsValidName() {
        assertTrue(validationService.isValidName("John Doe"));
    }

    /**
     * When null name then is valid name.
     */
    @Test
    void whenNullNameThenIsValidName() {
        assertFalse(validationService.isValidName(null));
    }

    /**
     * When empty name then is valid name.
     */
    @Test
    void whenEmptyNameThenIsValidName() {
        assertFalse(validationService.isValidName(""));
    }

    /**
     * When valid email then is valid email.
     */
    @Test
    void whenValidEmailThenIsValidEmail() {
        assertTrue(validationService.isValidEmail("john.doe@example.com"));
    }

    /**
     * When null email then is valid email.
     */
    @Test
    void whenNullEmailThenIsValidEmail() {
        assertFalse(validationService.isValidEmail(null));
    }

    /**
     * When invalid email then is valid email.
     */
    @Test
    void whenInvalidEmailThenIsValidEmail() {
        assertFalse(validationService.isValidEmail("invalid_email"));
    }
}