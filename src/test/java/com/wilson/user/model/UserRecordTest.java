package com.wilson.user.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The type User record test.
 */
public class UserRecordTest {

    /**
     * When user constructor and getters then succeed.
     */
    @Test
    void whenUserConstructorAndGettersThenSucceed() {
        Long id = 1L;
        String name = "John Doe";
        String email = "john.doe@example.com";
        UserRecord userRecord = new UserRecord(id, name, email);

        assertEquals(id, userRecord.getId());
        assertEquals(name, userRecord.getName());
        assertEquals(email, userRecord.getEmail());
    }

    /**
     * When user setters then succeed.
     */
    @Test
    void whenUserSettersThenSucceed() {
        UserRecord userRecord = new UserRecord();
        Long id = 1L;
        String name = "John Doe";
        String email = "john.doe@example.com";

        userRecord.setId(id);
        userRecord.setName(name);
        userRecord.setEmail(email);

        assertEquals(id, userRecord.getId());
        assertEquals(name, userRecord.getName());
        assertEquals(email, userRecord.getEmail());
    }

}